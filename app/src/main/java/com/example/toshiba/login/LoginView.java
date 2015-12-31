package com.example.toshiba.login;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface LoginView {

    void loadingVisible();
    void loadingInvisible();
    void showUsernameError();
    void showPasswordError();
    void showServerError(FirebaseError firebaseError);
    void moveToRequest(String uid);
}
