package com.lospibescompany.loginsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.lospibescompany.loginsharedpreferences.databinding.ActivityMainBinding;

import Login.LoginActivityViewModel;
import models.Usuario;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel mv;
    private LoginActivityViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        loginViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);


        loginViewModel.getDataUsuarioMutable().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                binding.etNombre.setText(usuario.getNombre());
                binding.etApellido.setText(usuario.getApellido());
                binding.etDni.setText(String.valueOf(usuario.getDni()));
                binding.etMail.setText(usuario.getMail());
                binding.etClave.setText(usuario.getClave());

            }
        });


        loginViewModel.leerDatos(getIntent());
        binding.btnRegistrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mv.registrarUsuario(binding.etNombre.getText().toString()
                        ,binding.etApellido.getText().toString()
                        ,binding.etDni.getText().toString()
                        ,binding.etMail.getText().toString()
                        ,binding.etClave.getText().toString());
            }
        });

    }
}