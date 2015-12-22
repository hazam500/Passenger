package com.example.toshiba.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.toshiba.passenger.R;
import com.example.toshiba.register.RegisterActivity;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText username;
    private EditText password;
    private LinearLayout login;
    private LinearLayout cargando;
    private LoginPresenterImpl presenter;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (LinearLayout) findViewById(R.id.login);
        cargando = (LinearLayout) findViewById(R.id.loading);

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    public void loadingVisible() {
        login.setVisibility(View.GONE);
        cargando.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingInvisible() {
        login.setVisibility(View.VISIBLE);
        cargando.setVisibility(View.GONE);
    }

    @Override
    public void showUsernameError() {
        username.setError("Por favor introduzca su email");
    }

    @Override
    public void showPasswordError() {
        password.setError("Por favor introduzca su contraseña");
    }

    @Override
    public void showServerError(FirebaseError firebaseError) {
        Snackbar.make(login, "Problema del servidor, no autenticado", Snackbar.LENGTH_SHORT).show();
    }

    public void login() {
        presenter.loginUser(username.getText().toString(), password.getText().toString());
    }

    public void register() {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
