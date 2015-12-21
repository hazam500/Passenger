package com.example.toshiba.register;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface RegisterPresenter {
    public void register(String username,String password,String repassword);
    public void onUsernameError();
    public void onPasswordError();
    public void onRepeatPasswordErrorVoid();
    public void onRepeatPasswordErrorDiffer();
    public void onServerError(FirebaseError firebaseError);
}
