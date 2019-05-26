/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.Product;
import common.Store;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author isner
 */
public class Menu {
    
    String ip;
    int port;
    String name;
    Store store;

    public Menu(String ip, int port, Store store) {
        this.ip = ip;
        this.port = port;
        this.store = store;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void mostrar(){

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        int codigo; int cantidad;
        while (!salir) {

            System.out.println("Menú:\n");
            System.out.println(" 1. Listar productos de la empresa");
            System.out.println(" 2. Cargar productos de la empresa");
            System.out.println(" 3. Listar productos de la empresa por tienda");
            System.out.println(" 4. Agregar tiendas al sistema");
            
             try{
                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();
            
            switch (opcion) {
                case 1:
                    try{
                        Store store = new Store("Prueba", "", 9700);
                        Paquete paqueteNewStore = new Paquete("regProduct");
                        StoreRequest request = new StoreRequest();
                        request.send(paqueteNewStore, ip,port );
                        System.out.println("all good");
                    }catch(IOException ex){
                        System.out.println("Error de sistema operativo");
                    }
                    catch(ClassNotFoundException ex){
                        System.out.println("Error de clase no encontrada");
                    }
                    break;
                case 2:
                    try{
                        System.out.println("Ingrese el codigo del producto: ");
                        codigo = sn.nextInt();
                        if (codigo!=0){ //Codigo no puede ser 0
                        System.out.println("Ingrese la cantidad del producto: ");
                        cantidad = sn.nextInt();
                        if (cantidad>0){ //Cantidad debe ser > 0
                        Product producto = new Product(codigo, cantidad);
                        Paquete paquete = new Paquete("regProduct");
                        paquete.setStore(this.store);
                        paquete.setProduct(producto);
                        System.out.println(port);
                        System.out.println(ip);
                        StoreRequest request = new StoreRequest();
                        request.send(paquete, ip, port);
                        System.out.println("Producto agregado");
                        } else{System.out.println("Debe ingresar una cantidad valida");}
                        }
                        else 
                        {System.out.println("Debe ingresar un codigo distinto");}
                    }catch(IOException ex){
                        System.out.println("Error de sistema operativo");
                    }
                    catch(ClassNotFoundException ex){
                      System.out.println("Error de clase no encontrada");
                    }
                    break;
                case 3:
                    try{
                        Paquete paquete = new Paquete("ListProducto");
                        StoreRequest request = new StoreRequest();
                        Paquete mi_paquete = request.sendPaquete(paquete, ip, port);
                        ArrayList<Store> stores = mi_paquete.getStores();
                        System.out.println(stores);
                    }catch(IOException ex){
                        System.out.println("Error de sistema operativo");
                    }
                    catch(ClassNotFoundException ex){
                        System.out.println("Error de clase no encontrada");
                    }
                    break;
                    
                case 4:
                    System.out.println("Has seleccionado la opcion 3");
                    break;
                default:
                    System.out.println("Solo números entre 1 y 4");
                    }
            
            }catch (InputMismatchException e) {
            System.out.println("Debes insertar un número");
            sn.next();
            }
        }        
    }
}
