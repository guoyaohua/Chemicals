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
public class DangerFragment extends Fragment {
    private TextView tv_risk_categories;
    private TextView tv_danger;
    private TextView tv_health_hazard;
    private TextView tv_environmental;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danger_layout, container, false);
        return view;
    }
    public void initWidget() {
        tv_risk_categories = (TextView) getActivity().findViewById(R.id.tv_risk_categories);
        tv_danger = (TextView) getActivity().findViewById(R.id.tv_danger);
        tv_health_hazard = (TextView) getActivity().findViewById(R.id.tv_health_hazard);
        tv_environmental = (TextView) getActivity().findViewById(R.id.tv_environmental);
    }

    public void initData() {
        tv_risk_categories.setText(ChemicalDetailActivity.chemical_detail_map.get("risk_categories") + "");
        tv_danger.setText(ChemicalDetailActivity.chemical_detail_map.get("danger") + "");
        tv_health_hazard.setText(ChemicalDetailActivity.chemical_detail_map.get("health_hazard") + "");
        tv_environmental.setText(ChemicalDetailActivity.chemical_detail_map.get("environmental") + "");
    }
}
