package com.example.toshiba.login;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface LoginPresenter {

    void loginUser(String username,String password);
    void onUsernameError();
    void onPasswordError();
    void onServerError(FirebaseError firebaseError);
    void moveToRequest(String uid);
}
