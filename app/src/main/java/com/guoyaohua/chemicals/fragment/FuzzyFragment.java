package com.guoyaohua.chemicals.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
public class FuzzyFragment extends Fragment {
    public EditText et_fuzzy_search;
    SimpleAdapter result_Adapter;
    String searchLine[] = {"characterization", "danger", "health_hazard", "environmental"};//用于标识在数据库中那些列搜索关键字。
    int index = 0; //把checkbox当作radioButton来使用，通过index来判断当前的选择状态。
    private View mParent;
    private FragmentActivity mActivity;
    private ImageButton bt_fuzzy_search;
    private ListView lv_fuzzy_searchResult;
    private CheckBox cb_1;//characterization
    private CheckBox cb_2;//danger
    private CheckBox cb_3;//health_hazard
    private CheckBox cb_4;//environmental

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuzzy_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mParent = getView();
        mActivity = getActivity();
        et_fuzzy_search = (EditText) mParent.findViewById(R.id.et_fuzzy_search);
        bt_fuzzy_search = (ImageButton) mParent.findViewById(R.id.bt_fuzzy_search);
        lv_fuzzy_searchResult = (ListView) mParent.findViewById(R.id.lv_fuzzy_searchResult);

        cb_1 = (CheckBox) mParent.findViewById(R.id.cb_1);
        cb_2 = (CheckBox) mParent.findViewById(R.id.cb_2);
        cb_3 = (CheckBox) mParent.findViewById(R.id.cb_3);
        cb_4 = (CheckBox) mParent.findViewById(R.id.cb_4);

        cb_1.setOnClickListener(new MyOnClickListener());
        cb_2.setOnClickListener(new MyOnClickListener());
        cb_3.setOnClickListener(new MyOnClickListener());
        cb_4.setOnClickListener(new MyOnClickListener());


//        cb_1.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
//        cb_2.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
//        cb_3.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
//        cb_4.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        bt_fuzzy_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_fuzzy_search.getWindowToken(), 0);
                if (!et_fuzzy_search.getText().toString().isEmpty()) {
                    startSearch(et_fuzzy_search.getText().toString());
                }
            }
        });
        lv_fuzzy_searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    TextView tv = (TextView) view.findViewById(R.id.num_item_name);
                    String args = tv.getText().toString();
                    String name = (String) tv.getText();
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ChemicalDetailActivity.class);
                    intent.putExtra("cn_name", name);
                    startActivity(intent);
                   /* Intent intent = new Intent();
                    intent.setClass(getContext(), ChemicalDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cn_name", args);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                }
            }
        });

    }

    private void startSearch(String searchWord) {
//        String lines = "";
//        if (searchLine[0] != "") {
//            lines = searchLine[0];
//            if (searchLine[1] != "")
//                lines = lines + " or " + searchLine[1];
//            if (searchLine[2] != "")
//                lines = lines + " or " + searchLine[2];
//            if (searchLine[3] != "")
//                lines = lines + " or " + searchLine[3];
//        } else if (searchLine[1] != "") {
//            lines = searchLine[1];
//            if (searchLine[2] != "")
//                lines = lines + " or " + searchLine[2];
//            if (searchLine[3] != "")
//                lines = lines + " or " + searchLine[3];
//        } else if (searchLine[2] != "") {
//            lines = searchLine[2];
//            if (searchLine[3] != "")
//                lines = lines + " or " + searchLine[3];
//        }
//        Log.i("lines", lines);

        Cursor cursor = Welcome.sqLite.rawQuery("select * from " +
                MyDatabaseHelper.TABLENAME +
                " where " + searchLine[index] + " like '%" + searchWord + "%'", null);  //提取数据库中所有化学品。
        if (cursor != null) {
            //构建搜索所需要的Listview的adapter
            List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> map;
            map = new HashMap<String, Object>();
            map.put("cn_name", "名称");
            map.put("CAS", "CAS编号");
            map.put("UN", "UN编号");
            listItem.add(map);
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
            lv_fuzzy_searchResult.setAdapter(result_Adapter);
        }
    }



    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cb_1:
                        index=0;
                        cb_2.setChecked(false);
                        cb_3.setChecked(false);
                        cb_4.setChecked(false);
                        cb_1.setChecked(true);
                    break;
                case R.id.cb_2:
                        index=1;
                        cb_1.setChecked(false);
                        cb_3.setChecked(false);
                        cb_4.setChecked(false);
                        cb_2.setChecked(true);
                    break;
                case R.id.cb_3:
                        index=2;
                        cb_1.setChecked(false);
                        cb_2.setChecked(false);
                        cb_4.setChecked(false);
                        cb_3.setChecked(true);
                    break;
                case R.id.cb_4:
                        index=3;
                        cb_1.setChecked(false);
                        cb_2.setChecked(false);
                        cb_3.setChecked(false);
                        cb_4.setChecked(true);
                    break;
            }
        }
    }



 /*   class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_1:
                    if (isChecked) {
                        index=0;
                        cb_2.setChecked(false);
                        cb_3.setChecked(false);
                        cb_4.setChecked(false);
                    } else {
                        cb_1.setChecked(true);
                    }

                    break;
                case R.id.cb_2:
                    if (isChecked) {
                        index=1;
                        cb_1.setChecked(false);
                        cb_3.setChecked(false);
                        cb_4.setChecked(false);
                    } else {
                        cb_2.setChecked(true);
                    }
                    break;
                case R.id.cb_3:
                    if (isChecked) {
                        index=2;
                        cb_1.setChecked(false);
                        cb_2.setChecked(false);
                        cb_4.setChecked(false);
                    } else {
                        cb_3.setChecked(true);
                    }
                    break;
                case R.id.cb_4:
                    if (isChecked) {
                        index=3;
                        cb_1.setChecked(false);
                        cb_2.setChecked(false);
                        cb_3.setChecked(false);
                    } else {
                        cb_4.setChecked(true);
                        cb_4.setSelected(false);
                    }
                    break;
            }

        }
    }
*/
}
