/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.Serializable;

/**
 *
 * @author Manuel Espinoza Isnere Hernandez Madelein Valderrabano
 */
public class Product implements Serializable {
    private int code;
    private String name;
    private int quantity;

    public Product(int code, int quantity) {
        this.code = code;
        this.quantity = quantity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" + "code=" + code + ", name=" + name + ", quantity=" + quantity + '}';
    }
       
}
