package com.example.toshiba.register;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface RegisterPresenter {
    void register(String username,String password,String repassword);
    void onUsernameError();
    void onPasswordError();
    void onRepeatPasswordErrorVoid();
    void onRepeatPasswordErrorDiffer();
    void onServerError(FirebaseError firebaseError);
    void moveToLogin();
}
