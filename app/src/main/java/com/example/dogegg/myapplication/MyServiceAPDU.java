package com.example.dogegg.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyServiceAPDU extends HostApduService {
    public static String TAG = "MyServiceAPUD";
    public MyServiceAPDU() {
        System.out.println("Service start..");
        Log.d(TAG,"Service start...");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 启动主activity
        Intent startMain = new Intent(getBaseContext(),MainActivity.class);
        startMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {
        System.out.print("processCommandApdu:");
        printHex(commandApdu);
        byte[] re = {(byte)0x90,(byte)0x88,(byte)0x88};

        return re;
    }

    @Override
    public void onDeactivated(int reason) {
        System.out.println("on Deactivated");
    }

    public static void printHex(byte[] apdu){
        for( int i = 0; i < apdu.length; i++ ){
            System.out.printf("%02x ",apdu[i]);
        }
        System.out.println();
    }

    private void addLogToFile(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd>HH:mm:ss");
        Date now = new Date(System.currentTimeMillis());
        String msg = format.format(now);

        FileOutputStream outputStream;

        try{
            outputStream = openFileOutput(MainActivity.LogFileName, Context.MODE_APPEND);
            outputStream.write(msg.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
