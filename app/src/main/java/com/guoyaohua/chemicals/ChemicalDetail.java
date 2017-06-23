package com.guoyaohua.chemicals;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by John Kwok on 2016/7/17.
 */
public class ChemicalDetail extends AppCompatActivity implements View.OnClickListener {
    Cursor c = null;
    List<HashMap<String, Object>> listItem1;
    List<HashMap<String, Object>> listItem2;
    List<HashMap<String, Object>> listItem3;
    List<HashMap<String, Object>> listItem4;
    List<HashMap<String, Object>> listItem5;

    private ListView lv_detail;
    private ImageButton bt_detail_info;
    private ImageButton bt_detail_features;
    private ImageButton bt_detail_danger;
    private ImageButton bt_detail_protect;
    private ImageButton bt_detail_disposal;
    private ImageButton bt_detail_detection;
    private SimpleAdapter adapter1 = null;
    private SimpleAdapter adapter2 = null;
    private SimpleAdapter adapter3 = null;
    private SimpleAdapter adapter4 = null;
    private SimpleAdapter adapter5 = null;
    String cn_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_detail_layout);
        bt_detail_info = (ImageButton) findViewById(R.id.bt_detail_info);
        bt_detail_features = (ImageButton) findViewById(R.id.bt_detail_features);
        bt_detail_danger = (ImageButton) findViewById(R.id.bt_detail_danger);
        bt_detail_protect = (ImageButton) findViewById(R.id.bt_detail_protect);
        bt_detail_disposal = (ImageButton) findViewById(R.id.bt_detail_disposal);
        bt_detail_detection = (ImageButton) findViewById(R.id.bt_detail_detection);
        lv_detail = (ListView) findViewById(R.id.lv_detail);
        bt_detail_info.setOnClickListener(this);
        bt_detail_features.setOnClickListener(this);
        bt_detail_danger.setOnClickListener(this);
        bt_detail_protect.setOnClickListener(this);
        bt_detail_disposal.setOnClickListener(this);
        bt_detail_detection.setOnClickListener(this);

        cn_name = this.getIntent().getExtras().getString("cn_name");//获得Intent传过来的化学品名称。

        this.setTitle(cn_name);
        initData();//初始化各个Adapter
    }

    /**
     * 初始化各个Adapter 、、、、
     */
    private void initData() {
        c = Welcome.sqLite.rawQuery("select * from " +
                MyDatabaseHelper.TABLENAME +
                " where cn_name = '" + cn_name + "'", null);
        if (c != null) {
/*

            //Successfully obtain all information of database.
            c.moveToNext();
            String buffer = "化学品名称：" + c.getString(c.getColumnIndex("cn_name"))
                    + '\n' + "英文名：" + c.getString(c.getColumnIndex("en_name"))
                    + '\n' + "别名/商用名：" + c.getString(c.getColumnIndex("byname"))
                    + '\n' + "UN号：" + c.getString(c.getColumnIndex("UN"))
                    + '\n' + "CAS号：" + c.getString(c.getColumnIndex("CAS"))
                    + '\n' + "GHS危害分类：" + c.getString(c.getColumnIndex("class"))
                    + '\n' + "分子式：" + c.getString(c.getColumnIndex("formula"))
                    + '\n' + "特性描述：" + c.getString(c.getColumnIndex("characterization"))
                    + '\n' + "主要用途：" + c.getString(c.getColumnIndex("application"))
                    + '\n' + "危险性类别：" + c.getString(c.getColumnIndex("risk_categories"))
                    + '\n' + "燃烧及爆炸危险性：" + c.getString(c.getColumnIndex("danger"))
                    + '\n' + "健康危害：" + c.getString(c.getColumnIndex("health_hazard"))
                    + '\n' + "环境影响：" + c.getString(c.getColumnIndex("environmental"))
                    + '\n' + "个人防护建议（NIOSH）：" + c.getString(c.getColumnIndex("protect"))
                    + '\n' + "急救：" + c.getString(c.getColumnIndex("aid"))
                    + '\n' + "灭火：" + c.getString(c.getColumnIndex("fire"))
                    + '\n' + "泄漏处置：" + c.getString(c.getColumnIndex("leak"))
                    + '\n' + "疏散和隔离（ERG）：" + c.getString(c.getColumnIndex("evacuate"))
                    + '\n' + "环境应急：" + c.getString(c.getColumnIndex("emergency"))
                    + '\n' + "废物处置：" + c.getString(c.getColumnIndex("disposal"));
            c.close();
*/
            c.moveToNext();//将光标下移一个单位
            //初始化adapter1
            listItem1 = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> map;

            map = new HashMap<String, Object>();
            map.put("tv_1", "化学品名称");
            map.put("tv_2", c.getString(c.getColumnIndex("cn_name")));
            listItem1.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "英文名");
            map.put("tv_2", c.getString(c.getColumnIndex("en_name")));
            listItem1.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "别名/商用名");
            map.put("tv_2", c.getString(c.getColumnIndex("byname")));
            listItem1.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "UN号");
            map.put("tv_2", c.getString(c.getColumnIndex("UN")));
            listItem1.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "CAS号");
            map.put("tv_2", c.getString(c.getColumnIndex("CAS")));
            listItem1.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "GHS危害分类");
            map.put("tv_2", c.getString(c.getColumnIndex("class")));
            listItem1.add(map);


            //初始化adapter2
            listItem2 = new ArrayList<HashMap<String, Object>>();
            map = new HashMap<String, Object>();
            map.put("tv_1", "分子式");
            map.put("tv_2", c.getString(c.getColumnIndex("formula")));
            listItem2.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "特性描述");
            map.put("tv_2", c.getString(c.getColumnIndex("characterization")));
            listItem2.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "主要用途");
            map.put("tv_2", c.getString(c.getColumnIndex("application")));
            listItem2.add(map);

            //初始化adapter3
            listItem3 = new ArrayList<HashMap<String, Object>>();
            map = new HashMap<String, Object>();
            map.put("tv_1", "危险性类别");
            map.put("tv_2", c.getString(c.getColumnIndex("risk_categories")));
            listItem3.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "燃烧及爆炸危险性");
            map.put("tv_2", c.getString(c.getColumnIndex("danger")));
            listItem3.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "健康危害");
            map.put("tv_2", c.getString(c.getColumnIndex("health_hazard")));
            listItem3.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "环境影响");
            map.put("tv_2", c.getString(c.getColumnIndex("environmental")));
            listItem3.add(map);

            //初始化adapter4
            listItem4 = new ArrayList<HashMap<String, Object>>();

            map = new HashMap<String, Object>();
            map.put("tv_1", "个人防护建议");
            map.put("tv_2", c.getString(c.getColumnIndex("protect")));
            listItem4.add(map);

            //初始化adapter5
            listItem5 = new ArrayList<HashMap<String, Object>>();

            map = new HashMap<String, Object>();
            map.put("tv_1", "急救");
            map.put("tv_2", c.getString(c.getColumnIndex("aid")));
            listItem5.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "灭火");
            map.put("tv_2", c.getString(c.getColumnIndex("fire")));
            listItem5.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "泄漏处置");
            map.put("tv_2", c.getString(c.getColumnIndex("leak")));
            listItem5.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "疏散和隔离");
            map.put("tv_2", c.getString(c.getColumnIndex("evacuate")));
            listItem5.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "环境应急");
            map.put("tv_2", c.getString(c.getColumnIndex("emergency")));
            listItem5.add(map);

            map = new HashMap<String, Object>();
            map.put("tv_1", "废物处置");
            map.put("tv_2", c.getString(c.getColumnIndex("disposal")));
            listItem5.add(map);
            //关闭cursor
            c.close();

            //初始化ListView的adapter
            adapter1 = new SimpleAdapter(this, listItem1,
                    R.layout.lv_detail_item_layout, new String[]{"tv_1", "tv_2"}, new int[]{R.id.tv_detail_1, R.id.tv_detail_2});
            lv_detail.setAdapter(adapter1);

        }
    }

    @Override
    public void onClick(View v) {
        bt_detail_info.setImageDrawable(getResources().getDrawable(R.drawable.jbxx_uns));
        bt_detail_features.setImageDrawable(getResources().getDrawable(R.drawable.lhtxjyt_uns));
        bt_detail_danger.setImageDrawable(getResources().getDrawable(R.drawable.wxx_uns));
        bt_detail_protect.setImageDrawable(getResources().getDrawable(R.drawable.fhjy_uns));
        bt_detail_disposal.setImageDrawable(getResources().getDrawable(R.drawable.yjcz_uns));
        bt_detail_detection.setImageDrawable(getResources().getDrawable(R.drawable.jcff_uns));
//        bt_detail_info.(R.drawable.jbxx_uns);
//        bt_detail_features.setBackgroundResource(R.drawable.lhtxjyt_uns);
//        bt_detail_danger.setBackgroundResource(R.drawable.wxx_uns);
//        bt_detail_protect.setBackgroundResource(R.drawable.fhjy_uns);
//        bt_detail_disposal.setBackgroundResource(R.drawable.yjcz_uns);
//        bt_detail_detection.setBackgroundResource(R.drawable.jcff_uns);
        if (v == bt_detail_info) {
//            bt_detail_info.setBackgroundResource(R.drawable.jbxx);
            bt_detail_info.setImageDrawable(getResources().getDrawable(R.drawable.jbxx));
            lv_detail.setAdapter(adapter1);
        } else if (v == bt_detail_features) {
            bt_detail_features.setImageDrawable(getResources().getDrawable(R.drawable.lhtxjyt));
//            bt_detail_features.setBackgroundResource(R.drawable.lhtxjyt);
            if (adapter2 == null)
                adapter2 = new SimpleAdapter(this, listItem2,
                        R.layout.lv_detail_item_layout, new String[]{"tv_1", "tv_2"}, new int[]{R.id.tv_detail_1, R.id.tv_detail_2});
            lv_detail.setAdapter(adapter2);
        } else if (v == bt_detail_danger) {
//            bt_detail_danger.setBackgroundResource(R.drawable.wxx);
            bt_detail_danger.setImageDrawable(getResources().getDrawable(R.drawable.wxx));
            if (adapter3 == null)
                adapter3 = new SimpleAdapter(this, listItem3,
                        R.layout.lv_detail_item_layout, new String[]{"tv_1", "tv_2"}, new int[]{R.id.tv_detail_1, R.id.tv_detail_2});
            lv_detail.setAdapter(adapter3);
        } else if (v == bt_detail_protect) {
//            bt_detail_protect.setBackgroundResource(R.drawable.fhjy);
            bt_detail_protect.setImageDrawable(getResources().getDrawable(R.drawable.fhjy));
            if (adapter4 == null)
                adapter4 = new SimpleAdapter(this, listItem4,
                        R.layout.lv_detail_item_layout, new String[]{"tv_1", "tv_2"}, new int[]{R.id.tv_detail_1, R.id.tv_detail_2});
            lv_detail.setAdapter(adapter4);
        } else if (v == bt_detail_disposal) {
//            bt_detail_disposal.setBackgroundResource(R.drawable.yjcz);
            bt_detail_disposal.setImageDrawable(getResources().getDrawable(R.drawable.yjcz));
            if (adapter5 == null)
                adapter5 = new SimpleAdapter(this, listItem5,
                        R.layout.lv_detail_item_layout, new String[]{"tv_1", "tv_2"}, new int[]{R.id.tv_detail_1, R.id.tv_detail_2});
            lv_detail.setAdapter(adapter5);
        } else if (v == bt_detail_detection) {
//            bt_detail_detection.setBackgroundResource(R.drawable.jcff);
            bt_detail_detection.setImageDrawable(getResources().getDrawable(R.drawable.jcff));
            Toast.makeText(ChemicalDetail.this, "功能待加入", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ChemicalDetailActivity.class);
            startActivity(intent);
        }
    }
}