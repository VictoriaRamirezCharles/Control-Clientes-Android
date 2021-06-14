package com.example.controlclientes.models;

import java.util.List;

public class Cliente {

    private int Id;
    private String Nombre;
    private List<Direccion> Direcciones;

    public Cliente(String nombre) {
        Nombre = nombre;
    }

    public Cliente() {
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

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        Direcciones = direcciones;
    }
}
