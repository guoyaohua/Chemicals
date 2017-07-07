/*
 * Copyright (C) 2013 ePapyrus, Inc. All rights reserved.
 *
 * This file is part of the PlugPDF Android project.
 */

/*
 * OutlineAdapter.java
 *
 * Version:
 *       id
 *
 * Revision:
 *      logs
 */

package com.epapyrus.plugpdf;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.epapyrus.plugpdf.core.OutlineItem;

import java.util.List;

/**
 * UI implementation of the Outline panel
 *
 * @author ePapyrus
 * @see <a href="http://www.plugpdf.com">http://www.plugpdf.com</a>
 */
public class OutlineAdapter extends BaseAdapter {
    private final List<OutlineItem> mItems;
    private final LayoutInflater mInflater;

    private final OutlintEditListener mListener;
    private final int MAX_DEPS = 8;
    /*public void setOutlineEditListener(OutlintEditListener outlintEditListener) {
        mListener = outlintEditListener;
    }
    */
    private boolean enableAddBtn;
    private boolean enableRemoveBtn;

    /**
     * Constructor
     *
     * @param inflater            {@link LayoutInflater}
     * @param items               {@link OutlineItem} {@link List}
     * @param outlintEditListener {@link OutlintEditListener} object
     */
    public OutlineAdapter(LayoutInflater inflater, List<OutlineItem> items, OutlintEditListener outlintEditListener) {
        mInflater = inflater;
        mItems = items;
        mListener = outlintEditListener;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        return mItems.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int arg0) {
        return mItems.get(arg0);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int arg0) {
        return 0;
    }

    /**
     * Sets the value to enable/disable the add button from the outline panel.
     *
     * @param enable {@link Boolean} value to enable/disable the add button.
     */
    public void setEnableAdd(boolean enable) {
        enableAddBtn = enable;
    }

    /**
     * Sets the value to enable/disable the remove button from the outline panel.
     *
     * @param enable {@link Boolean} value to enable/disable the remove button.
     */
    public void setEnableRemove(boolean enable) {
        enableRemoveBtn = enable;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(R.layout.panel_outline_item, null);
        } else {
            v = convertView;
        }
        final Context context = v.getContext();

        int deps = mItems.get(position).getDeps();
        if (deps > MAX_DEPS) {
            deps = MAX_DEPS;
        }

        String space = "";
        for (int i = 0; i < deps; i++) {
            space += "   ";
        }

        String title = space + mItems.get(position).getTitle();
        String pageNumber = String.valueOf(mItems.get(position).getPageIdx() + 1);

        ((TextView) v.findViewById(R.id.title)).setText(title);
        ((TextView) v.findViewById(R.id.page)).setText(pageNumber);

        if (enableAddBtn) {

            v.findViewById(R.id.add_btn).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    View promptView = layoutInflater.inflate(R.layout.outline_dialog, null);

                    final EditText input = (EditText) promptView.findViewById(R.id.outline_dialog_text_field);
                    final CheckBox childCheckBox = (CheckBox) promptView.findViewById(R.id.outline_dialog_child_check_box);
                    final InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    mListener.onClickToAdd(mItems, position + 1, input.getText().toString(), childCheckBox.isChecked());
                                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                                }
                            });

                    AlertDialog alertD = alertDialogBuilder.create();
                    alertD.show();

                }
            });

        } else {

            v.findViewById(R.id.add_btn).setVisibility(View.GONE);

        }

        if (enableRemoveBtn) {

            v.findViewById(R.id.remove_btn).setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.onClickToRemove(mItems, position);
                }
            });

        } else {

            v.findViewById(R.id.remove_btn).setVisibility(View.GONE);

        }

        return v;
    }

    public interface OutlintEditListener {
        void onClickToRemove(List<OutlineItem> list, int index);

        void onClickToAdd(List<OutlineItem> list, int index, String title, boolean isChild);
    }
}
