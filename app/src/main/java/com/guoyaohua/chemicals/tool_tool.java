package com.guoyaohua.chemicals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.Toast;

/**
 * Created by 谢仪頔 on 2016/7/18.
 */
public class tool_tool extends AppCompatActivity {
    public static String tool_tool_title[] = new String[]{"危险化学品名录汇编", "危险化学品相关法规", "危险化学品事故应急处置", "危险化学品事故消防救援"};
    private ListView mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tool_tool_layout);
        mList = (ListView)findViewById(R.id.lv_tool_tool);
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
        for (int i = 0; i < 4; i++) {
            map = new HashMap<String, Object>();
            map.put("tool_tool_title", tool_tool_title[i]);
            list.add(map);
        }
        SimpleAdapter simAdapter = new SimpleAdapter(this, list, R.layout.tool_tool_item_layout, new String[]{"tool_tool_title"}, new int[]{R.id.tx_tool_tool});
        mList.setAdapter(simAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF/"+tool_tool_title[0]+".pdf");
                             intent = new Intent("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromFile(file);
                            intent.setDataAndType(uri, "application/pdf");
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "您的手机无法显示该文件", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://wenku.baidu.com/link?url=XtJytbtXBLEYhKYGysCWQaCmMjYlYx4MCPZSjJgy9MCTmDrnGb56OVebx4s1aiVd6oEOvPeYGT7C2ZrbXF7t6cMJPl3SmBq4S9QasqA08Ne")));
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF/"+tool_tool_title[1]+".pdf");
                            intent = new Intent("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromFile(file);
                            intent.setDataAndType(uri, "application/pdf");
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "您的手机无法显示该文件", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF/"+tool_tool_title[2]+".pdf");
                            intent = new Intent("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromFile(file);
                            intent.setDataAndType(uri, "application/pdf");
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "您的手机无法显示该文件", Toast.LENGTH_SHORT).show();
                        }
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.guanyang.gov.cn/(S(msdpl1bt0v3s2ponstohswoz))/News/Display.aspx?aid=110112")));
                        break;
                    case 3:
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF/"+tool_tool_title[3]+".pdf");
                            intent = new Intent("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            Uri uri = Uri.fromFile(file);
                            intent.setDataAndType(uri, "application/pdf");
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "您的手机无法显示该文件", Toast.LENGTH_SHORT).show();
                        }
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://119.china.com.cn/zbjs/txt/2014-04/17/content_6828361.htm")));
                        break;

                }
            }
        });
    }

    public void click(View view){
        this.finish();
    }
}

