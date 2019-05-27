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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    
    public int compareTo(Product o) {
        if (o.code < o.code) {
            return -1;
        }
        if (o.code > o.code) {
            return 1;
        }
            return 0;
    }
    
    public void mostrar(){

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario
        int codigo; int cantidad; int continuar;
        while (!salir) {
            System.out.println("\n Menú:\n");
            System.out.println(" 1. Listar productos de la empresa");
            System.out.println(" 2. Cargar productos de la empresa");
            System.out.println(" 3. Listar productos de la empresa por tienda");            
            try{
                System.out.print("Escribe una de las opciones: ");
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                        try{
                            int total[] = new int [1000];
                            for (int k=0; k<1000; k++){ 
                                total[k]=0;
                            }
                            int cantpro = 0;
                            Paquete paquete = new Paquete("ListProducto");
                            StoreRequest request = new StoreRequest();
                            Paquete mi_paquete = request.sendPaquete(paquete, ip, port);
                            ArrayList<Store> stores = mi_paquete.getStores();
                            for (int i = 0; i < stores.size(); i++) {
                                ArrayList<Product> productos = stores.get(i).getProducts();
                                System.out.println("\n");
                                cantpro = productos.size();
                                for (int j = 0; j < productos.size(); j++) {
                                    total[j] = productos.get(j).getQuantity() + total[j];
                                }
                            }
                            ArrayList<Product> productos1 = stores.get(0).getProducts();
                            for (int l = 0; l < cantpro; l++) {
                                System.out.print("Codigo producto: " + productos1.get(l).getCode() + 
                                    " Cantidad total: " + total[l] + "\n");
                            }
                        }catch(IOException ex){
                            System.out.println("Error de sistema operativo");
                        }
                        catch(ClassNotFoundException ex){
                            System.out.println("Error de clase no encontrada");
                        }
                        break;
                    case 2:
                        try{
                            System.out.print("Ingrese el codigo del producto: ");
                            codigo = sn.nextInt();
                            if (codigo!=0){ //Codigo no puede ser 0
                                System.out.print("Ingrese la cantidad del producto: ");
                                cantidad = sn.nextInt();
                                if (cantidad>0){ //Cantidad debe ser > 0
                                    Product producto = new Product(codigo, cantidad);
                                    Paquete paquete = new Paquete("regProduct");
                                    paquete.setStore(this.store);
                                    paquete.setProduct(producto);
                                    StoreRequest request = new StoreRequest();
                                    request.send(paquete, ip, port);
                                    System.out.println("Producto agregado");
                                }else{ System.out.println("Debe ingresar una cantidad valida");}
                            }
                            else{ System.out.println("Debe ingresar un codigo distinto");}
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
                            for (int i = 0; i < stores.size(); i++) {
                                ArrayList<Product> productos = stores.get(i).getProducts();
                                System.out.println("\n");
                                for (int j = 0; j < productos.size(); j++) {
                                    System.out.print("Tienda: " +
                                           stores.get(i).getName() + " Codigo producto: " +
                                           productos.get(j).getCode() + " Cantidad: " + productos.get(j).getQuantity() + "\n");
                                }
                            }
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
