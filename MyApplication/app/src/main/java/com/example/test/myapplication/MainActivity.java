package com.example.test.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener {

    String sms = "【民信贷】尊敬的用户,你好,你的验证码为:530546,请你进入完成操作。";
    private String[] myDataset = {"1", "2", "3", "4", "5", "6", "7","8","9", "10", "11",
            "12", "13","14","15","16","17","18","19"};
    private Button btn1,btn2,btn3,btn4,btn5;
    private TextView tv;
    ExecutorService threadPoolExecutor;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv.append(msg.obj.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
//                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(new FoldeMenuView(this));
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        tv = (TextView) findViewById(R.id.text_view);
//        initThreadPool();
//        initListener();
//        initData();
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    private void initThreadPool() {
        btn1 = (Button) findViewById(R.id.thread_pool_1_btn);
        btn2 = (Button) findViewById(R.id.thread_pool_2_btn);
        btn3 = (Button) findViewById(R.id.thread_pool_3_btn);
        btn4 = (Button) findViewById(R.id.thread_pool_4_btn);
        btn5 = (Button) findViewById(R.id.thread_pool_5_btn);
    }

    private void initData() {
//        mRv = (RecyclerView) findViewById(R.id.rv);
//        mRv.setHasFixedSize(true);
//        layoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        mRv.setLayoutManager(layoutManager);
//        adapter = new MyAdapter(myDataset);
//        mRv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        tv.setText("");
        switch (v.getId()){
            case R.id.thread_pool_1_btn:
                threadPoolExecutor = Executors.newFixedThreadPool(3);
//                startThreads();
                startActivity(new Intent(MainActivity.this,CitiesActivity.class));
                finish();
                break;
            case R.id.thread_pool_2_btn:
                threadPoolExecutor = Executors.newSingleThreadExecutor();
                startThreads();
                break;
            case R.id.thread_pool_3_btn:
                threadPoolExecutor = Executors.newCachedThreadPool();
                startThreads3();
                break;
            case R.id.thread_pool_4_btn:
                ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
                //延迟2秒后执行该任务
                scheduledThreadPool.schedule(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2, TimeUnit.SECONDS);
                //延迟1秒后，每隔2秒执行一次该任务
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = handler.obtainMessage();
                        msg.obj = "线程任务执行了一次\n";
                        handler.sendMessage(msg);
                    }
                }, 1, 2, TimeUnit.SECONDS);
                break;
            case R.id.thread_pool_5_btn:
                ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
                singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        String threadName = Thread.currentThread().getName();
                        Log.v("zxy", "线程：" + threadName + ",正在执行");
                        Message msg = handler.obtainMessage();
                        msg.obj = threadName+"  --线程任务执行了一次\n";
                        handler.sendMessage(msg);
                    }
                }, 1, 2, TimeUnit.SECONDS);
                break;
        }
    }

    private void startThreads3() {
        for (int i = 1; i <= 3; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();

                    Message msg = handler.obtainMessage();
                    msg.obj = "线程：" + threadName + ",正在执行第" + index + "个任务\n";
                    handler.sendMessage(msg);
                    Log.v("zxy", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void startThreads() {
        for (int i=0;i<3;i++){
            final int finalI = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Message msg = handler.obtainMessage();
                    msg.obj = "ThreadName = "+threadName+"执行第"+ finalI +"个线程任务！\n";
                    handler.sendMessage(msg);
                    Log.i("THREAD", "ThreadName = " + threadName + "执行第" + finalI + "个线程任务！");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    class FoldeMenuViews extends View{
        private Bitmap mBitmap;
        private Matrix mMatrix;

        private Paint mShadowPaint;
        private Matrix mShadowGradientMatrix;
        private LinearGradient mShadowGradientShader;

        public FoldeMenuViews(Context context)
        {
            super(context);
            mBitmap = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.menu);
            mMatrix = new Matrix();

            mShadowPaint = new Paint();
            mShadowPaint.setStyle(Paint.Style.FILL);
            mShadowGradientShader = new LinearGradient(0f, 0, 1f, 0,
                    Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mShadowGradientShader);

            mShadowGradientMatrix = new Matrix();
            mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
            mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
            mShadowPaint.setAlpha((int) (0.9*255));

        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            canvas.save();
            float[] src = { 0, 0,//
                    mBitmap.getWidth(), 0,//
                    mBitmap.getWidth(), mBitmap.getHeight(),//
                    0, mBitmap.getHeight() };
            float[] dst = { 0, 0,//
                    mBitmap.getWidth()-100, 100,//
                    mBitmap.getWidth()-100, mBitmap.getHeight() - 100,//
                    0, mBitmap.getHeight() };
            mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);

            canvas.concat(mMatrix);
            canvas.drawBitmap(mBitmap, 0, 0, null);
            //绘制阴影                                                                                                                        canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
            canvas.drawRect(0,0,mBitmap.getWidth(),mBitmap.getHeight(),mShadowPaint);
            canvas.restore();

        }

    }
}
