package com.example.controlclientes.cliente;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.controlclientes.R;
import com.example.controlclientes.db.BBDD_Oriontek;
import com.example.controlclientes.db.ConstructorBaseDatos;
import com.example.controlclientes.models.Cliente;
import com.example.controlclientes.models.GeneralModel;

public class EditClienteDialog extends Dialog {
    GeneralModel generalModel;
    EditText edt_nombre;
    ConstructorBaseDatos ct;
    BBDD_Oriontek db;
    Button edit_cliente;

    public EditClienteDialog(@NonNull Context context, BBDD_Oriontek db, ConstructorBaseDatos ct, GeneralModel generalModel) {
        super(context);
        this.generalModel = generalModel;
        this.db = db;
        this.ct = ct;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_cliente);
        setupLayout();
        edt_nombre = findViewById(R.id.ed_nombre_cliente_edit);
        edit_cliente = findViewById(R.id.btn_edit_cliente_data);

        edt_nombre.setText(generalModel.getCliente().getNombre().toString());
        edit_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCliente();


            }
        });
    }

    private void setupLayout() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    private void editCliente() {
        if (!edt_nombre.getText().toString().isEmpty()) {
            generalModel.getCliente().setNombre(edt_nombre.getText().toString());
            ct.updatecliente();
            ClienteActivity.getInstance().cargarClientes();
            ClienteActivity.getInstance().editClienteDialog.dismiss();
        } else {
            Toast.makeText(getContext(), "El campo no puede quedar vacio", Toast.LENGTH_SHORT).show();
        }
    }
}
