package com.example.controlclientes.direccion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controlclientes.R;
import com.example.controlclientes.cliente.ClienteActivity;
import com.example.controlclientes.cliente.EditClienteDialog;
import com.example.controlclientes.db.BBDD_Oriontek;
import com.example.controlclientes.db.ConstructorBaseDatos;
import com.example.controlclientes.models.Cliente;
import com.example.controlclientes.models.Direccion;
import com.example.controlclientes.models.GeneralModel;

import java.util.ArrayList;

public class DireccionActivity extends AppCompatActivity {
    ConstructorBaseDatos ct;
    BBDD_Oriontek db;
    GeneralModel generalModel;
    AddDireccionDialog addDireccionDialog;
    EditDireccionDialog editDireccionDialog;
    private ListView direccionListView;
    private static DireccionActivity INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("DIRECCIONES");
        INSTANCE = this;
        direccionListView = findViewById(R.id.direccionListView);

        TextView cliente = findViewById(R.id.tv_cliente_nombre_dr);
        Intent intent = getIntent();
        String clienteNombre = intent.getStringExtra("ClienteNombre");
        int clienteId = intent.getIntExtra("ClienteId",0);
        cliente.setText(clienteNombre);

        this.generalModel = new GeneralModel();
        this.db = new BBDD_Oriontek(DireccionActivity.this);
        this.ct = new ConstructorBaseDatos(DireccionActivity.this,db,generalModel);

        findViewById(R.id.btn_add_direccion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Direccion direccion = new Direccion();
                direccion.setClienteId(clienteId);
                generalModel.setDireccion(direccion);
                addDireccionDialog = new AddDireccionDialog(DireccionActivity.this,db,ct,generalModel);
                addDireccionDialog.show();
            }
        });
        cargarDirecciones(clienteId);
    }

    public static DireccionActivity getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DireccionActivity.this, ClienteActivity.class);
        startActivity(intent);
    }

    public void scrollMyListViewToBottom() {
        direccionListView.post(new Runnable() {
            @Override
            public void run() {
                direccionListView.setSelection(direccionListView.getAdapter().getCount()- 1);

            }
        });
    }

    public void cargarDirecciones(int clienteId){

        final ProgressDialog dialog = ProgressDialog.show(DireccionActivity.this, "PROCESANDO", "Se esta procesando su solicitud. Favor espere.", true);

        ArrayList<Direccion> direcciones = ct.cbtenerDatosDireccion(clienteId);
        if(direcciones != null){
            direccionListView.setAdapter(new DireccionActivity.DireccionAdapter(DireccionActivity.this, direcciones));
        }
        dialog.dismiss();

    }

    private class DireccionAdapter extends BaseAdapter
    {

        private final Context context;
        private final LayoutInflater inflater;
        private  ArrayList<Direccion> direcciones;

        public DireccionAdapter(Context context, ArrayList<Direccion> direcciones) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.direcciones = direcciones;
        }


        @Override
        public int getCount() {
            return direcciones.size();
        }

        @Override
        public Object getItem(int position) {
            return new Object();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null)
                convertView =  inflater.inflate(R.layout.item_direcciones, parent, false);

            ((TextView)convertView.findViewById(R.id.direccion_lista_nombre)).setText(this.direcciones.get(position).getNombre());

            convertView.findViewById(R.id.direccion_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopUp(position);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Direccion direccion = new Direccion();
                    direccion.setId(direcciones.get(position).getId());
                    direccion.setNombre(direcciones.get(position).getNombre());
                    direccion.setClienteId(direcciones.get(position).getClienteId());
                    generalModel.setDireccion(direccion);
                    editDireccionDialog = new EditDireccionDialog(context,db,ct,generalModel);
                    editDireccionDialog.show();
                }
            });


            return convertView;
        }

        public void PopUp(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DireccionActivity.this);
            builder.setMessage("¿Realmente desea eliminar esta direccion?")
                    .setTitle("Eliminar Direccion")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ct.deleteDireccion(direcciones.get(position).getId());
                            Toast.makeText(context, "Direccion eliminada correctamente", Toast.LENGTH_LONG).show();
                            cargarDirecciones(direcciones.get(position).getClienteId());

                        }
                    });
            builder.show();



        }
    }

}