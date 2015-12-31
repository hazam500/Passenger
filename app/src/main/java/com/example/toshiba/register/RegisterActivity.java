package com.example.toshiba.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.toshiba.login.LoginActivity;
import com.example.toshiba.passenger.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private EditText registerUsername;
    private EditText password;
    private EditText repassword;
    private LinearLayout register;
    private LinearLayout loading;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Firebase.setAndroidContext(this);

        registerUsername = (EditText) findViewById(R.id.registerUsername);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        register = (LinearLayout) findViewById(R.id.register);
        loading = (LinearLayout) findViewById(R.id.loading);

        presenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void loadingVisible() {
        register.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingInvisible() {
        register.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void showUsernameError() {

        registerUsername.setError("Por favor introduzca su email");
    }

    @Override
    public void showPasswordError() {

        password.setError("Por favor introduzca su contraseña");
    }

    @Override
    public void showRepeatPasswordErrorVoid() {
        repassword.setError("Por favor introduzca su contraseña");
    }

    @Override
    public void showRepeatPasswordErrorDiffer() {
        repassword.setError("Las contraseñas no coinciden, por favor introduzcala nuevamente");
    }

    @Override
    public void showServerError(FirebaseError firebaseError) {
        Snackbar.make(register, "Problema del servidor, no autenticado", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void moveToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        presenter.register(registerUsername.getText().toString(), password.getText().toString(), repassword.getText().toString());
    }
}
