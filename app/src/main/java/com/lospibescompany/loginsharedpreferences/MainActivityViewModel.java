package com.lospibescompany.loginsharedpreferences;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Login.LoginActivity;
import Request.ApiClient;
import models.Usuario;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<Usuario> dataUsuarioMutable;

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
