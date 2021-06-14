    package com.example.controlclientes.db;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    import androidx.annotation.Nullable;

    import com.example.controlclientes.models.Cliente;
    import com.example.controlclientes.models.Direccion;

    import java.util.ArrayList;

    public class BBDD_Oriontek extends SQLiteOpenHelper {

        public BBDD_Oriontek(@Nullable Context context) {
            super(context, ConstantesBaseDatos.DATEBASE_NAME, null, ConstantesBaseDatos.DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String queryCrearTablaClientes = "CREATE TABLE "      + ConstantesBaseDatos.TABLE_CLIENTES + "(" +
                    ConstantesBaseDatos.TABLE_CLIENTES_ID         + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    ConstantesBaseDatos.TABLE_CLIENTES_NOMBRE     + " TEXT  " +
                    ")";

            String queryCrearTablaDirecciones = "CREATE TABLE "      + ConstantesBaseDatos.TABLE_DIRECCIONES + "(" +
                    ConstantesBaseDatos.TABLE_DIRECCIONES_ID         + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    ConstantesBaseDatos.TABLE_DIRECCIONES_NOMBRE     + " TEXT,  " +
                    ConstantesBaseDatos.TABLE_DIRECCIONES_CLIENTE_ID + " INTEGER,"+
                    "CONSTRAINT fk_clientes" +
                    " FOREIGN KEY("+ConstantesBaseDatos.TABLE_DIRECCIONES_CLIENTE_ID+")"+
                    "REFERENCES "+ConstantesBaseDatos.TABLE_CLIENTES+"("+ConstantesBaseDatos.TABLE_CLIENTES_ID+")"+
                    "ON DELETE CASCADE"+
                    ")";

            sqLiteDatabase.execSQL(queryCrearTablaClientes);
            sqLiteDatabase.execSQL(queryCrearTablaDirecciones);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_CLIENTES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ConstantesBaseDatos.TABLE_DIRECCIONES);

        }

        public void insertarCliente(ContentValues contentValues) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(ConstantesBaseDatos.TABLE_CLIENTES, null, contentValues);
            db.close();
        }

        public void insertarDireccion(ContentValues contentValues) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(ConstantesBaseDatos.TABLE_DIRECCIONES, null, contentValues);
            db.close();
        }

        public ArrayList<Cliente> obtenerTodosClientes() {

            ArrayList<Cliente> clientes = new ArrayList<>();

            String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_CLIENTES;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor registros = db.rawQuery(query, null);

            while(registros.moveToNext()){
                Cliente clienteActual=new Cliente();
                clienteActual.setId(registros.getInt(0));
                clienteActual.setNombre(registros.getString(1));


                clientes.add(clienteActual);
            }

            return clientes;
        }

        public ArrayList<Direccion> obtenerTodasDirecciones(int clienteId) {

            ArrayList<Direccion> direcciones = new ArrayList<>();

            String query = "SELECT * FROM " + ConstantesBaseDatos.TABLE_DIRECCIONES + " Where "+ ConstantesBaseDatos.TABLE_DIRECCIONES_CLIENTE_ID+" = "+clienteId;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor registros = db.rawQuery(query, null);

            while(registros.moveToNext()){

                Direccion direccionActual = new Direccion();
                direccionActual.setId(registros.getInt(0));
                direccionActual.setNombre(registros.getString(1));
                direccionActual.setClienteId(registros.getInt(2));
                direcciones.add(direccionActual);
            }

            return direcciones;
        }

        public boolean deleteCliente(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(ConstantesBaseDatos.TABLE_CLIENTES, ConstantesBaseDatos.TABLE_CLIENTES_ID + "=" + id, null) > 0;
        }

        public boolean deleteDireccion(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(ConstantesBaseDatos.TABLE_DIRECCIONES, ConstantesBaseDatos.TABLE_DIRECCIONES_ID + "=" + id, null) > 0;
        }

        public boolean updateCliente(ContentValues contentValues) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.update(ConstantesBaseDatos.TABLE_CLIENTES, contentValues, ConstantesBaseDatos.TABLE_CLIENTES_ID + "=" + contentValues.getAsInteger(ConstantesBaseDatos.TABLE_CLIENTES_ID), null) > 0;
        }

        public boolean updateDireccion(ContentValues contentValues) {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.update(ConstantesBaseDatos.TABLE_DIRECCIONES, contentValues, ConstantesBaseDatos.TABLE_DIRECCIONES_ID + "=" + contentValues.getAsInteger(ConstantesBaseDatos.TABLE_DIRECCIONES_ID), null) > 0;
        }
    }
