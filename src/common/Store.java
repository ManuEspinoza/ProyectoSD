/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Manuel Espinoza Madelein Valderrabano Isnere Hernandez
 */
public class Store implements Serializable {
    private ArrayList<Product> products;
    private String name;
    private String ip;
    private int port;
    private ArrayList<Compra> compras;

    public ArrayList<Compra> getCompras() {
        return compras;
    }

    public void setCompras(ArrayList<Compra> compras) {
        this.compras = compras;
    }
    

    public Store(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        products = new ArrayList();
        compras = new ArrayList();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Store{" + "products=" + products + ", name=" + name + ", ip=" + ip + ", port=" + port + ", compras=" + compras + '}';
    }

        
}
