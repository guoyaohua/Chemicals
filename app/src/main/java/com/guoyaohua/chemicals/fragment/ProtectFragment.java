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
public class ProtectFragment extends Fragment{
    private TextView tv_protect;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protect_layout, container, false);
        return view;
    }
    public void initWidget() {
        tv_protect = (TextView) getActivity().findViewById(R.id.tv_protect);
    }

    public void initData() {
        tv_protect.setText(ChemicalDetailActivity.chemical_detail_map.get("protect") + "");
    }
}
