/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.StoreMessage;
import common.Store;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


/**
 *
 * @author franciscogomezlopez
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private HashMap<String, String> participant = new HashMap<String, String>();
    private boolean finishGame = false;
    private boolean tengopapa = false;
    private String name = "";
    private boolean sendFinishGame = false;
    private ArrayList<Store> stores = new ArrayList();

    public void start(int port, String name) throws IOException {
        //<----------- No cambiar inicia el servidor para las tiendas ------------>
        System.out.println("Im listening ... on " + port + " I'm " + name);
        this.name = name;
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String command = in.readLine();
        //<----------------------------------------------------------------------->
        if (command.startsWith("regstore")) {
              
//            int totallength = "regtienda".length();
//            String partipante = greeting.substring(totallength, totallength + 4);
//            String puerto = greeting.substring(totallength + 4, totallength + 8);
//            String ip = greeting.substring(totallength + 8);
            String[] parameters =  command.split("#");
            String nameStore = parameters[1];
            int portStore = Integer.parseInt(parameters[2]);
            String ipStore = parameters[3];
            Store store = new Store(nameStore, ipStore, portStore);
            
            stores.add(store);
            
            for(Store specificStore: stores){
//                ObjectOutputStream paquete_datos = new ObjectOutputStream(socket.getOutputStream());
            }
            

            // agregar participante
           // this.participant.put(partipante, puerto + ":" + ip);

            // paseo por cada uno de los elementos de los participantes para enviar la lista
//            for (Map.Entry<String, String> entry : participant.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue();
//
//                if (!key.equals(this.name)) {
//
//                    StoreMessage sendmessage = new StoreMessage();
//                    sendmessage.startConnection(value.substring(5), new Integer(value.substring(0, 4)));
//                    sendmessage.sendMessage("actualizalista" + this.serializarLista());
//
//                }
//
//            }

            out.println(store.toString());

        } else if (command.startsWith("recibepapa")) {

            this.tengopapa = true;
            System.out.println("TEngo la papa " + this.name);
            out.println("tienes la papa" + this.name);
            // agregar participante

            if (this.finishGame && this.tengopapa) {

                System.out.println("perdiste:" + this.name);
                System.exit(0);
            }

        } else if (command.startsWith("sequemo")) {

            this.finishGame = true;

            // agregar participante
            if (this.finishGame && this.tengopapa) {

                System.out.println("perdiste:" + this.name);
                System.exit(0);
            }

            if (!this.sendFinishGame) {
                for (Map.Entry<String, String> entry : participant.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();

                    if (!key.equals(this.name)) {
                        SendMessageThread hilo = new SendMessageThread();
                        hilo.setSeconds(0);
                        hilo.setValue(value);
                        hilo.setMessage("sequemo");
                        (new Thread(hilo)).start();
                    }

                }
                this.sendFinishGame = true;
            }
            out.println("finish se quemo");

        } else if (command.startsWith("actualizalista")) {

            String lista = command.substring("actualizalista".length());

            String[] listatmp = lista.split(",");
            this.participant = new HashMap<String, String>();
            for (String tmp : listatmp) {
                String[] finaltmp = tmp.split("#");
                this.participant.put(finaltmp[0], finaltmp[1]);
            }

        } else {
            System.out.println("Mensaje no reconocido");
            out.println("mensaje corrupto vete de aqui");
        }

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();

        if (this.tengopapa) {
            try {
                this.sendingPapa();
            } catch (InterruptedException ex) {
                System.out.println("Error sending papa");
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.start(port, name);

    }

    private void sendingPapa() throws InterruptedException, IOException {

        boolean found = false;

        for (Map.Entry<String, String> entry : participant.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals(this.name)) {
                found = true;
            } else {

                if (found) {
                    // siguiente
//                    ClientMessage sendmessage = new ClientMessage();
//                    sendmessage.startConnection(value.substring(5), new Integer(value.substring(0, 4)));
//                    sendmessage.sendMessage("recibepapa");

                    SendMessageThread hilo = new SendMessageThread();
                    hilo.setValue(value);
                    hilo.setMessage("recibepapa");
                    (new Thread(hilo)).start();
                    System.out.println("bye hilo1");
                    this.tengopapa = false;
                    break;
                }
            }

        }

        if (this.tengopapa) {
            Map.Entry<String, String> entry = this.participant.entrySet().iterator().next();
            String key = entry.getKey();
            String value = entry.getValue();

            SendMessageThread hilo = new SendMessageThread();
            hilo.setValue(value);
            hilo.setMessage("recibepapa");
            (new Thread(hilo)).start();
            System.out.println("bye hilo2");
            this.tengopapa = false;
        }
    }

    private String serializarLista() {

        String finallista = "";

        for (Map.Entry<String, String> entry : participant.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            finallista += key + "#" + value + ",";

        }

        return finallista;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
