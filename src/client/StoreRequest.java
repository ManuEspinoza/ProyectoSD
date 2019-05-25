/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Manuel Espinoza y Madelein Valderrabano
 */
public class StoreRequest {
    
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void send(Paquete paquete, String ip, int port) throws IOException, ClassNotFoundException {
        
        //Envia peticion al servidor
        
        clientSocket = new Socket(ip,port);
        ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
        sendMessage.writeObject(paquete);
        
        //Espera respuesta del servidor
        ObjectInputStream message = new ObjectInputStream(clientSocket.getInputStream());
        Paquete mi_paquete = (Paquete) message.readObject();
        System.out.println(mi_paquete.getCode());
     
        clientSocket.close();
        sendMessage.close();
    }
 
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }
 
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
