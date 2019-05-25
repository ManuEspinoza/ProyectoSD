/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosistemasdistribuidos;

import client.Paquete;
import client.StoreRequest;
import common.Store;
import server.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manue Espinoza
 */
public class ProyectoSistemasDistribuidos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        if(args[0].startsWith("server")) {
            try {
                
                Server server = new Server();
                server.start(new Integer(args[1]), args[2]);
                
            } catch (IOException ex) {
                System.out.println("error server");
                Logger.getLogger(ProyectoSistemasDistribuidos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException  ex){
                System.out.println("client server");
            }
            
        } else {
            try {
           
                String storeParameters = args[3];
                String[] parameters = storeParameters.split("#");
                String nameStore = parameters[0];
                int port = new Integer(args[2]);
                String ip = args[1];
                
                Store store = new Store(nameStore, parameters[1], new Integer(parameters[2]));
                Paquete paqueteNewStore = new Paquete("regTienda");
                paqueteNewStore.setStore(store);
                StoreRequest request = new StoreRequest();
                request.send(paqueteNewStore, ip, port);
            } catch (IOException ex) {
                System.out.println("error client message");
                Logger.getLogger(ProyectoSistemasDistribuidos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex){
                System.out.println("Error clase no encontrada");
            }
        }
    }
}
