package com.example.controlclientes.direccion;

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
import com.example.controlclientes.models.GeneralModel;

public class EditDireccionDialog extends Dialog {
    GeneralModel generalModel;
    EditText edt_nombre;
    ConstructorBaseDatos ct;
    BBDD_Oriontek db;
    Button add_direccion;

    public EditDireccionDialog(@NonNull Context context, BBDD_Oriontek db, ConstructorBaseDatos ct, GeneralModel generalModel) {
        super(context);
        this.generalModel =  generalModel;
        this.db = db;
        this.ct = ct;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_direccion);
        setupLayout();
        edt_nombre = findViewById(R.id.ed_nombre_direccion_edit);
        add_direccion = findViewById(R.id.btn_edit_direccion_data);
         edt_nombre.setText(generalModel.getDireccion().getNombre());
        add_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDireccion();
            }
        });
    }

    private void setupLayout() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    private void editDireccion(){
        if(!edt_nombre.getText().toString().isEmpty()){
            generalModel.getDireccion().setNombre(edt_nombre.getText().toString());
            ct.updatedireccion();
            DireccionActivity.getInstance().cargarDirecciones(generalModel.getDireccion().getClienteId());
            DireccionActivity.getInstance().editDireccionDialog.dismiss();
        }else{
            Toast.makeText(getContext(), "Debe agregar un nombre", Toast.LENGTH_SHORT).show();
        }
    }


}