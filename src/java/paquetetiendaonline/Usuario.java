/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paquetetiendaonline;

/**
 *
 * @author luis
 */
public class Usuario {
    private String nombre, password, productos;
    private float saldo;
    private int id;

    public Usuario(String nombre, String password, float saldo) {
        this.nombre = nombre;
        this.password = password;
        this.saldo = saldo;
    }

    public Usuario(String nombre, String password, String productos, float saldo, int id) {
        this.nombre = nombre;
        this.password = password;
        this.productos = productos;
        this.saldo = saldo;
        this.id = id;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getProductos() {
        return productos;
    }

    public float getSaldo() {
        return saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

   
    
    
}
