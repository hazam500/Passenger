package com.example.toshiba.register;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface RegisterView {

    public void loadingVisible();
    public void loadingInvisible();
    public void showUsernameError();
    public void showPasswordError();
    public void showRepeatPasswordErrorVoid();
    public void showRepeatPasswordErrorDiffer();
    public void showServerError(FirebaseError firebaseError);

}
