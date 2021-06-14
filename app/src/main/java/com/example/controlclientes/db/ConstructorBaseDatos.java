package com.example.controlclientes.db;

import android.content.ContentValues;
import android.content.Context;

import com.example.controlclientes.models.Cliente;
import com.example.controlclientes.models.Direccion;
import com.example.controlclientes.models.GeneralModel;

import java.util.ArrayList;

public class ConstructorBaseDatos {

    private Context context;
    private GeneralModel generalModel;
    private  BBDD_Oriontek db;

    public ConstructorBaseDatos(Context context,BBDD_Oriontek db, GeneralModel generalModel) {

        this.context = context;
        this.generalModel = generalModel;
        this.db = db;
    }

    public ArrayList<Cliente> cbtenerDatosClientes() {
        return db.obtenerTodosClientes();
    }

    public ArrayList<Direccion> cbtenerDatosDireccion(int clienteId) {

        return db.obtenerTodasDirecciones(clienteId);
    }

    public void insertarcliente() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_CLIENTES_NOMBRE, generalModel.getCliente().getNombre());
        db.insertarCliente(contentValues);

    }

    public void deleteCliente(int id){
        db.deleteCliente(id);
    }

    public void deleteDireccion(int id){
        db.deleteDireccion(id);
    }

    public void updatecliente() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_CLIENTES_ID,generalModel.getCliente().getId());
        contentValues.put(ConstantesBaseDatos.TABLE_CLIENTES_NOMBRE, generalModel.getCliente().getNombre());
        db.updateCliente(contentValues);

    }

    public void updatedireccion() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_DIRECCIONES_ID,generalModel.getDireccion().getId());
        contentValues.put(ConstantesBaseDatos.TABLE_DIRECCIONES_NOMBRE, generalModel.getDireccion().getNombre());
        db.updateDireccion(contentValues);

    }

    public void insertarDireccion() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBaseDatos.TABLE_DIRECCIONES_NOMBRE, generalModel.getDireccion().getNombre());
        contentValues.put(ConstantesBaseDatos.TABLE_DIRECCIONES_CLIENTE_ID, generalModel.getDireccion().getClienteId());
        db.insertarDireccion(contentValues);

    }


}
