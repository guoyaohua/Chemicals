package com.guoyaohua.chemicals.fragment;

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
public class NameFragment extends Fragment implements View.OnClickListener {
    //设定三种搜索状态，用来让ListView判断，点击item时响应何种事件。
    public static int state = 1;
    public static int pre_state;
    public static EditText et_search;
    public final int CN_ALPHABET_INDEX_STATE = 1;
    public final int EN_ALPHABET_INDEX_STATE = 2;
    public final int NAME_SEARCH_STATE = 3;
    private View mParent;
    private FragmentActivity mActivity;
    private ImageButton bt_search;
    private ImageButton bt_cnSearch;
    private ImageButton bt_enSearch;
    private ListView lv_searchResult;
    private SimpleAdapter simpleAdapter_cn;//中文搜索的Adapter
    private SimpleAdapter simpleAdapter_en;//英文搜索的Adapter
    private SimpleAdapter simpleAdapter_search;//英文搜索的Adapter

    //用来保存拼音/英文名称各个字母开头的数目  此处直接定义，不用每次查库。
    private int[] cn_num = {1, 4, 1, 1, 2, 6, 1, 7, 7, 1, 8, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 6, 0};
    private int[] en_num = {7, 3, 2, 5, 2,2, 1, 7, 3, 7, 9, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        state = CN_ALPHABET_INDEX_STATE; //初始化状态；
        mParent = getView();

        mActivity = getActivity();

        et_search = (EditText) mParent.findViewById(R.id.et_search);
        bt_search = (ImageButton) mParent.findViewById(R.id.bt_search);
        bt_cnSearch = (ImageButton) mParent.findViewById(R.id.bt_cnSearch);
        bt_enSearch = (ImageButton) mParent.findViewById(R.id.bt_enSearch);
        lv_searchResult = (ListView) mParent.findViewById(R.id.lv_searchResult);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (et_search.getText().toString().isEmpty() && state != NAME_SEARCH_STATE) {
                    pre_state = state;//保留在空白edittext控件输入文字时的状态；
                }
            }

            /**
             * 当editText文字改变的时候，直接查询数据库，不用点击搜索即可。
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i("textchange","onTextChanged");
                state = NAME_SEARCH_STATE;
                startSearch(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_search.getText().toString().isEmpty()) {
                    state = pre_state;//当edittext中文字均删除后，回复当前状态。
                }
            }
        });

        bt_search.setOnClickListener(this);
        bt_cnSearch.setOnClickListener(this);
        bt_enSearch.setOnClickListener(this);
//构建中文拼音搜索所需要的Listview的adapter
        List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        char alphabet = 'A';
        HashMap<String, Object> map;
        for (int i = 0; i < 26; i++) {
            map = new HashMap<String, Object>();
            map.put("ItemText", alphabet);
            map.put("num", cn_num[i]);
            alphabet++;
            listItem.add(map);
        }


        simpleAdapter_cn = new SimpleAdapter(getContext(), listItem,
                R.layout.searchresult_item_layout, new String[]{"ItemText", "num"}, new int[]{R.id.tv_lvItem_cn_name, R.id.tv_lvItem_num});

        lv_searchResult.setAdapter(simpleAdapter_cn);

//构建英文字母搜索所需要的Adapter
        List<HashMap<String, Object>> listItem_en = new ArrayList<HashMap<String, Object>>();
        char alphabet_en = 'A';
        HashMap<String, Object> map_en;
        for (int i = 0; i < 26; i++) {
            map_en = new HashMap<String, Object>();
            map_en.put("ItemText", alphabet_en);
            map_en.put("num", en_num[i]);
            alphabet_en++;
            listItem_en.add(map_en);
        }
        simpleAdapter_en = new SimpleAdapter(getContext(), listItem_en,
                R.layout.searchresult_item_layout, new String[]{"ItemText", "num"}, new int[]{R.id.tv_lvItem_cn_name, R.id.tv_lvItem_num});


        lv_searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (state) {
                    case CN_ALPHABET_INDEX_STATE:
//                        Toast.makeText(getContext(), "此时是拼音索引状态，点击了" + position, Toast.LENGTH_SHORT).show();
                        TextView tv1 = (TextView) view.findViewById(R.id.tv_lvItem_cn_name);
                        String args1 = tv1.getText().toString();
                        Cursor c1 = Welcome.sqLite.rawQuery("select * from " +
                                MyDatabaseHelper.TABLENAME +
                                " where pinyin like '" + args1 + "%'", null);  //关键字搜索。在整个字符串中搜索某个关键字。
                        if (c1 != null) {
                            //构建搜索所需要的Listview的adapter
                            List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                            HashMap<String, Object> map;
                            c1.moveToNext();
                            for (int i = 0; i < c1.getCount(); i++, c1.moveToNext()) {

                                map = new HashMap<String, Object>();
                                map.put("cn_name", c1.getString(c1.getColumnIndex("cn_name")));
                                map.put("en_name", c1.getString(c1.getColumnIndex("en_name")));
                                listItem.add(map);
                            }
                            c1.close();
                            simpleAdapter_search = new SimpleAdapter(getContext(), listItem,
                                    R.layout.searchresult_item_layout, new String[]{"cn_name", "en_name"},
                                    new int[]{R.id.tv_lvItem_cn_name, R.id.tv_lvItem_en_name});
                            lv_searchResult.setAdapter(simpleAdapter_search);
                        }
                        pre_state = state;
                        state = NAME_SEARCH_STATE;
                        break;
                    case EN_ALPHABET_INDEX_STATE:
//                      Toast.makeText(getContext(), "此时是英文字母索引状态，点击了" + position, Toast.LENGTH_SHORT).show();
                        TextView tv2 = (TextView) view.findViewById(R.id.tv_lvItem_cn_name);
                        String args2 = tv2.getText().toString();
                        Cursor c2 = Welcome.sqLite.rawQuery("select * from " +
                                MyDatabaseHelper.TABLENAME +
                                " where en_name like '" + args2 + "%'", null);  //关键字搜索。在整个字符串中搜索某个关键字。
                        if (c2 != null) {
                            //构建搜索所需要的Listview的adapter
                            List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                            HashMap<String, Object> map;
                            c2.moveToNext();
                            for (int i = 0; i < c2.getCount(); i++, c2.moveToNext()) {

                                map = new HashMap<String, Object>();
                                int w = c2.getColumnIndex("cn_name");
                                map.put("en_name", c2.getString(c2.getColumnIndex("en_name")));
                                map.put("cn_name", c2.getString(c2.getColumnIndex("cn_name")));
                                listItem.add(map);
                            }
                            c2.close();
                            simpleAdapter_search = new SimpleAdapter(getContext(), listItem,
                                    R.layout.searchresult_item_layout, new String[]{"cn_name", "en_name"}, new int[]{R.id.tv_lvItem_cn_name, R.id.tv_lvItem_en_name});
                            lv_searchResult.setAdapter(simpleAdapter_search);
                        }
                        pre_state = state;
                        state = NAME_SEARCH_STATE;
                        break;
                    case NAME_SEARCH_STATE:
                        TextView tv3 = (TextView) view.findViewById(R.id.tv_lvItem_cn_name);
                        String name = tv3.getText().toString();

//                        Toast.makeText(getContext(), "此时是搜索状态，点击了" + args3 + position, Toast.LENGTH_SHORT).show();
                        //test
                        Intent intent = new Intent();
                        intent.setClass(getContext(), ChemicalDetailActivity.class);
                        intent.putExtra("cn_name", name);
                        startActivity(intent);
                        break;

                    //test
                   /*
                        Intent intent = new Intent();
                        intent.setClass(getContext(), ChemicalDetail.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("cn_name", args3);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;*/
                }


                //此处设计为，当点击某个按钮，判断其字母，和按钮状态，对数据库进行检索，并显示在listview中。

            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v == bt_enSearch) {
            //下面是修改背景颜色动作
            //bt_cnSearch.setBackgroundResource(R.color.colorPrimary);
            //bt_enSearch.setBackgroundResource(R.color.colorAccent);
            bt_cnSearch.setImageDrawable(getResources().getDrawable(R.drawable.bt_cn_unselected));
            bt_enSearch.setImageDrawable(getResources().getDrawable(R.drawable.bt_en_selected));
            lv_searchResult.setAdapter(simpleAdapter_en);
            state = EN_ALPHABET_INDEX_STATE;
        } else if (v == bt_cnSearch) {
            //bt_enSearch.setBackgroundResource(R.color.colorPrimary);
            //bt_cnSearch.setBackgroundResource(R.color.colorAccent);
            bt_cnSearch.setImageDrawable(getResources().getDrawable(R.drawable.bt_cn_selected));
            bt_enSearch.setImageDrawable(getResources().getDrawable(R.drawable.bt_en_unselected));
            lv_searchResult.setAdapter(simpleAdapter_cn);
            state = CN_ALPHABET_INDEX_STATE;
        } else if (v == bt_search) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
            state = NAME_SEARCH_STATE;

            startSearch(true);

        }
    }

    private void startSearch(boolean isButtonClicked) {
        String args = et_search.getText().toString().trim();
        Cursor c = null;

        if (!args.isEmpty()) {
            switch (pre_state) {
                case CN_ALPHABET_INDEX_STATE:
                    c = Welcome.sqLite.rawQuery("select * from " +
                            MyDatabaseHelper.TABLENAME +
                            " where cn_name like '%" + args + "%' or pinyin like '" + args + "%'", null);  //关键字搜索。在整个字符串中搜索某个关键字。
                    break;
                case EN_ALPHABET_INDEX_STATE:
                    c = Welcome.sqLite.rawQuery("select * from " +
                            MyDatabaseHelper.TABLENAME +
                            " where en_name like '" + args + "%'", null);  //关键字搜索。在整个字符串中搜索某个关键字。
                    break;
            }

//            Cursor c = Welcom.sqLite.rawQuery("select * from " +
//                    MyDatabaseHelper.TABLENAME +
//                    " where cn_name like '%" + args + "%' or pinyin like '" + args + "%' or en_name like '" + args + "%'", null);  //关键字搜索。在整个字符串中搜索某个关键字。
//


            if (c != null) {
                //构建搜索所需要的Listview的adapter
                List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> map;
                c.moveToNext();
                for (int i = 0; i < c.getCount(); i++, c.moveToNext()) {

                    map = new HashMap<String, Object>();
                    map.put("cn_name", c.getString(c.getColumnIndex("cn_name")));
                    map.put("en_name", c.getString(c.getColumnIndex("en_name")));
                    listItem.add(map);
                }
                c.close();
                simpleAdapter_search = new SimpleAdapter(getContext(), listItem,
                        R.layout.searchresult_item_layout, new String[]{"cn_name", "en_name"}, new int[]{R.id.tv_lvItem_cn_name, R.id.tv_lvItem_en_name});
                lv_searchResult.setAdapter(simpleAdapter_search);
            }
        } else if (isButtonClicked) {
            Toast.makeText(getContext(), "请输入带查询的关键字。", Toast.LENGTH_SHORT).show();
        } else {
            switch (pre_state) {
                case EN_ALPHABET_INDEX_STATE:
                    lv_searchResult.setAdapter(simpleAdapter_en);
                    state = EN_ALPHABET_INDEX_STATE;
                    break;
                case CN_ALPHABET_INDEX_STATE:
                    lv_searchResult.setAdapter(simpleAdapter_cn);
                    state = CN_ALPHABET_INDEX_STATE;
                    break;
            }
//            如果搜索框中的文字删除，导致内容为空，这时默认将listview设置为中文搜索，并将state状态改变。
//            lv_searchResult.setAdapter(simpleAdapter_cn);
//            state = CN_ALPHABET_INDEX_STATE;

        }
    }


}
