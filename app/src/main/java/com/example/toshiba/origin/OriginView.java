package com.example.toshiba.origin;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public interface OriginView {
    public void showCurrentLoc();
    public void showDestiny();
    public void showSearchingBus();
    void hideConfirmOrigin();
    void searchDestination(String address);
}
