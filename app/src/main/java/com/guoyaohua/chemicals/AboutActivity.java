package com.guoyaohua.chemicals;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_intro;
    private Button bt_update;
    private Button bt_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);
        setTitle("关于");
        initWidget();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        bt_exit = (Button) findViewById(R.id.bt_exit);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_intro = (Button) findViewById(R.id.bt_intro);
        bt_exit.setOnClickListener(this);
        bt_intro.setOnClickListener(this);
        bt_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_update:
                Toast.makeText(this, "已经是最新版本！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_intro:
                Intent intent = new Intent();
                intent.setClass(this, IntroduceActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_exit:
                android.os.Process.killProcess(android.os.Process.myPid());



                break;
        }
    }

}
