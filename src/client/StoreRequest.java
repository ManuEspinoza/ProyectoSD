/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author Manuel Espinoza y Madelein Valderrabano
 */
public class StoreRequest {
    
    private Socket clientSocket;
   
    public void send(Paquete paquete, String ip, int port) throws IOException, ClassNotFoundException {
        //Envia peticion al servidor
        
        clientSocket = new Socket(ip,port);
        ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
        sendMessage.writeObject(paquete);
   
        clientSocket.close();
        sendMessage.close();
    }
    
    public void sendVerify(String ip, int port) throws IOException, ClassNotFoundException {
        //Envia peticion al servidor
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), 1000);
        socket.close();
    }
    
    
    
    public void sendWithResponse(Paquete paquete, String ip, int port) throws IOException, ClassNotFoundException {
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
    
        public Paquete sendPaquete(Paquete paquete, String ip, int port) throws IOException, ClassNotFoundException {
        //Envia peticion al servidor
        
        clientSocket = new Socket(ip,port);
        ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
        sendMessage.writeObject(paquete);
        
        //Espera respuesta del servidor
        ObjectInputStream message = new ObjectInputStream(clientSocket.getInputStream());
        Paquete mi_paquete = (Paquete) message.readObject();
     
        clientSocket.close();
        sendMessage.close();
        
        return mi_paquete;
    }

}
