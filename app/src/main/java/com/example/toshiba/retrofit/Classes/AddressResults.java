package com.example.toshiba.retrofit.Classes;

/**
 * Created by TOSHIBA on 22/12/2015.
 */
public class AddressResults {

    public Result[] results = new Result[100];
    public String status = "";

    public Result[] getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}

