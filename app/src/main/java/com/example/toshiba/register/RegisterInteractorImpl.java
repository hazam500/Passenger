package com.example.toshiba.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.toshiba.login.LoginActivity;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public class RegisterInteractorImpl extends AppCompatActivity implements RegisterInteractor {
    @Override
    public void register(String username, String password, String repassword, final RegisterPresenterImpl registerPresenter) {

        Firebase.setAndroidContext(LoginActivity.getContext());

        Firebase firebase = new Firebase("https://brilliant-heat-7882.firebaseio.com/");

        if (username.equals("")) {
            registerPresenter.onUsernameError();
        }
        if (password.equals("")) {
            registerPresenter.onPasswordError();
            return;
        }
        if (repassword.equals("")) {
            registerPresenter.onRepeatPasswordErrorVoid();
            return;
        }
        if (!password.equals(repassword)) {
            registerPresenter.onRepeatPasswordErrorDiffer();
        }

        firebase.createUser(username, password, new Firebase.ValueResultHandler<Map<String, Object>>() {

            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Intent intent = new Intent(RegisterInteractorImpl.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                registerPresenter.onServerError(firebaseError);
            }
        });
    }
}
