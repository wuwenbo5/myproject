package com.example.test.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Toast.makeText(context,"-------MyReciever--"+i,Toast.LENGTH_LONG).show();
        i++;
        Log.i("----","------MyReceiver：i="+i);
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
