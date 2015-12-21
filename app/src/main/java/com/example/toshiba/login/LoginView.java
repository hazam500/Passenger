package com.example.toshiba.login;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface LoginView {

    public void loadingVisible();
    public void loadingInvisible();
    public void showUsernameError();
    public void showPasswordError();
    public void showServerError(FirebaseError firebaseError);

}
