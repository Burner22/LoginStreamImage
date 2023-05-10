package Login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Request.ApiClient;
import models.Usuario;

public class LoginActivityViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>(false);
    private MutableLiveData<Usuario> dataUsuarioMutable;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = new ApiClient();
    }

    public LiveData<Boolean> getLoginSuccess() {
        loginSuccess = new MutableLiveData<>();
        return loginSuccess;
    }
    public LiveData<Usuario> getDataUsuarioMutable() {
        if (dataUsuarioMutable == null) {
            dataUsuarioMutable = new MutableLiveData<>();
        }
        return dataUsuarioMutable;
    }


    public void confirmarLogin(String mail, String clave) {
        if(mail.length() == 0 || clave.length() == 0){
            Toast.makeText(context, "Credenciales erroneas", Toast.LENGTH_SHORT).show();
        }
        else{
            Usuario usuario = apiClient.login(context,mail,clave);
            loginSuccess.setValue(usuario != null ? true : false);
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



}
