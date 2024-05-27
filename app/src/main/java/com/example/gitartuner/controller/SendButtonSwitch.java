package com.example.gitartuner.controller;

import android.widget.Button;

public class SendButtonSwitch {

    Button button;
    private boolean isFlagData=false;
    private boolean isFlagBluetooth=false;
    public SendButtonSwitch(Button button) {
        this.button = button;
        button.setEnabled(false);
        button.setClickable(false);
    }


    public void enableBluetoothFlag(){
        isFlagBluetooth=true;
        check();
    }
    public void enableDataFlag(){
        isFlagData=true;
        check();
    }
    private void check(){
        if(isFlagBluetooth&&isFlagData) {
            button.setEnabled(true);
            button.setClickable(true);
        }
    }
}
