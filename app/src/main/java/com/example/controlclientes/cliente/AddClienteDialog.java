package com.example.controlclientes.cliente;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.controlclientes.R;
import com.example.controlclientes.db.BBDD_Oriontek;
import com.example.controlclientes.db.ConstructorBaseDatos;
import com.example.controlclientes.models.Cliente;
import com.example.controlclientes.models.Direccion;
import com.example.controlclientes.models.GeneralModel;

import java.util.ArrayList;

public class AddClienteDialog extends Dialog {
    GeneralModel generalModel;
    EditText edt_nombre;
    ConstructorBaseDatos ct;
    BBDD_Oriontek db;
    Button add_cliente;

    public AddClienteDialog(@NonNull Context context,BBDD_Oriontek db, ConstructorBaseDatos ct, GeneralModel generalModel) {
        super(context);
        this.generalModel =  generalModel;
        this.db = db;
        this.ct = ct;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_cliente);
        setupLayout();
        edt_nombre = findViewById(R.id.ed_nombre_cliente);
        add_cliente = findViewById(R.id.btn_add_cliente_data);

        add_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCliente();
            }
        });
    }

    private void setupLayout() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    private void addCliente(){
        if(!edt_nombre.getText().toString().isEmpty()){
            Cliente cliente = new Cliente(edt_nombre.getText().toString());
            generalModel.setCliente(cliente);
            ct.insertarcliente();
            ClienteActivity.getInstance().cargarClientes();
            ClienteActivity.getInstance().addClienteDialog.dismiss();
            ClienteActivity.getInstance().scrollMyListViewToBottom();
        }else{
            Toast.makeText(getContext(), "Debe agregar un nombre", Toast.LENGTH_SHORT).show();
        }
    }


}