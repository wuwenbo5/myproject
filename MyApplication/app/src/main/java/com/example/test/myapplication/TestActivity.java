/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.test.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.myapplication.adapter.CommonAdapter;
import com.example.test.myapplication.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.greenrobot.event.EventBus;

/*
 * MainActivity class that loads MainFragment
 */
public class TestActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private GridView mListView;

    private CommonAdapter mAdapter;
    private List<String> mDatas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.main_layout);
        mListView = (GridView) findViewById(R.id.list_view);
        mDatas = new ArrayList<String>(Arrays.asList("hello","你好","不好啊","yes?","ok?","是的啊"));
        sonEventMainThread(mDatas);
        initDatas();
    }

    private void initDatas() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    EventBus.getDefault().post(mDatas);
                    EventBus.getDefault().post("弹出消息");


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }


    public void sonEventMainThread(List<String> datas){
        mListView.setAdapter(mAdapter = new CommonAdapter<String>(getApplicationContext(), mDatas,
                R.layout.layout_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                TextView textView = helper.getView(R.id.tv);
                textView.setText(item);
                ImageView imageView = helper.getView(R.id.iv);
                imageView.setImageResource(R.drawable.star_icon);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().postSticky("通过EventBus弹出的消息");
                    }
                });
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(TestActivity.this,SecondActivity.class));
                EventBus.getDefault().postSticky(mDatas.get(position));
//                finish();
            }
        });
    }
    public void onEvent(String msg )
    {
//        Toast.makeText(TestActivity.this,"onEventPostThread---"+msg,Toast.LENGTH_LONG).show();
        Log.i("TAG", "--onEventPostThread--" + msg);
    }
//    public void onEventMainThread(String msg){
//        Toast.makeText(this,"onEventMainThread---"+msg,Toast.LENGTH_LONG).show();
//    }

//    public void onEventPostThread(String msg )
//    {
////        Toast.makeText(TestActivity.this,"onEventPostThread---"+msg,Toast.LENGTH_LONG).show();
//        Log.i("TAG","--onEventPostThread--"+msg);
//    }

//    public void onEventBackgroundThread(String msg)
//    {
////        Toast.makeText(TestActivity.this,"onEventBackgroundThread---"+msg,Toast.LENGTH_LONG).show();
//                    Log.i("TAG","--onEventBackgroundThread--"+msg);
//
//    }

//    public void onEventAsync(String msg)
//    {
////        Toast.makeText(TestActivity.this,"onEventAsync---"+msg,Toast.LENGTH_LONG).show();
//                Log.i("TAG","--onEventAsync--"+msg);
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
