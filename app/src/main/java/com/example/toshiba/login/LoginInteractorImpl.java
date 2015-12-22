package com.example.toshiba.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.toshiba.request.RequestActivity;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public class LoginInteractorImpl extends AppCompatActivity implements LoginInteractor {
    @Override
    public void login(String username, String password, final LoginPresenterImpl loginPresenterImpl) {

        Firebase.setAndroidContext(LoginActivity.getContext());

        final Firebase firebase = new Firebase("https://brilliant-heat-7882.firebaseio.com/");

        if (username.equals("")) {
            loginPresenterImpl.onUsernameError();
        }
        if (password.equals("")) {
            loginPresenterImpl.onPasswordError();
        }

        firebase.authWithPassword(username, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
                Intent intent = new Intent(LoginInteractorImpl.this, RequestActivity.class);
                intent.putExtra("Uid", authData.getUid());
                startActivity(intent);

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                loginPresenterImpl.onServerError(firebaseError);
            }
        });
    }

}
