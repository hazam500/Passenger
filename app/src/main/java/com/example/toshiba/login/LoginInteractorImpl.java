package com.example.toshiba.login;

import android.support.v7.app.AppCompatActivity;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public class LoginInteractorImpl extends AppCompatActivity implements LoginInteractor {
    @Override
    public void login(String username, String password, final LoginPresenterImpl loginPresenterImpl) {

        Firebase firebase = new Firebase("https://passenger.firebaseio.com/");

        if (username.equals("")) {
            loginPresenterImpl.onUsernameError();
        }
        if (password.equals("")) {
            loginPresenterImpl.onPasswordError();
        }

        firebase.authWithPassword(username, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
                loginPresenterImpl.moveToRequest(authData.getUid());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                loginPresenterImpl.onServerError(firebaseError);
            }
        });
    }

}
