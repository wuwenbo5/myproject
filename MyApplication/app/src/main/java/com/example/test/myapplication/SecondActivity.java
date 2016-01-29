package com.example.test.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import de.greenrobot.event.EventBus;

public class SecondActivity extends AppCompatActivity {

//    @state
    private int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().registerSticky(this);

        setContentView(R.layout.activity_second);

    }
    public void sonEventMainThread(String msg){

        Toast.makeText(SecondActivity.this,msg+"-------",Toast.LENGTH_LONG).show();
        Log.i("SecondActivity","----------"+msg);
    }

    public void onEvent(String msg){

        Toast.makeText(SecondActivity.this,msg+"----onEvent---",Toast.LENGTH_LONG).show();
        Log.i("SecondActivity","----------"+msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
