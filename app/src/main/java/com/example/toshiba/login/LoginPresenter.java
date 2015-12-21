package com.example.toshiba.login;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface LoginPresenter {

    public void loginUser(String username,String password);
    public void onUsernameError();
    public void onPasswordError();
    public void onServerError(FirebaseError firebaseError);

}
