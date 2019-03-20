package com.haki.agendaayacucho.Lai;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HandlerBasedeDatos extends SQLiteOpenHelper {
    private final static String NombreBD = "mariana";
    private static final int VersionBD = 1;
    private SQLiteDatabase base_datos;
    private Context context;

    //Declaramos las tablas
    private String SQL_SERVIDORES   = "CREATE TABLE if not exists servidores (_id INTEGER PRIMARY KEY AUTOINCREMENT, urlservidor TEXT, macbluetooth TEXT, estado INTEGER)";
    private String SQL_USUARIOS     = "CREATE TABLE if not exists usuarios   (_id INTEGER, id_rol TEXT, rol TEXT, id_objeto TEXT, nombrecompleto TEXT, avatar TEXT, usuario TEXT, contrasenia TEXT,estado INTEGER)";

    //Contructor
    public HandlerBasedeDatos(Context context) {
        super(context, NombreBD, null, VersionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_SERVIDORES);
        db.execSQL(SQL_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS servidores;");
        db.execSQL("DROP TABLE IF EXISTS usuarios;");
        onCreate(db);
    }

    public void cerrar(){
        this.close();
    }

    // Metodos de Inserion de Regitros a las tablas

    public void addServidores(String url_servidor, String mac, int estado){
        ContentValues valores = new ContentValues();
        valores.put("urlservidor",url_servidor);
        valores.put("macbluetooth",mac);
        valores.put("estado",estado);
        this.getWritableDatabase().insert("servidores", null,valores);
    }

    public void addUsuario(int id, String id_rol, String rol, String id_objeto, String nombrecompleto, String avatar,String usuario, String contrasenia) {
        ContentValues valores = new ContentValues();
        valores.put("_id", id);
        valores.put("id_rol",id_rol);
        valores.put("rol",rol);
        valores.put("id_objeto",id_objeto);
        valores.put("nombrecompleto",nombrecompleto);
        valores.put("avatar",avatar);
        valores.put("usuario",usuario);
        valores.put("contrasenia",contrasenia);
        valores.put("estado", 1);
        this.getWritableDatabase().insert("usuarios", null, valores);
    }
}

