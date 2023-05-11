package Request;

import android.content.Context;
import android.content.SharedPreferences;

import models.Usuario;

public class ApiClient {

    private static SharedPreferences sp;

    private static SharedPreferences conectar(Context context){
        if(sp == null){
            sp = context.getSharedPreferences("datos",0);
        }
        return sp;
    }

    public static void registrar(Context context, Usuario usuario){
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("dni",usuario.getDni());
        editor.putString("apellido",usuario.getApellido());
        editor.putString("nombre",usuario.getNombre());
        editor.putString("mail",usuario.getMail());
        editor.putString("clave",usuario.getClave());
        editor.commit();
    }

    public static Usuario leer(Context context){
        SharedPreferences sp = conectar(context);
        Long dni = sp.getLong("dni",-1);
        String apellido = sp.getString("apellido","-1");
        String nombre = sp.getString("nombre","-1");
        String mail = sp.getString("mail","-1");
        String clave = sp.getString("clave","-1");

        Usuario usuario = new Usuario(nombre,apellido,dni,mail,clave);

        return usuario;
    }

    public static Usuario login(Context context, String mail, String password){
        Usuario usuarioLogin = null;
        SharedPreferences sp = conectar(context);
        Long dniLogin = sp.getLong("dni",-1);
        String apellidoLogin = sp.getString("apellido","-1");
        String nombreLogin = sp.getString("nombre","-1");
        String mailLogin = sp.getString("mail","-1");
        String claveLogin = sp.getString("clave","-1");

        if(mail.equals(mailLogin) && password.equals(claveLogin)){
            usuarioLogin = new Usuario(nombreLogin,apellidoLogin,dniLogin,mailLogin,claveLogin);
        }
        return usuarioLogin;
    }

}
