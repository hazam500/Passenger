package com.example.toshiba.register;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface RegisterInteractor {

    void register(String username, String password, String repassword, RegisterPresenterImpl registerPresenter);
}
