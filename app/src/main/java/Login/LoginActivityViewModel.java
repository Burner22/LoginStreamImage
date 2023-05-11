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

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        apiClient = new ApiClient();
    }

    public LiveData<Boolean> getLoginSuccess() {
        loginSuccess = new MutableLiveData<>();
        return loginSuccess;
    }

    public void confirmarLogin(String mail, String clave) {
        if(mail.length() == 0 || clave.length() == 0){
            Toast.makeText(context, "Credenciales erroneas", Toast.LENGTH_SHORT).show();
        }
        else{
            Usuario usu = apiClient.login(context, mail, clave);
            if (usu != null) {
                loginSuccess.setValue(true);
            } else {
                Toast.makeText(context, "Credenciales erroneas", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
