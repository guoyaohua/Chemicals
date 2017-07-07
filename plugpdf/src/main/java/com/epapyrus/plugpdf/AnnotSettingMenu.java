/*
 * Copyright (C) 2013 ePapyrus, Inc. All rights reserved.
 *
 * This file is part of the PlugPDF Android project.
 */

/*
 * AnnotSettingMenu.java
 * 
 * Version:
 *       id
 *       
 * Revision:
 *      logs
 */

package com.epapyrus.plugpdf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.epapyrus.plugpdf.core.annotation.tool.AnnotSetting;
import com.epapyrus.plugpdf.core.annotation.tool.BaseAnnotTool.AnnotToolType;

/**
 * Setups the Annotation menu, creates and shows the annotation pop-up window.
 *
 * @author ePapyrus
 * @see <a href="http://www.plugpdf.com">http://www.plugpdf.com</a>
 */
public class AnnotSettingMenu {
    private static final String PX = "px";
    private static final String PERCENT = "%";
    private Context mContext;
    private LayoutInflater mInflater;
    private PopupWindow mPopupWindow;
    private AnnotToolType mAnnotType;

    /**
     * Constructor that assigns the context and initializes the {@link LayoutInflater}.
     *
     * @param context {@link Context} instance
     */
    public AnnotSettingMenu(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the spinner position with respect to the annotation color.
     *
     * @param color Annotation color
     * @return Spinner position
     */
    private int getSpinnerPos(int color) {
        switch (color) {
            case 0x000000:
                return 0;
            case 0xFF0000:
                return 1;
            case 0xFFFF00:
                return 2;
            case 0x00FF00:
                return 3;
            case 0x0000FF:
                return 4;
            case 0xFFFFFF:
                return 5;
        }
        return 0;
    }

    /**
     * Returns the color with respect to the spinner position.
     *
     * @param pos Spinner position
     * @return Annotation color
     */
    private int getColorFromSpinner(int pos) {
        switch (pos) {
            case 0:
                return 0x000000;
            case 1:
                return 0xFF0000;
            case 2:
                return 0xFFFF00;
            case 3:
                return 0x00FF00;
            case 4:
                return 0x0000FF;
            case 5:
                return 0xFFFFFF;
        }
        return 0x000000;
    }

    /**
     * Given an annotation type it creates the annotation pop-up window.
     *
     * @param type Annotation tool type
     */
    @SuppressLint("InflateParams")
    private void createPopupWindow(AnnotToolType type) {
        View menuView = mInflater.inflate(R.layout.annot_setting, null);

        mPopupWindow = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        final AnnotSetting setting = AnnotSetting.instance();
        // line color
        Spinner lineColorSpinner = (Spinner) menuView
                .findViewById(R.id.annot_line_color_value);
        lineColorSpinner.setSelection(getSpinnerPos(setting.getLineColor(mAnnotType)));
        lineColorSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        setting.setLineColor(getColorFromSpinner(position), mAnnotType);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        // fill color
        Spinner fillColorSpinner = (Spinner) menuView
                .findViewById(R.id.annot_fill_color_value);
        fillColorSpinner.setSelection(getSpinnerPos(setting.getFillColor(mAnnotType)));
        fillColorSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        setting.setFillColor(getColorFromSpinner(position), mAnnotType);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        // line opacity
        final TextView lineOpacityValueDisplay = (TextView) menuView
                .findViewById(R.id.annot_opacity_value_display);
        lineOpacityValueDisplay.setText(Integer.toString((int) (AnnotSetting
                .instance().getOpacity(mAnnotType) / 255.0f * 100)) + PERCENT);

        SeekBar lineOpacityValue = (SeekBar) menuView
                .findViewById(R.id.annot_opacity_value);
        lineOpacityValue.setProgress(setting.getOpacity(mAnnotType));
        lineOpacityValue
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        lineOpacityValueDisplay.setText(Integer
                                .toString((int) (progress / 255.0f * 100))
                                + PERCENT);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        setting.setOpacity(seekBar.getProgress(), mAnnotType);
                        lineOpacityValueDisplay.setText(Integer
                                .toString((int) (setting.getOpacity(mAnnotType) / 255.0f * 100))
                                + PERCENT);
                    }
                });

        // line width
        final TextView lineWidthValueDisplay = (TextView) menuView
                .findViewById(R.id.annot_line_width_value_display);
        lineWidthValueDisplay.setText(Integer.toString(setting.getLineWidth(mAnnotType))
                + PX);

        SeekBar lineWidthValue = (SeekBar) menuView
                .findViewById(R.id.annot_line_width_value);
        lineWidthValue.setProgress(setting.getLineWidth(mAnnotType));
        lineWidthValue
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        lineWidthValueDisplay.setText(Integer
                                .toString(progress) + PX);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        setting.setLineWidth(seekBar.getProgress(), mAnnotType);
                        lineWidthValueDisplay.setText(Integer.toString(setting
                                .getLineWidth(mAnnotType)) + PX);
                    }
                });

        // line straight
        final CheckBox straight = (CheckBox) menuView
                .findViewById(R.id.annot_line_straight);
        straight.setChecked(setting.getInkLineStraight());
        straight.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                straight.setChecked(isChecked);
                setting.setInkLineStraight(isChecked);
            }
        });

        // line squiggly
        final CheckBox squiggly = (CheckBox) menuView
                .findViewById(R.id.annot_line_squiggly);
        squiggly.setChecked(setting.isSquiggly());
        squiggly.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                squiggly.setChecked(isChecked);
                setting.setSquiggly(isChecked);
            }
        });

        switch (mAnnotType) {
            case INK:
                menuView.findViewById(R.id.annot_fill_color_title).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_fill_color_value).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_line_squiggly).setVisibility(View.GONE);
                break;
            case STRIKEOUT:
            case HIGHLIGHT:
                menuView.findViewById(R.id.annot_line_squiggly).setVisibility(View.GONE);
            case UNDERLINE:
                menuView.findViewById(R.id.annot_line_straight).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_fill_color_title).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_fill_color_value).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_opacity_title).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_opacity_value).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_opacity_value_display).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_line_width_title).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_line_width_value).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_line_width_value_display).setVisibility(View.GONE);
                break;
            case SQUARE:
            case CIRCLE:
                menuView.findViewById(R.id.annot_line_straight).setVisibility(View.GONE);
                menuView.findViewById(R.id.annot_line_squiggly).setVisibility(View.GONE);
                ((TextView) menuView.findViewById(R.id.annot_line_color_title)).setText("line color");
            default:
                break;
        }
    }

    /**
     * Given both the annotation type and the position of the annotation pop-up, it creates and shows
     * the annotation tool's pop-up.
     *
     * @param anchor A parent {@link View} to get Window token from
     * @param x      The popup's x location offset
     * @param y      The popup's y location offset
     * @param type   Annotation tool type
     */
    public synchronized void show(final View anchor, final int x, final int y, AnnotToolType type) {

        mAnnotType = type;

        createPopupWindow(mAnnotType);

        mPopupWindow.showAtLocation(anchor, Gravity.CENTER, x, y);
    }

    /**
     * Closes the annotation pop up.
     */
    public synchronized void close() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * enum to set and get the Annotation's color types </br>
     * <b>Color  &nbsp;  Value  </b>
     * </br>Black   &nbsp;    0
     * </br>Red     &nbsp;    1
     * </br>Yellow  &nbsp;    2
     * </br>Green   &nbsp;    3
     * </br>Blue    &nbsp;    4
     * </br>White   &nbsp;    5
     */
    public enum ColorType {
        BLACK(0), RED(1), YELLOW(2), GREEN(3), BLUE(4), WHITE(5);

        private int mValue;

        ColorType(int value) {
            mValue = value;
        }

        public int value() {
            return mValue;
        }
    }

}
