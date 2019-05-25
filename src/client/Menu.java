/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.Product;
import common.Store;
import java.io.IOException;
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
            System.out.println("Escribe una de las opciones: ");
            opcion = sn.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Has seleccionado la opcion 1");
                    break;
                case 2:
                    try{
                        System.out.println("Ingrese el codigo del producto: ");
                        codigo = sn.nextInt();
                        System.out.println("Ingrese la cantidad del producto: ");
                        cantidad = sn.nextInt();
                        Product producto = new Product(codigo, cantidad);
                        Paquete paquete = new Paquete("regProduct");
                        paquete.setStore(this.store);
                        paquete.setProduct(producto);
                        StoreRequest request = new StoreRequest();
                        request.sendWithResponse(paquete, ip, port);
                    }catch(IOException ex){
                        System.out.println("Error de sistema operativo");
                    }
                    catch(ClassNotFoundException ex){
                        System.out.println("Error de clase no encontrada");
                    }
                    break;
                case 3:
                    System.out.println("Has seleccionado la opcion 3");
                    break;
                case 4:
                    System.out.println("Has seleccionado la opcion 3");
                    break;
                default:
                    System.out.println("Solo números entre 1 y 4");
            }
        }        
    }
}
