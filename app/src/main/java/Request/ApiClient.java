package Request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.Usuario;

public class ApiClient {


    public static void registrar(Context context, Usuario usuario){
        File archivo = new File(context.getFilesDir(), "personal.dat");
        try {
            FileOutputStream fos = new FileOutputStream(archivo, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(usuario);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuario leer(Context context){
        File archivo = new File(context.getFilesDir(), "personal.dat");
        if (!archivo.exists()) {
            return null;
        }
        Usuario usuarioRegistrado = null;
        try {
            FileInputStream fis = new FileInputStream(archivo);
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarioRegistrado = (Usuario) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuarioRegistrado;
    }

    public static Usuario login(Context context, String mail, String password){
        Usuario usuarioRegistrado = leer(context);

        if (usuarioRegistrado != null && mail.equals(usuarioRegistrado.getMail()) && password.equals(usuarioRegistrado.getClave())) {
            return usuarioRegistrado;
        }
        return null;
    }

}
