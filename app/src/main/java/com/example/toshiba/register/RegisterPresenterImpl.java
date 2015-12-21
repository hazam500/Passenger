package com.example.toshiba.register;

import android.support.v7.app.AppCompatActivity;

import com.firebase.client.FirebaseError;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public class RegisterPresenterImpl extends AppCompatActivity implements RegisterPresenter {

    private final RegisterView registerView;
    private final RegisterInteractor registerInteractor;

    public RegisterPresenterImpl(RegisterView registerView){
        this.registerView = registerView;
        this.registerInteractor = new RegisterInteractorImpl();
    }

    public void register(String username,String password,String repassword){
        registerView.loadingVisible();
        registerInteractor.register(username,password,repassword,this);
    }

    @Override
    public void onUsernameError() {
        registerView.loadingInvisible();
        registerView.showUsernameError();
    }

    @Override
    public void onPasswordError() {
        registerView.loadingInvisible();
        registerView.showPasswordError();
    }

    @Override
    public void onRepeatPasswordErrorVoid() {
        registerView.loadingInvisible();
        registerView.showRepeatPasswordErrorVoid();
    }

    @Override
    public void onRepeatPasswordErrorDiffer() {
        registerView.loadingInvisible();
        registerView.showRepeatPasswordErrorDiffer();
    }

    @Override
    public void onServerError(FirebaseError firebaseError) {
        registerView.loadingInvisible();
        registerView.showServerError(firebaseError);
    }
}
