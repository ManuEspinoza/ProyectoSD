/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import client.Paquete;
import client.StoreRequest;
import common.Product;
import common.Store;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

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
                        updateStores.sendWithResponse(paqueteUpdate, store.getIp(), store.getPort());
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
            
            } else if("regProduct".equals(mi_paquete.getCode())){
                Product product = mi_paquete.getProduct();
                int selfStore = getSelfStore();
                Store storeUpdate = stores.get(selfStore);
                storeUpdate.getProducts().add(product);
                stores.set(selfStore,storeUpdate);
                
                for(int i = 0; i < stores.size(); i++){
                    if(!stores.get(i).getName().equals(this.name)){
                        stores.get(i).getProducts().add(new Product(product.getCode(),0));
                    }
                }
                
                for(Store store: this.stores){
                    if(!this.name.equals(store.getName())){
                        Paquete paqueteUpdate = new Paquete("updateStores");
                        paqueteUpdate.setStores(this.stores);
                        StoreRequest updateStores =  new StoreRequest();
                        updateStores.sendWithResponse(paqueteUpdate, store.getIp(), store.getPort());
                    }
                }
                System.out.println(mi_paquete.getCode());
            }
        }
    } 
    
    public int getSelfStore(){
        for(int i = 0; i < stores.size();i++){
            if(stores.get(i).getName().equals(this.name))
                return i;
        }
        return -1;
    }
}

   

    


