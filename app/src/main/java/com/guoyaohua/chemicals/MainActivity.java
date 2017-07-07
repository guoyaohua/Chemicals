package com.guoyaohua.chemicals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.epapyrus.plugpdf.core.PlugPDF;
import com.epapyrus.plugpdf.core.PlugPDFException;
import com.guoyaohua.chemicals.fragment.FuzzyFragment;
import com.guoyaohua.chemicals.fragment.GHSFragment;
import com.guoyaohua.chemicals.fragment.NameFragment;
import com.guoyaohua.chemicals.fragment.NumberFragment;
import com.guoyaohua.chemicals.fragment.ToolFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnPageChangeListener {

    ViewPager pager = null;
    ArrayList<Fragment> fragments = null;
    PagerAdapter adapter = null;
    int curPosition = 0;
    private ImageButton bt_icon = null;
    private ImageButton bt_name = null;
    private ImageButton bt_number = null;
    private ImageButton bt_fuzzy = null;
    private ImageButton bt_tool = null;
    private boolean mBackKeyPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //为了防止软键盘弹出时影响布局。
        this.setTitle("GHS分类检索");
        getSupportActionBar().setIcon(R.drawable.icon);
//        mFragments = new Fragment[5];
//        mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_icon);
//        mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_name);
//        mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_number);
//        mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_fuzzy);
//        mFragments[4] = getSupportFragmentManager().findFragmentById(R.id.fragment_tool);
        //初始化PulgPDF SDK
        try {
            // Initialize PlugPDF with a license key.
            PlugPDF.init(getApplicationContext(),
                    "43F5EGBB7GG27BF8ED3459C9GFD5G3HAA35HG97DF6AHEAAGAH9DFBF5");
        } catch (PlugPDFException.InvalidLicense ex) {
            Log.e("PlugPDF", "error ", ex);
            // Handle invalid license exceptions.
        }

        bt_icon = (ImageButton) findViewById(R.id.ib_icon);
        bt_name = (ImageButton) findViewById(R.id.ib_name);
        bt_number = (ImageButton) findViewById(R.id.ib_number);
        bt_fuzzy = (ImageButton) findViewById(R.id.ib_fuzzy);
        bt_tool = (ImageButton) findViewById(R.id.ib_tool);

        pager = (ViewPager) findViewById(R.id.viewPager);

        bt_icon.setOnClickListener(this);
        bt_name.setOnClickListener(this);
        bt_number.setOnClickListener(this);
        bt_tool.setOnClickListener(this);
        bt_fuzzy.setOnClickListener(this);
        pager.setOnPageChangeListener(this);
        initFragment();

        initPager();
        App app = (App) getApplication();//获取应用程序全局的实例引用
        app.activities.add(this);  //把当前Activity放入集合中

    }

//    public void setFragmentIndicator(int whichIsDefault) {
//        getSupportFragmentManager().beginTransaction().hide(mFragments[0])
//                .hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]).hide(mFragments[4]).show(mFragments[whichIsDefault]).commit();
//    }


    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new GHSFragment());
        fragments.add(new NameFragment());
        fragments.add(new NumberFragment());
        fragments.add(new FuzzyFragment());
        fragments.add(new ToolFragment());
    }

    /**
     * 初始化ViewPagerAdapter
     */
    private void initPager() {
        adapter = new ViewPagerAdapter(MainActivity.this, getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {  //此处应该改变按钮背景状态。
        if (v == bt_icon) {

            pager.setCurrentItem(0);
        }
        if (v == bt_name) {

            pager.setCurrentItem(1);
        }
        if (v == bt_number) {

            pager.setCurrentItem(2);
        }
        if (v == bt_fuzzy) {

            pager.setCurrentItem(3);
        }
        if (v == bt_tool) {

            pager.setCurrentItem(4);

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.i("666666","onPageScrolled响应"+position);
//        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 当界面改变时，无论是滑动还是直接点击按钮，都会触发该方法，在这个方法里可以进行修改底部按钮状态，也可以初始化fragment。
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
//修改底部按钮图标状态
        changeButtonIcon(position);
        curPosition = position;
        switch (position) {
            case 0:
                this.setTitle("GHS分类检索");
                break;
            case 1:
                this.setTitle("名称搜索");
                break;
            case 2:
                this.setTitle("编号搜索");
                break;
            case 3:
                this.setTitle("模糊搜索");
                break;
            case 4:
                this.setTitle("工具");
                break;
        }
    }

    private void changeButtonIcon(int position) {
        bt_fuzzy.setBackgroundResource(R.drawable.bt_fuzzy_unselected);
        bt_icon.setBackgroundResource(R.drawable.bt_icon_unselected);
        bt_number.setBackgroundResource(R.drawable.bt_number_unselected);
        bt_name.setBackgroundResource(R.drawable.bt_name_unselected);
        bt_tool.setBackgroundResource(R.drawable.bt_tool_unselected);
        switch (position) {
            case 0:
                bt_icon.setBackgroundResource(R.drawable.bt_icon_selected);
                break;
            case 1:
                bt_name.setBackgroundResource(R.drawable.bt_name_selected);
                break;
            case 2:
                bt_number.setBackgroundResource(R.drawable.bt_number_selected);
                break;
            case 3:
                bt_fuzzy.setBackgroundResource(R.drawable.bt_fuzzy_selected);
                break;
            case 4:
                bt_tool.setBackgroundResource(R.drawable.bt_tool_selected);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.i("666666","onPageScrollStateChanged响应"+state);
    }

    /**
     * 捕获返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            switch (curPosition) {
                case 0:
                    if (GHSFragment.state == GHSFragment.STATE_NAME) {
                        GHSFragment.state = GHSFragment.STATE_GHS;
                        GHSFragment.lv_GHS.setAdapter(GHSFragment.adapter_GHS);
                    } else {
                        doubleClickBack();
                    }
                    break;
                case 1:
                    if (NameFragment.state == 3) {
                        NameFragment.et_search.setText("");
                    } else {
                        doubleClickBack();
                    }
                    break;
                case 2:
                    if (!NumberFragment.et_num_search.getText().toString().isEmpty()) {
                        NumberFragment.et_num_search.setText("");
                    } else {
                        doubleClickBack();
                    }
                    break;
                case 3:
                    doubleClickBack();
                    break;
                case 4:
                    doubleClickBack();
                    break;
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void doubleClickBack() {
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            //退出程序
            this.finish();
            System.exit(0);
        }

    }
}