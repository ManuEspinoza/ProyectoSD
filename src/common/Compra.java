/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author Manuel Espinoza
 */
public class Compra implements Serializable, Comparable {
    int clientCode;
    String clientName;
    Product product;

    @Override
    public String toString() {
        return "Compra{" + "clientCode=" + clientCode + ", clientName=" + clientName + ", product=" + product + '}';
    }

    public Compra(int clientCode, String clientName, Product product) {
        this.clientCode = clientCode;
        this.clientName = clientName;
        this.product = product;
    }

    public int getClientCode() {
        return clientCode;
    }

    public void setClientCode(int clientCode) {
        this.clientCode = clientCode;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public int compareTo(Object o) {
        Compra compra = (Compra) o;
        return this.clientCode - compra.getClientCode();
    }
}
