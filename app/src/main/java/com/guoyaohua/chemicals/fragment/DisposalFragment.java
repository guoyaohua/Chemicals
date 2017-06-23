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
 * Created by John Kwok on 2016/9/9.
 */
public class DisposalFragment extends Fragment{
    private TextView tv_aid;
    private TextView tv_fire;
    private TextView tv_leak;
    private TextView tv_evacuate;
    private TextView tv_emergency;
    private TextView tv_disposal;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disposal_layout, container, false);
        return view;
    }
    public void initWidget() {
        tv_aid = (TextView) getActivity().findViewById(R.id.tv_aid);
        tv_fire = (TextView) getActivity().findViewById(R.id.tv_fire);
        tv_leak = (TextView) getActivity().findViewById(R.id.tv_leak);
        tv_evacuate = (TextView) getActivity().findViewById(R.id.tv_evacuate);
        tv_emergency = (TextView) getActivity().findViewById(R.id.tv_emergency);
        tv_disposal = (TextView) getActivity().findViewById(R.id.tv_disposal);
    }

    public void initData() {
        tv_aid.setText(ChemicalDetailActivity.chemical_detail_map.get("aid") + "");
        tv_fire.setText(ChemicalDetailActivity.chemical_detail_map.get("fire") + "");
        tv_leak.setText(ChemicalDetailActivity.chemical_detail_map.get("leak") + "");
        tv_evacuate.setText(ChemicalDetailActivity.chemical_detail_map.get("evacuate") + "");
        tv_emergency.setText(ChemicalDetailActivity.chemical_detail_map.get("emergency") + "");
        tv_disposal.setText(ChemicalDetailActivity.chemical_detail_map.get("disposal") + "");
    }
}
