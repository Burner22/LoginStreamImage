package Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lospibescompany.loginsharedpreferences.MainActivity;
import com.lospibescompany.loginsharedpreferences.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel mv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);


        binding.btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.confirmarLogin(binding.etMail.getText().toString(), binding.etClave.getText().toString());
            }
        });

        binding.btnIrARegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("loguin", false);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        mv.getLoginSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginSuccess) {
                if (loginSuccess) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loguin", true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}