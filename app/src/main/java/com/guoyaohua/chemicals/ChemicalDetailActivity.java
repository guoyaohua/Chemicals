package com.guoyaohua.chemicals;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.guoyaohua.chemicals.fragment.DangerFragment;
import com.guoyaohua.chemicals.fragment.DisposalFragment;
import com.guoyaohua.chemicals.fragment.FeatureFragment;
import com.guoyaohua.chemicals.fragment.InformationFrgment;
import com.guoyaohua.chemicals.fragment.ProtectFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by John Kwok on 2016/9/9.
 */
public class ChemicalDetailActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public static HashMap<String, Object> chemical_detail_map;//用于保存从数据库中查询得到的化学品详细信息。
    Cursor c = null;
    ViewPager pager = null;
    ArrayList<Fragment> fragments = null;

    FragmentPagerAdapter fragmentPagerAdapter;
    int curPosition = 0;
    String cn_name;
    private ImageButton bt_detail_info;
    private ImageButton bt_detail_features;
    private ImageButton bt_detail_danger;
    private ImageButton bt_detail_protect;
    private ImageButton bt_detail_disposal;
    private ImageButton bt_detail_detection;
    private HorizontalScrollView ScrollView;
    private TextView chemical_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_detail_activity_layout);
        getSupportActionBar().hide();
        initWidget();
        cn_name = this.getIntent().getExtras().getString("cn_name");//获得Intent传过来的化学品名称。
        chemical_name.setText(cn_name);
//        this.setTitle(cn_name);
        initData();//初始化chemical_detail_map。
        initFragment();

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        pager.setAdapter(fragmentPagerAdapter);

    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new InformationFrgment());
        fragments.add(new FeatureFragment());
        fragments.add(new DangerFragment());
        fragments.add(new ProtectFragment());
        fragments.add(new DisposalFragment());
    }

    private void initData() {
        c = Welcome.sqLite.rawQuery("select * from " +
                MyDatabaseHelper.TABLENAME +
                " where cn_name = '" + cn_name + "'", null);
        if (c != null) {
            c.moveToNext();//将光标下移一个单位
            chemical_detail_map = new HashMap<String, Object>();

            chemical_detail_map.put("cn_name", c.getString(c.getColumnIndex("cn_name")));
            chemical_detail_map.put("byname", c.getString(c.getColumnIndex("byname")));
            chemical_detail_map.put("en_name", c.getString(c.getColumnIndex("en_name")));
            chemical_detail_map.put("UN", c.getString(c.getColumnIndex("UN")));
            chemical_detail_map.put("CAS", c.getString(c.getColumnIndex("CAS")));
            chemical_detail_map.put("dg_code", c.getString(c.getColumnIndex("dg_code")));
            chemical_detail_map.put("GHS", c.getString(c.getColumnIndex("GHS")));
            chemical_detail_map.put("formula", c.getString(c.getColumnIndex("formula")));
            chemical_detail_map.put("weight", c.getString(c.getColumnIndex("weight")));
            chemical_detail_map.put("melting_point", c.getString(c.getColumnIndex("melting_point")));
            chemical_detail_map.put("boiling_point", c.getString(c.getColumnIndex("boiling_point")));
            chemical_detail_map.put("flash_point", c.getString(c.getColumnIndex("flash_point")));
            chemical_detail_map.put("freezing_point", c.getString(c.getColumnIndex("freezing_point")));
            chemical_detail_map.put("vapour_pressure", c.getString(c.getColumnIndex("vapour_pressure")));
            chemical_detail_map.put("vapour_density", c.getString(c.getColumnIndex("vapour_density")));
            chemical_detail_map.put("gas_density", c.getString(c.getColumnIndex("gas_density")));
            chemical_detail_map.put("relative_density", c.getString(c.getColumnIndex("relative_density")));
            chemical_detail_map.put("critical_pressure", c.getString(c.getColumnIndex("critical_pressure")));
            chemical_detail_map.put("critical_temperature", c.getString(c.getColumnIndex("critical_temperature")));
            chemical_detail_map.put("ionization_potential", c.getString(c.getColumnIndex("ionization_potential")));
            chemical_detail_map.put("explosion_limit", c.getString(c.getColumnIndex("explosion_limit")));
            chemical_detail_map.put("autogenous_ignition", c.getString(c.getColumnIndex("autogenous_ignition")));
            chemical_detail_map.put("ignition_energy", c.getString(c.getColumnIndex("ignition_energy")));
            chemical_detail_map.put("characterization", c.getString(c.getColumnIndex("characterization")));
            chemical_detail_map.put("application", c.getString(c.getColumnIndex("application")));
            chemical_detail_map.put("risk_categories", c.getString(c.getColumnIndex("risk_categories")));
            chemical_detail_map.put("danger", c.getString(c.getColumnIndex("danger")));
            chemical_detail_map.put("health_hazard", c.getString(c.getColumnIndex("health_hazard")));
            chemical_detail_map.put("environmental", c.getString(c.getColumnIndex("environmental")));
            chemical_detail_map.put("protect", c.getString(c.getColumnIndex("protect")));
            chemical_detail_map.put("aid", c.getString(c.getColumnIndex("aid")));
            chemical_detail_map.put("fire", c.getString(c.getColumnIndex("fire")));
            chemical_detail_map.put("leak", c.getString(c.getColumnIndex("leak")));
            chemical_detail_map.put("evacuate", c.getString(c.getColumnIndex("evacuate")));
            chemical_detail_map.put("emergency", c.getString(c.getColumnIndex("emergency")));
            chemical_detail_map.put("disposal", c.getString(c.getColumnIndex("disposal")));

            //关闭cursor
            c.close();
        }
    }

    private void initWidget() {

        chemical_name = (TextView) findViewById(R.id.tv_chemical_name);
        bt_detail_info = (ImageButton) findViewById(R.id.ib_detail_info);
        bt_detail_features = (ImageButton) findViewById(R.id.ib_detail_features);
        bt_detail_danger = (ImageButton) findViewById(R.id.ib_detail_danger);
        bt_detail_protect = (ImageButton) findViewById(R.id.ib_detail_protect);
        bt_detail_disposal = (ImageButton) findViewById(R.id.ib_detail_disposal);
        bt_detail_detection = (ImageButton) findViewById(R.id.ib_detail_detection);
        pager = (ViewPager) findViewById(R.id.viewpager_chemical_detail);
        ScrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        pager.setOnPageChangeListener(this);
        bt_detail_info.setOnClickListener(this);
        bt_detail_features.setOnClickListener(this);
        bt_detail_danger.setOnClickListener(this);
        bt_detail_protect.setOnClickListener(this);
        bt_detail_disposal.setOnClickListener(this);
        bt_detail_detection.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        bt_detail_info.setImageDrawable(getResources().getDrawable(R.drawable.jbxx_uns));
        bt_detail_features.setImageDrawable(getResources().getDrawable(R.drawable.lhtxjyt_uns));
        bt_detail_danger.setImageDrawable(getResources().getDrawable(R.drawable.wxx_uns));
        bt_detail_protect.setImageDrawable(getResources().getDrawable(R.drawable.fhjy_uns));
        bt_detail_disposal.setImageDrawable(getResources().getDrawable(R.drawable.yjcz_uns));
        bt_detail_detection.setImageDrawable(getResources().getDrawable(R.drawable.jcff_uns));

        if (v == bt_detail_info) {
            bt_detail_info.setImageDrawable(getResources().getDrawable(R.drawable.jbxx));
            pager.setCurrentItem(0);
        } else if (v == bt_detail_features) {
            bt_detail_features.setImageDrawable(getResources().getDrawable(R.drawable.lhtxjyt));
            pager.setCurrentItem(1);
        } else if (v == bt_detail_danger) {
            bt_detail_danger.setImageDrawable(getResources().getDrawable(R.drawable.wxx));
            pager.setCurrentItem(2);
        } else if (v == bt_detail_protect) {
            bt_detail_protect.setImageDrawable(getResources().getDrawable(R.drawable.fhjy));
            pager.setCurrentItem(3);
        } else if (v == bt_detail_disposal) {
            bt_detail_disposal.setImageDrawable(getResources().getDrawable(R.drawable.yjcz));
            pager.setCurrentItem(4);
        } else if (v == bt_detail_detection) {
            bt_detail_detection.setImageDrawable(getResources().getDrawable(R.drawable.jcff));
//            Toast.makeText(this, "功能待加入", Toast.LENGTH_SHORT).show();
            test();
        }
    }

    private void test() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory() + "/ChemicalPDF/test.pdf");
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        } else {
            Toast.makeText(this, "您的手机无法显示该文件", Toast.LENGTH_SHORT).show();
        }


    }

    public void changeButtonIcon(int curPosition) {
        bt_detail_info.setImageDrawable(getResources().getDrawable(R.drawable.jbxx_uns));
        bt_detail_features.setImageDrawable(getResources().getDrawable(R.drawable.lhtxjyt_uns));
        bt_detail_danger.setImageDrawable(getResources().getDrawable(R.drawable.wxx_uns));
        bt_detail_protect.setImageDrawable(getResources().getDrawable(R.drawable.fhjy_uns));
        bt_detail_disposal.setImageDrawable(getResources().getDrawable(R.drawable.yjcz_uns));
        bt_detail_detection.setImageDrawable(getResources().getDrawable(R.drawable.jcff_uns));

        switch (curPosition) {
            case 0:
                bt_detail_info.setImageDrawable(getResources().getDrawable(R.drawable.jbxx));
                break;
            case 1:
                bt_detail_features.setImageDrawable(getResources().getDrawable(R.drawable.lhtxjyt));
                break;
            case 2:
                bt_detail_danger.setImageDrawable(getResources().getDrawable(R.drawable.wxx));
                break;
            case 3:
                bt_detail_protect.setImageDrawable(getResources().getDrawable(R.drawable.fhjy));
                break;
            case 4:
                bt_detail_disposal.setImageDrawable(getResources().getDrawable(R.drawable.yjcz));
                break;
            case 5:
                bt_detail_detection.setImageDrawable(getResources().getDrawable(R.drawable.jcff));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//修改底部按钮图标状态
        changeButtonIcon(position);
        if (position == 3 || position == 4 || position == 5) {
            ScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
        } else {
            ScrollView.fullScroll(ScrollView.FOCUS_LEFT);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
