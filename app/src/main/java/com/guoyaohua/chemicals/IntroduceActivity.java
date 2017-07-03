package com.guoyaohua.chemicals;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce_layout);
        setTitle("程序介绍");
        App app = (App) getApplication();//获取应用程序全局的实例引用
        app.activities.add(this);  //把当前Activity放入集合中

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App app = (App) getApplication();//获取应用程序全局的实例引用
        app.activities.remove(this); //把当前Activity从集合中移除
    }
}
