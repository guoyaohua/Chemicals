package com.guoyaohua.chemicals.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.guoyaohua.chemicals.ChemicalDetail;
import com.guoyaohua.chemicals.ChemicalDetailActivity;
import com.guoyaohua.chemicals.MyDatabaseHelper;
import com.guoyaohua.chemicals.R;
import com.guoyaohua.chemicals.Welcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by John Kwok on 2016/7/12.
 */
public class GHSFragment extends Fragment {
    public static final int STATE_GHS = 1;
    public static final int STATE_NAME = 2;
    public static int state = 1;
    public static ListView lv_GHS;
    public static SimpleAdapter adapter_GHS;
    public static SimpleAdapter adapter_name;
    String[] GHS = {"爆炸品", "压缩气体和液化气体", "易燃液体", "易燃固体", "氧化剂", "毒害品", "放射性物品", "腐蚀品", "其他化学品"};
    String[] GHS_count = {"11", "8", "16", "4", "6", "9", "3", "8", "2"};
    List<List<HashMap<String, Object>>> data_list; //用来保存返回的各个分类中化学品的名称。
    private View mParent;
    private FragmentActivity mActivity;
    private List<Map<String, Object>> GHS_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_icon_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        state=1;
        mParent = getView();
        mActivity = getActivity();
        lv_GHS = (ListView) mParent.findViewById(R.id.lv_GHS);
        data_list = new ArrayList<List<HashMap<String, Object>>>();
        GHS_list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 9; i++) {
            data_list.add(startSearch(GHS[i]));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("GHS_name", GHS[i]);
//            if(!data_list.isEmpty()) {
//                map.put("count", data_list.get(i).size());//用于存放各种类化学品的数目
                map.put("count", GHS_count[i]);//用于存放各种类化学品的数目
//            }else{
//                map.put("count", 0);
//           }
            GHS_list.add(map);
        }
        adapter_GHS = new SimpleAdapter(getContext(), GHS_list,
                R.layout.ghs_item_layout, new String[]{"GHS_name", "count"},
                new int[]{R.id.GHS_item_name, R.id.GHS_item_count});
        lv_GHS.setAdapter(adapter_GHS);
        lv_GHS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (state) {
                    case STATE_GHS:
                        adapter_name = new SimpleAdapter(getContext(), data_list.get(position),
                                R.layout.ghs_item_layout, new String[]{"cn_name"},
                                new int[]{R.id.GHS_item_name});
                        lv_GHS.setAdapter(adapter_name);
//                        lv_GHS.notify();
                        state = STATE_NAME;
                        break;
                    case STATE_NAME:
                        TextView tv = (TextView) view.findViewById(R.id.GHS_item_name);
                        String name = (String) tv.getText();
                        Intent intent = new Intent();
                        intent.setClass(getContext(), ChemicalDetailActivity.class);
                        intent.putExtra("cn_name", name);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    /**
     * this function is used to search data from database
     * 当该fragment初始化时，程序会自动搜索数据库中class列，
     * 返回满足该条件的化学品个数及名称，保存到集合里。以备点击使用。
     *
     * @param searchWord
     */
    private List<HashMap<String, Object>> startSearch(String searchWord) {
        List<HashMap<String, Object>> listItem = null;
        Cursor cursor = null;
        try{
            cursor = Welcome.sqLite.rawQuery("select * from " +
                    MyDatabaseHelper.TABLENAME +
                    " where GHS like '%" + searchWord + "%'", null);  //提取数据库中所有化学品。
        }catch (Exception e){
            e.printStackTrace();
        }

        if (cursor != null) {
            //构建搜索所需要的Listview的adapter
            listItem = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> map;
//            map = new HashMap<String, Object>();
//            map.put("cn_name", "名称");
//            map.put("CAS", "CAS编号");
//            map.put("UN", "UN编号");
//            listItem.add(map);
            cursor.moveToNext();
            for (int i = 0; i < cursor.getCount(); i++, cursor.moveToNext()) {

                map = new HashMap<String, Object>();
                map.put("cn_name", cursor.getString(cursor.getColumnIndex("cn_name")));
                map.put("CAS", cursor.getString(cursor.getColumnIndex("CAS")));
                map.put("UN", cursor.getString(cursor.getColumnIndex("UN")));
                listItem.add(map);
            }
            cursor.close();
        }
        return listItem;
    }
}
