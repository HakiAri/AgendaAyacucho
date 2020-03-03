package com.haki.agendaayacucho.LuisMiguel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.haki.agendaayacucho.Lai.HandlerBasedeDatos;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.dmoral.toasty.Toasty;

public class Maya {
    private Context context ;
    private HandlerBasedeDatos manejaBD;
    private SQLiteDatabase nuestraBD;

    public Maya(Context context) {
        this.context = context;
    }

    public void Toast(String msm){
        Toast.makeText(context,msm,Toast.LENGTH_SHORT).show();
    }

    public void toastError(String msm){
        Toasty.error(context, msm, Toast.LENGTH_SHORT, true).show();
    }

    public void toastOk(String msm){
        Toasty.success(context, msm, Toast.LENGTH_SHORT, true).show();
    }

    public void toastAdvertencia(String msm){
        Toasty.warning(context, msm, Toast.LENGTH_SHORT, true).show();
    }

    public void toastInfo(String msm){
        Toasty.info(context, msm, Toast.LENGTH_SHORT, true).show();
    }

    public int accesoInternet(){
        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;
        int respuesta = -1;

        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {
                tipoConexion1 = true;
            }
            if (mMobile.isConnected()) {
                tipoConexion2 = true;
            }

            if (tipoConexion1 == true || tipoConexion2 == true) {
                respuesta = 1;/* Estas conectado a internet usando wifi o redes moviles, puedes enviar tus datos */
            }
        }
        else {
            /* No estas conectado a internet */
            respuesta = 0;
        }
        return  respuesta;
    }

    public int verificarUrlServidor() {
        String[] columnasus = { "_id","urlservidor"};
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("servidores",columnasus,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){
            return 1;
        }else{
            return 0;
        }
    }

    public String  buscarUrlServidor() {
        /*String[] columnasus = { "_id","urlservidor"};
        String urlservidor = "";
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("servidores",columnasus,"_id = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){
            int i_id         =  cu.getColumnIndex("_id");
            int i_url_con    = cu.getColumnIndex("urlservidor");
            cu.moveToFirst();
            Log.d("ID SERVIDOR", cu.getInt(i_id)+"" );
            urlservidor = cu.getString(i_url_con);
        }else{
            Toast("No se encuentra activo ninguna conexion a servidor...");
        }
        return  urlservidor;*/
        return "http://192.168.1.7/ayacucho";
        //return "http://ayacucho.hakiari.com";
    }

    public String  buscarMacBluetooth() {
        String[] columnasus = { "_id","macbluetooth"};
        String macbluetooth = "";
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("servidores",columnasus,"_id = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){
            int i_id         =  cu.getColumnIndex("_id");
            int i_url_con    = cu.getColumnIndex("macbluetooth");
            cu.moveToFirst();
            Log.d("ID BLUETOOTH", cu.getInt(i_id)+"" );
            macbluetooth = cu.getString(i_url_con);
        }else{
            Toast("No se encuentra activo ninguna conexion a servidor...");
        }
        return  macbluetooth;
    }

    public String  buscarUrlConexion() {
        String[] columnasu = { "_id", "nombreconexion","urlconexion"};
        String urlconexion = "";
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("urls",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){
            int i_id         =  cu.getColumnIndex("_id");
            int i_url_con    = cu.getColumnIndex("urlconexion");
            cu.moveToFirst();
            Log.d("ID SESION ACTIVOs", cu.getInt(i_id)+"" );
            urlconexion = cu.getString(i_url_con);
        }else{
            Toast("No se encuentra activo ninguna conexion...");
        }
        return  urlconexion;
    }

    public String  buscarIdAcceso() {
        String[] columnasu = { "_id", "nombreconexion","urlconexion"};
        String id_acceso = "";
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("urls",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){
            int i_id         =  cu.getColumnIndex("_id");
            int i_nom_con    = cu.getColumnIndex("nombreconexion");
            cu.moveToFirst();
            Log.d("ID SESION ACTIVOs", cu.getInt(i_id)+"" );
            id_acceso = cu.getString(i_nom_con);
        }else{
            Toast("No se encuentra activo ninguna conexion...");
        }
        return  id_acceso;
    }

    public String buscarNombreLogeado(){
        String[] columnasu = { "_id","nombrecompleto" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_nombre  = cu.getColumnIndex("nombrecompleto");
            cu.moveToFirst();
            Log.d("IdAcceso : ", cu.getString(i_nombre)+"" );

            return  cu.getString(i_nombre);

        }else{
            return  "";
        }
    }

    public String id_user(){
        String[] columnasu = { "_id","nombrecompleto" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_id_user  = cu.getColumnIndex("_id");
            cu.moveToFirst();
            Log.d("IdAcceso : ", cu.getString(i_id_user)+"" );

            return  cu.getString(i_id_user);

        }else{
            return  "0";
        }
    }

    public String buscarId_Objeto(){
        String[] columnasu = { "id_objeto" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"1 = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_id_objeto  = cu.getColumnIndex("id_objeto");
            cu.moveToFirst();
            Log.d("Id_Usuario : ", cu.getString(i_id_objeto)+"" );

            return  cu.getString(i_id_objeto);

        }else{
            return  "-1";
        }
    }

    public String buscarUsuarioLogeado(){
        String[] columnasu = { "_id","usuario" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_nombre  = cu.getColumnIndex("usuario");
            cu.moveToFirst();
            Log.d("usuario : ", cu.getString(i_nombre)+"" );

            return  cu.getString(i_nombre);

        }else{
            return  "";
        }
    }

    public String buscarUsuarioRol(){
        String[] columnasu = { "_id","rol" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_rol  = cu.getColumnIndex("rol");
            cu.moveToFirst();
            Log.d("usuario : ", cu.getString(i_rol)+"" );

            return  cu.getString(i_rol);

        }else{
            return  "";
        }
    }

    public String buscarSecretLogeado(){
        String[] columnasu = { "_id","contrasenia" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_nombre  = cu.getColumnIndex("contrasenia");
            cu.moveToFirst();
            Log.d("secret : ", cu.getString(i_nombre)+"" );

            return  cu.getString(i_nombre);

        }else{
            return  "";
        }
    }

    public String buscarAvatarLogueado(){
        String[] columnasu = { "_id","avatar" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"estado = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_avatar  = cu.getColumnIndex("avatar");
            cu.moveToFirst();
            Log.d("AvatarUrl : ", cu.getString(i_avatar)+"" );

            return  cu.getString(i_avatar);

        }else{
            return  "";
        }
    }



    public int cargarBDporPrimeraVez(String _id_acceso) {
        String[] columnas = { "_id","estado", "id_acceso" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor c = nuestraBD.query("cargarbd",columnas,"id_acceso = '"+_id_acceso+"'",null,null,null,null);
        //Toast.makeText(context," Count de la Consulta "+ c.getCount() ,Toast.LENGTH_SHORT).show();
        if(c.getCount() == 1){
            return 1;
        }else{
            return 0;
        }
    }

    public int buscarRol(){
        String[] columnasu = { "id_rol" };
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.query("usuarios",columnasu,"1 = 1",null,null,null,null);
        //Log.d("sql",cu.getCount()+"");
        if(cu.getCount() == 1){

            int i_id_rol  = cu.getColumnIndex("id_rol");
            cu.moveToFirst();
            Log.d("id_rol : ", cu.getString(i_id_rol)+"" );

            return  Integer.parseInt(cu.getString(i_id_rol));

        }else{
            return  -1;
        }
    }

    public String fechaActual(int tipo){
        Calendar c = new GregorianCalendar();
        String [] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        String fecha = null;
        int hora, minutos, segundos;
        hora         = c.get(Calendar.HOUR_OF_DAY);
        minutos      = c.get(Calendar.MINUTE);
        segundos     = c.get(Calendar.SECOND);
        String dia   = Integer.toString(c.get(Calendar.DATE));
        String mes   = meses[c.get(Calendar.MONTH)];
        String anio  = Integer.toString(c.get(Calendar.YEAR));
        if(tipo == 0){
            fecha = dia +" de "+ mes + " del "+anio;
        }
        if(tipo == 1){
            fecha = dia +"/"+ c.get(Calendar.MONTH) + "/"+anio;
        }
        if(tipo == 2){
            fecha = dia +"  "+ mes + " del "+anio +" a Horas. "+hora + ":" + minutos + ":" + segundos;
        }
        if(tipo == 3){
            fecha = anio+"-"+ c.get(Calendar.MONTH)+"-"+dia+" "+hora+":"+minutos+":"+segundos;
        }
        return fecha;
    }

    public void cerrarSesionUsuario() {
        manejaBD = new HandlerBasedeDatos(context);
        nuestraBD = manejaBD.getWritableDatabase();
        Cursor cu = nuestraBD.rawQuery("DELETE FROM usuarios", null);
        if(cu.getCount()==0){
            Log.d("sesion ", "cerrado sesion con exito");
        }
    }
}
