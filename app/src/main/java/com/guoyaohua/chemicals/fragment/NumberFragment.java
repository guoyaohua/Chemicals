package com.guoyaohua.chemicals.fragment;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.guoyaohua.chemicals.ChemicalDetailActivity;
import com.guoyaohua.chemicals.MyDatabaseHelper;
import com.guoyaohua.chemicals.R;
import com.guoyaohua.chemicals.Welcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by John Kwok on 2016/7/12.
 */
public class NumberFragment extends Fragment implements View.OnClickListener {
    public static EditText et_num_search = null;
    public static int state = 2;//初始状态为UN号检索。
    private final int CAS = 1;
    private final int UN = 2;
    private View mParent;
    private FragmentActivity mActivity;
    private ImageButton bt_UN;
    private ImageButton bt_CAS;
    private ImageButton bt_num_search;
    private ListView lv_num_searchResult;
    private SimpleAdapter simpleAdapter_search;
    private SimpleAdapter result_Adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_number_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParent = getView();
        mActivity = getActivity();
        et_num_search = (EditText) mParent.findViewById(R.id.et_num_search);
        bt_UN = (ImageButton) mParent.findViewById(R.id.bt_UN);
        bt_CAS = (ImageButton) mParent.findViewById(R.id.bt_CAS);
        bt_num_search = (ImageButton) mParent.findViewById(R.id.bt_num_search);
        lv_num_searchResult = (ListView) mParent.findViewById(R.id.lv_num_searchResult);
        bt_UN.setOnClickListener(this);
        bt_CAS.setOnClickListener(this);
        bt_num_search.setOnClickListener(this);

        et_num_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_num_search.getText().toString().isEmpty())
                    startSearch(et_num_search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_num_search.getText().toString().isEmpty())
                    lv_num_searchResult.setAdapter(simpleAdapter_search);
            }
        });
        lv_num_searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setTransitionName("name");
                TextView tv = (TextView) view.findViewById(R.id.num_item_name);
                String name = tv.getText().toString();

                Intent intent = new Intent();
                intent.setClass(getContext(), ChemicalDetailActivity.class);
                intent.putExtra("cn_name", name);
//                    startActivity(intent);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "name").toBundle());
            }
        });
        initData();

    }

    /**
     * 初始化listview
     */
    private void initData() {
        Cursor curson = Welcome.sqLite.rawQuery("select * from " +
                MyDatabaseHelper.TABLENAME +
                " where 1=1", null);  //提取数据库中所有化学品。
        if (curson != null) {
            //构建搜索所需要的Listview的adapter
            List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> map;
//            map = new HashMap<String, Object>();
//            map.put("cn_name", "名称");
//            map.put("CAS", "CAS编号");
//            map.put("UN", "UN编号");
//            listItem.add(map);
            curson.moveToNext();
            for (int i = 0; i < curson.getCount(); i++, curson.moveToNext()) {

                map = new HashMap<String, Object>();
                map.put("cn_name", curson.getString(curson.getColumnIndex("cn_name")));
                map.put("CAS", curson.getString(curson.getColumnIndex("CAS")));
                map.put("UN", curson.getString(curson.getColumnIndex("UN")));
                listItem.add(map);
            }
            curson.close();
            simpleAdapter_search = new SimpleAdapter(getContext(), listItem,
                    R.layout.numsearch_item_layout, new String[]{"cn_name", "CAS", "UN"},
                    new int[]{R.id.num_item_name, R.id.num_item_CAS, R.id.num_item_UN});
            lv_num_searchResult.setAdapter(simpleAdapter_search);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bt_CAS) {
            state = CAS;
            bt_UN.setImageDrawable(getResources().getDrawable(R.drawable.bt_un_unselected));
            bt_CAS.setImageDrawable(getResources().getDrawable(R.drawable.bt_cas_selected));
//            bt_UN.setBackgroundResource(R.color.colorPrimary);
//            bt_CAS.setBackgroundResource(R.color.colorAccent);
        } else if (v == bt_UN) {
            state = UN;
            bt_UN.setImageDrawable(getResources().getDrawable(R.drawable.bt_un_selected));//  gswwww5
            bt_CAS.setImageDrawable(getResources().getDrawable(R.drawable.bt_cas_unselected));
//            bt_CAS.setBackgroundResource(R.color.colorPrimary);
//            bt_UN.setBackgroundResource(R.color.colorAccent);
        } else if (v == bt_num_search) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_num_search.getWindowToken(), 0);
            if (!et_num_search.getText().toString().isEmpty()) {
                startSearch(et_num_search.getText().toString());
            } else {
                Toast.makeText(getContext(), "请先输入待查询编号", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startSearch(String searchWord) {
        Cursor cursor;
        switch (state) {
            case UN:
                cursor = Welcome.sqLite.rawQuery("select * from " +
                        MyDatabaseHelper.TABLENAME +
                        " where UN like '" + searchWord + "%'", null);  //提取数据库中所有化学品。
                if (cursor != null) {
                    //构建搜索所需要的Listview的adapter
                    List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                    HashMap<String, Object> map;
//                    map = new HashMap<String, Object>();
//                    map.put("cn_name", "名称");
//                    map.put("CAS", "CAS编号");
//                    map.put("UN", "UN编号");
//                    listItem.add(map);
                    cursor.moveToNext();
                    for (int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {

                        map = new HashMap<String, Object>();
                        map.put("cn_name", cursor.getString(cursor.getColumnIndex("cn_name")));
                        map.put("CAS", cursor.getString(cursor.getColumnIndex("CAS")));
                        map.put("UN", cursor.getString(cursor.getColumnIndex("UN")));
                        listItem.add(map);
                    }
                    cursor.close();
                    result_Adapter = new SimpleAdapter(getContext(), listItem,
                            R.layout.numsearch_item_layout, new String[]{"cn_name", "CAS", "UN"},
                            new int[]{R.id.num_item_name, R.id.num_item_CAS, R.id.num_item_UN});
                    lv_num_searchResult.setAdapter(result_Adapter);
                }
                break;
            case CAS:
                cursor = Welcome.sqLite.rawQuery("select * from " +
                        MyDatabaseHelper.TABLENAME +
                        " where CAS like '" + searchWord + "%'", null);  //提取数据库中所有化学品。
                if (cursor != null) {
                    //构建搜索所需要的Listview的adapter
                    List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                    HashMap<String, Object> map;
//                    map = new HashMap<String, Object>();
//                    map.put("cn_name", "名称");
//                    map.put("CAS", "CAS编号");
//                    map.put("UN", "UN编号");
//                    listItem.add(map);
                    cursor.moveToNext();
                    for (int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {
                        map = new HashMap<String, Object>();
                        map.put("cn_name", cursor.getString(cursor.getColumnIndex("cn_name")));
                        map.put("CAS", cursor.getString(cursor.getColumnIndex("CAS")));
                        map.put("UN", cursor.getString(cursor.getColumnIndex("UN")));
                        listItem.add(map);
                    }
                    cursor.close();
                    result_Adapter = new SimpleAdapter(getContext(), listItem,
                            R.layout.numsearch_item_layout, new String[]{"cn_name", "CAS", "UN"},
                            new int[]{R.id.num_item_name, R.id.num_item_CAS, R.id.num_item_UN});
                    lv_num_searchResult.setAdapter(result_Adapter);
                }
                break;


        }
    }


}
