package com.example.toshiba.login;

import android.support.v7.app.AppCompatActivity;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public class LoginPresenterImpl extends AppCompatActivity implements LoginPresenter {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }


    @Override
    public void loginUser(String username, String password) {
        loginView.loadingVisible();
        loginInteractor.login(username, password, this);
    }

    @Override
    public void onUsernameError() {
        loginView.loadingInvisible();
        loginView.showUsernameError();
    }

    @Override
    public void onPasswordError() {
        loginView.loadingInvisible();
        loginView.showPasswordError();
    }

    @Override
    public void onServerError(FirebaseError firebaseError) {
        loginView.loadingInvisible();
        loginView.showServerError(firebaseError);
    }
}
