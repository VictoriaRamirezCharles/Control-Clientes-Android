package com.example.controlclientes.cliente;

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

import com.example.controlclientes.direccion.DireccionActivity;
import com.example.controlclientes.R;
import com.example.controlclientes.db.BBDD_Oriontek;
import com.example.controlclientes.db.ConstructorBaseDatos;
import com.example.controlclientes.models.Cliente;
import com.example.controlclientes.models.GeneralModel;

import java.util.ArrayList;

public class ClienteActivity extends AppCompatActivity {
    ConstructorBaseDatos ct;
    BBDD_Oriontek db;
    GeneralModel generalModel;
    private ListView clienteListView;
    private Context context;
    private static ClienteActivity INSTANCE;
    AddClienteDialog addClienteDialog;
    EditClienteDialog editClienteDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        INSTANCE = this;
        setTitle("CONTROL CLIENTES");
        Button  add_cliente = findViewById(R.id.btn_add_cliente);
        this.clienteListView = findViewById(R.id.clienteListView);
        this.generalModel = new GeneralModel();
        this.db = new BBDD_Oriontek(ClienteActivity.this);
        this.ct = new ConstructorBaseDatos(ClienteActivity.this,db,generalModel);
        cargarClientes();
        add_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClienteDialog = new AddClienteDialog(ClienteActivity.this,db,ct,generalModel);
                addClienteDialog.show();

            }
        });
    }

    public void scrollMyListViewToBottom() {
        clienteListView.post(new Runnable() {
            @Override
            public void run() {
                clienteListView.setSelection(clienteListView.getAdapter().getCount()- 1);

            }
        });
    }

    public static ClienteActivity getInstance() {
        return INSTANCE;
    }

    public void cargarClientes(){

        final ProgressDialog dialog = ProgressDialog.show(ClienteActivity.this, "PROCESANDO", "Se esta procesando su solicitud. Favor espere.", true);

        ArrayList<Cliente> clientes = ct.cbtenerDatosClientes();
        if(clientes != null){
            clienteListView.setAdapter(new ClienteAdapter(ClienteActivity.this, clientes));
        }
        dialog.dismiss();

    }

    private class ClienteAdapter extends BaseAdapter
    {

        private final Context context;
        private final LayoutInflater inflater;
        private  ArrayList<Cliente> clientes;

        public ClienteAdapter(Context context, ArrayList<Cliente> clientes) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.clientes = clientes;
        }


        @Override
        public int getCount() {
            return clientes.size();
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
                convertView =  inflater.inflate(R.layout.item_clientes, parent, false);

            ((TextView)convertView.findViewById(R.id.cliente_lista_nombre)).setText(this.clientes.get(position).getNombre());

            ((ImageView)convertView.findViewById(R.id.cliente_delete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        PopUp(position);
                }
            });

            ((ImageView)convertView.findViewById(R.id.cliente_edit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generalModel.setCliente(clientes.get(position));
                    editClienteDialog = new EditClienteDialog(context,db,ct,generalModel);
                    editClienteDialog.show();

                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ClienteActivity.this, DireccionActivity.class);
                    intent.putExtra("ClienteId",clientes.get(position).getId());
                    intent.putExtra("ClienteNombre",clientes.get(position).getNombre());
                    startActivity(intent);
                }
            });

            return convertView;
        }

        public void PopUp(int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ClienteActivity.this);
            builder.setMessage("¿Realmente desea eliminar este cliente?")
                    .setTitle("Eliminar Cliente")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ct.deleteCliente(clientes.get(position).getId());
                            Toast.makeText(context, "Cliente eliminado correctamente", Toast.LENGTH_LONG).show();
                            cargarClientes();

                        }
                    });
            builder.show();



        }
    }
}