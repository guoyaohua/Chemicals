package com.guoyaohua.chemicals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 谢仪頔 on 2016/7/18.
 */
public class tool_set extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_set_layout);
    }
    public void click(View view){
        this.finish();
    }
}
