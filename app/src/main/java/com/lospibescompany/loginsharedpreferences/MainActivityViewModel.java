package com.lospibescompany.loginsharedpreferences;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Login.LoginActivity;
import Request.ApiClient;
import models.Usuario;

import static android.app.Activity.RESULT_OK;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<Usuario> dataUsuarioMutable;
    private MutableLiveData<Bitmap> foto = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = new ApiClient();
    }

    public LiveData<Usuario> getDataUsuarioMutable() {
        if (dataUsuarioMutable == null) {
            dataUsuarioMutable = new MutableLiveData<>();
        }
        return dataUsuarioMutable;
    }

    public LiveData<Bitmap> getFoto(){
        if(foto==null){
            foto=new MutableLiveData<>();
        }
        return foto;
    }

    public void cargar(Intent intent) {
        Bundle bundle = intent.getExtras();
        boolean loguin = (boolean) bundle.getSerializable("loguin");
        if(loguin != false){
            File archivo =new File(context.getFilesDir(),"foto1.png");

            Bitmap imageBitmap= BitmapFactory.decodeFile(archivo.getAbsolutePath());
            if(imageBitmap!=null) {
                foto.setValue(imageBitmap);
            }
        }

    }

    public void leerDatos(Intent intent) {
        Bundle bundle = intent.getExtras();
        boolean loguin = (boolean) bundle.getSerializable("loguin");
        if(loguin != false) {
            Usuario usuario = apiClient.leer(context);
            if (usuario != null) {
                dataUsuarioMutable.setValue(usuario);

            } else {
                Toast.makeText(context, "Credenciales erroneas", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            dataUsuarioMutable.setValue(new Usuario("","",0,"",""));
        }
    }

    public void respuetaDeCamara(int requestCode, int resultCode, @Nullable Intent data, int REQUEST_IMAGE_CAPTURE){
        Log.d("salida",requestCode+"");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Recupero los datos provenientes de la camara.
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara.
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Rutina para optimizar la foto,
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            foto.setValue(imageBitmap);

            //Rutina para convertir a un arreglo de byte los datos de la imagen
            byte [] b=baos.toByteArray();


            //Aquí podría ir la rutina para llamar al servicio que recibe los bytes.
            File archivo =new File(context.getFilesDir(),"foto1.png");
            if(archivo.exists()){
                archivo.delete();
            }
            try {
                FileOutputStream fo=new FileOutputStream(archivo);
                BufferedOutputStream bo=new BufferedOutputStream(fo);
                bo.write(b);
                bo.flush();
                bo.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void registrarUsuario(String nombre, String apellido, String dni, String mail, String clave) {
        try {
            Long dniLong = Long.parseLong(dni);
            Usuario usuario = new Usuario(nombre, apellido, dniLong, mail, clave);

            apiClient.registrar(context, usuario);

            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(context, "El DNI debe ser un número válido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }

    }

}
