package com.example.toshiba.login;

/**
 * Created by TOSHIBA on 21/12/2015.
 */
public interface LoginInteractor {

    public void login(String username, String password, LoginPresenterImpl loginView);
}
