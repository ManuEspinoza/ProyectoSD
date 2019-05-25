/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.Paquete;
import client.StoreRequest;
import common.Store;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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

    public void start(int port, String name) throws IOException, ClassNotFoundException {
       
        System.out.println("Im listening ... on " + port + " I'm " + name);
        this.name = name;
        serverSocket = new ServerSocket(port);
        Paquete mi_paquete;
       
        while(true){
            clientSocket = serverSocket.accept();
            ObjectInputStream message = new ObjectInputStream(clientSocket.getInputStream());
            mi_paquete = (Paquete) message.readObject();
            
            if("regTienda".equals(mi_paquete.getCode())){
                Store newStore = mi_paquete.getStore();
                this.stores.add(newStore);
                for(Store store: this.stores){
                    if(!this.name.equals(store.getName())){
                        Paquete paqueteUpdate = new Paquete("updateStores");
                        paqueteUpdate.setStores(this.stores);
                        StoreRequest updateStores =  new StoreRequest();
                        updateStores.send(paqueteUpdate, store.getIp(), store.getPort());
                    }
                }
                System.out.println(this.stores.toString());
                Paquete response = new Paquete("Tienda agregada");
                ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
                sendMessage.writeObject(response);
            } else if("updateStores".equals(mi_paquete.getCode())){
                this.stores = mi_paquete.getStores();
                System.out.println(this.stores.toString());
               Paquete response = new Paquete("Lista Actualizada");
               ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
                sendMessage.writeObject(response);
            }
        }
       // if (command.startsWith("regstore")) {
              
//            int totallength = "regtienda".length();
//            String partipante = greeting.substring(totallength, totallength + 4);
//            String puerto = greeting.substring(totallength + 4, totallength + 8);
//            String ip = greeting.substring(totallength + 8);
//            String[] parameters =  command.split("#");
//            String nameStore = parameters[1];
//            int portStore = Integer.parseInt(parameters[2]);
//            String ipStore = parameters[3];
//            Store store = new Store(nameStore, ipStore, portStore);
//            
//            stores.add(store);
//            
//            for(Store specificStore: stores){
//                ObjectOutputStream paquete_datos = new ObjectOutputStream(socket.getOutputStream());
//            }
            

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

//            out.println(store.toString());

        } 
//    public void stop() throws IOException {
//        in.close();
//        out.close();
//        clientSocket.close();
//        serverSocket.close();
//    }
    }

   

    


