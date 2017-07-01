package com.guoyaohua.chemicals.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.guoyaohua.chemicals.AboutActivity;
import com.guoyaohua.chemicals.IntroduceActivity;
import com.guoyaohua.chemicals.R;
import com.guoyaohua.chemicals.tool_tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by John Kwok on 2016/7/12.
 */
public class ToolFragment extends Fragment {
    public static String tool_title[] = new String[]{"实用手册", "程序介绍", "关 于"};
    TextView mText = null;
    private View mParent;
    private FragmentActivity mActivity;
    private Button bt_test = null;
    private ListView mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tool_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        mParent = getView();
        mList = (ListView) mParent.findViewById(R.id.lv_tool);
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map;
        for (int i = 0; i < 3; i++) {
            map = new HashMap<String, Object>();
            map.put("tool_title", tool_title[i]);
            list.add(map);
        }

        SimpleAdapter simAdapter = new SimpleAdapter(getContext(), list, R.layout.tool_item, new String[]{"tool_title"}, new int[]{R.id.tx_tool});
        mList.setAdapter(simAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(view.getContext(), tool_tool.class);
                        break;
                    case 1:
                        intent = new Intent(view.getContext(), IntroduceActivity.class);
                        break;
                    case 2:
                        intent = new Intent(view.getContext(), AboutActivity.class);
                        break;

                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

}
