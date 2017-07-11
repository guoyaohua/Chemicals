package com.guoyaohua.chemicals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        mList = (ListView) findViewById(R.id.lv_tool_tool);
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
                        File file = new File(getFilesDir() + "/ChemicalPDF/" + tool_tool_title[0] + ".pdf");
                        if (file.exists()) {
                            startPDF(file.getPath(), tool_tool_title[0]);
                        } else {
                            Toast.makeText(getApplicationContext(), "文件丢失", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        file = new File(getFilesDir() + "/ChemicalPDF/" + tool_tool_title[1] + ".pdf");
                        if (file.exists()) {
                            startPDF(file.getPath(), tool_tool_title[0]);
                        } else {
                            Toast.makeText(getApplicationContext(), "文件丢失", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2:
                        file = new File(getFilesDir() + "/ChemicalPDF/" + tool_tool_title[2] + ".pdf");
                        if (file.exists()) {
                            startPDF(file.getPath(), tool_tool_title[0]);
                        } else {
                            Toast.makeText(getApplicationContext(), "文件丢失", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        file = new File(getFilesDir() + "/ChemicalPDF/" + tool_tool_title[3] + ".pdf");
                        if (file.exists()) {
                            startPDF(file.getPath(), tool_tool_title[0]);
                        } else {
                            Toast.makeText(getApplicationContext(), "文件丢失", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }
        });
    }

    private void startPDF(final String path, final String title) {

        Toast.makeText(this, "正在打开文件，请稍后......", Toast.LENGTH_SHORT).show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PDFViewer.class);
                intent.putExtra("filePath", path);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        }.start();

    }

    public void click(View view) {
        this.finish();
    }
}

