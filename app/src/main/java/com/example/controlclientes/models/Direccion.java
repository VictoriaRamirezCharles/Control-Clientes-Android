package com.example.controlclientes.models;

public class Direccion {

    private int Id;
    private int ClienteId;
    private String Nombre;

    public Direccion() {
    }

    public Direccion(String nombre,int clienteId) {
        ClienteId = clienteId;
        Nombre = nombre;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
