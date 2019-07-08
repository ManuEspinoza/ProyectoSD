/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import common.Product;

/**
 *
 * @author Manuel Espinoza
 */
public class CompraTotal implements Comparable {
    int total;
    int codigoCliente;
    int codigoProducto;

    @Override
    public String toString() {
        return "CompraTotal{" + "total=" + total + ", codigoCliente=" + codigoCliente + ", codigoProducto=" + codigoProducto + '}';
    }

    public CompraTotal(int codigoCliente, int total,  int codigoProducto) {
        this.total = total;
        this.codigoCliente = codigoCliente;
        this.codigoProducto = codigoProducto;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    @Override
    public int compareTo(Object o) {
        CompraTotal product = (CompraTotal) o;
        return this.codigoCliente - product.getCodigoCliente();
    }
}
