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
public class FeatureFragment extends Fragment {
    private TextView tv_formula;
    private TextView tv_weight;
    private TextView tv_melting_point;
    private TextView tv_boiling_point;
    private TextView tv_flash_point;
    private TextView tv_freezing_point;
    private TextView tv_vapour_pressure;
    private TextView tv_vapor_density;
    private TextView tv_gas_density;
    private TextView tv_relative_density;
    private TextView tv_critical_pressure;
    private TextView tv_critical_temperature;
    private TextView tv_ionization_potential;
    private TextView tv_explosion_limit;
    private TextView tv_autogenous_ignition;
    private TextView tv_ignition_energy;
    private TextView tv_characterization;
    private TextView tv_application;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feature_layout, container, false);
        return view;
    }
    public void initWidget() {
        tv_formula = (TextView) getActivity().findViewById(R.id.tv_formula);
        tv_weight = (TextView) getActivity().findViewById(R.id.tv_weight);
        tv_melting_point = (TextView) getActivity().findViewById(R.id.tv_melting_point);
        tv_boiling_point = (TextView) getActivity().findViewById(R.id.tv_boiling_point);
        tv_flash_point = (TextView) getActivity().findViewById(R.id.tv_flash_point);
        tv_freezing_point = (TextView) getActivity().findViewById(R.id.tv_freezing_point);
        tv_vapour_pressure = (TextView) getActivity().findViewById(R.id.tv_vapour_pressure);
        tv_vapor_density = (TextView) getActivity().findViewById(R.id.tv_vapor_density);
        tv_gas_density = (TextView) getActivity().findViewById(R.id.tv_gas_density);
        tv_relative_density = (TextView) getActivity().findViewById(R.id.tv_relative_density);
        tv_critical_pressure = (TextView) getActivity().findViewById(R.id.tv_critical_pressure);
        tv_critical_temperature = (TextView) getActivity().findViewById(R.id.tv_critical_temperature);
        tv_ionization_potential = (TextView) getActivity().findViewById(R.id.tv_ionization_potential);
        tv_explosion_limit = (TextView) getActivity().findViewById(R.id.tv_explosion_limit);
        tv_autogenous_ignition = (TextView) getActivity().findViewById(R.id.tv_autogenous_ignition);
        tv_ignition_energy = (TextView) getActivity().findViewById(R.id.tv_ignition_energy);
        tv_characterization = (TextView) getActivity().findViewById(R.id.tv_characterization);
        tv_application = (TextView) getActivity().findViewById(R.id.tv_application);
    }

    public void initData() {
        tv_formula.setText(ChemicalDetailActivity.chemical_detail_map.get("formula") + "");
        tv_weight.setText(ChemicalDetailActivity.chemical_detail_map.get("weight") + "");
        tv_melting_point.setText(ChemicalDetailActivity.chemical_detail_map.get("melting_point") + "");
        tv_boiling_point.setText(ChemicalDetailActivity.chemical_detail_map.get("boiling_point") + "");
        tv_flash_point.setText(ChemicalDetailActivity.chemical_detail_map.get("flash_point") + "");
        tv_freezing_point.setText(ChemicalDetailActivity.chemical_detail_map.get("freezing_point") + "");
        tv_vapour_pressure.setText(ChemicalDetailActivity.chemical_detail_map.get("vapour_pressure") + "");
        tv_vapor_density.setText(ChemicalDetailActivity.chemical_detail_map.get("vapour_pressure") + "");
        tv_gas_density.setText(ChemicalDetailActivity.chemical_detail_map.get("gas_density") + "");
        tv_relative_density.setText(ChemicalDetailActivity.chemical_detail_map.get("relative_density") + "");
        tv_critical_pressure.setText(ChemicalDetailActivity.chemical_detail_map.get("critical_pressure") + "");
        tv_critical_temperature.setText(ChemicalDetailActivity.chemical_detail_map.get("critical_temperature") + "");
        tv_ionization_potential.setText(ChemicalDetailActivity.chemical_detail_map.get("ionization_potential") + "");
        tv_explosion_limit.setText(ChemicalDetailActivity.chemical_detail_map.get("explosion_limit") + "");
        tv_autogenous_ignition.setText(ChemicalDetailActivity.chemical_detail_map.get("autogenous_ignition") + "");
        tv_ignition_energy.setText(ChemicalDetailActivity.chemical_detail_map.get("ignition_energy") + "");
        tv_characterization.setText(ChemicalDetailActivity.chemical_detail_map.get("characterization") + "");
        tv_application.setText(ChemicalDetailActivity.chemical_detail_map.get("application") + "");
    }
}
