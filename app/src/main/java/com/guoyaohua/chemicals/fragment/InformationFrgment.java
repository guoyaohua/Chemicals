package com.guoyaohua.chemicals.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guoyaohua.chemicals.ChemicalDetailActivity;
import com.guoyaohua.chemicals.R;

/**
 * Created by John Kwok on 2016/9/8.
 */
public class InformationFrgment extends Fragment {
    private TextView tv_cnname;
    private TextView tv_enname;
    private TextView tv_byname;
    private TextView tv_un;
    private TextView tv_cas;
    private TextView tv_dg_code;
    private TextView tv_chs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Toast.makeText(this.getContext(),ChemicalDetailActivity.chemical_detail_map.get("cn_name")+"",Toast.LENGTH_SHORT).show();
        initWidget();
        initData();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initWidget() {
        tv_cnname = (TextView) getActivity().findViewById(R.id.tv_cnname);
        tv_enname = (TextView) getActivity().findViewById(R.id.tv_enname);
        tv_byname = (TextView) getActivity().findViewById(R.id.tv_byname);
        tv_un = (TextView) getActivity().findViewById(R.id.tv_un);
        tv_cas = (TextView) getActivity().findViewById(R.id.tv_cas);
        tv_dg_code = (TextView) getActivity().findViewById(R.id.tv_dg_code);
        tv_chs = (TextView) getActivity().findViewById(R.id.tv_chs);

    }

    public void initData() {
        tv_cnname.setText(ChemicalDetailActivity.chemical_detail_map.get("cn_name") + "");
        tv_enname.setText(ChemicalDetailActivity.chemical_detail_map.get("en_name") + "");
        tv_byname.setText(ChemicalDetailActivity.chemical_detail_map.get("byname") + "");
        tv_un.setText(ChemicalDetailActivity.chemical_detail_map.get("UN") + "");
        tv_cas.setText(ChemicalDetailActivity.chemical_detail_map.get("CAS") + "");
        tv_dg_code.setText(ChemicalDetailActivity.chemical_detail_map.get("dg_code") + "");
        tv_chs.setText(ChemicalDetailActivity.chemical_detail_map.get("GHS") + "");
    }
}
