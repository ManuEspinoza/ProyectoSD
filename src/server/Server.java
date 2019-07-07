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
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                if(!verifyName(newStore.getName())){
                    if(this.stores.size() > 0){
                       newStore = updateNewStore(newStore);
                    }
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
                } else{
                    Paquete response = new Paquete("denied");
                    ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
                    sendMessage.writeObject(response);
                }
                
            
            } else if("updateStores".equals(mi_paquete.getCode())){
                this.stores = mi_paquete.getStores();
                System.out.println(this.stores.toString());
            }
            else if("regProduct".equals(mi_paquete.getCode())){
                Product product = mi_paquete.getProduct();
                int selfStore = getSelfStore();
                
                if(productExists(product.getCode())){
                    Store storeUpdate = stores.get(selfStore);
                    ArrayList<Product> produtcs = storeUpdate.getProducts();
                    int producToUp = getProductToUpdate(produtcs,product.getCode());
                    Product productU = produtcs.get(producToUp);
                    productU.setQuantity(productU.getQuantity() + product.getQuantity());
                    produtcs.set(producToUp, productU);
                    storeUpdate.setProducts(produtcs);
                    stores.set(selfStore,storeUpdate);
                    
                } else {
                    Store storeUpdate = stores.get(selfStore);
                    storeUpdate.getProducts().add(product);
                    stores.set(selfStore,storeUpdate);
                        
                    for(int i = 0; i < stores.size(); i++){
                        if(!stores.get(i).getName().equals(this.name)){
                            stores.get(i).getProducts().add(new Product(product.getCode(),0));
                        }
                     }
                }
                
                for(Store store: this.stores){
                    if(!this.name.equals(store.getName()) && verifyUp(store.getIp(), store.getPort())){
                        Paquete paqueteUpdate = new Paquete("updateStores");
                        paqueteUpdate.setStores(this.stores);
                        StoreRequest updateStores =  new StoreRequest();
                        updateStores.send(paqueteUpdate, store.getIp(), store.getPort());
                    }
                }
            }
            
            else if ("ListProducto".equals(mi_paquete.getCode())){
                System.out.println(mi_paquete.getCode());
                Paquete paquetet = new Paquete("");
                paquetet.setStores(this.stores);
                ObjectOutputStream sendMessage = new ObjectOutputStream(clientSocket.getOutputStream());
                sendMessage.writeObject(paquetet);
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
    
    public boolean verifyUp(String host, int port){
        boolean result = true;
        try{
            Paquete paqueteUpdate = new Paquete("");
            StoreRequest updateStores =  new StoreRequest();
            updateStores.send(paqueteUpdate, host, port);
        } catch(ConnectException e){
            result = false;
        } catch (IOException e){
            result = false;
        } catch(ClassNotFoundException e){
            
        }
        return result;
    }
    
    public boolean verifyName(String name){
        for(int i = 0; i < stores.size();i++){
            if(stores.get(i).getName().equals(name))
                return true;
        }
        return false;
    }
    
    public boolean isActive(String host, int port) {
    Socket s = null;
    try {
        s = new Socket();
        s.setReuseAddress(true);
        SocketAddress sa = new InetSocketAddress(host, port);
        s.connect(sa, 3000);
        return true;
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (s != null) {
            try {
                s.close();
            } catch (IOException e) {
            }
        }
    }
    return false;
}
    
    public Store updateNewStore(Store store){
        ArrayList<Product> produtcs =  stores.get(0).getProducts();
        for(Product product: produtcs){
            Product newProduct =  new Product(product.getCode(),0);
            store.getProducts().add(newProduct);
        }
        return store;
    }
    
    public boolean productExists(int code){
        ArrayList<Product> products = this.stores.get(0).getProducts();
        return products.stream().anyMatch((product) -> (product.getCode() == code));
    }
    
    public int getProductToUpdate(ArrayList<Product> products, int code){
        for(int i = 0; i < products.size();i++){
            if(products.get(i).getCode() ==  code)
                return i;
        }
        return -1;
    }
}

   

    


