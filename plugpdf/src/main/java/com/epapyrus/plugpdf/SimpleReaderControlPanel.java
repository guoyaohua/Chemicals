/*
 * Copyright (C) 2013 ePapyrus, Inc. All rights reserved.
 *
 * This file is part of the PlugPDF Android project.
 */

/*
 * SimpleReaderControlPanel.java
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
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ToggleButton;

import com.epapyrus.plugpdf.OutlineAdapter.OutlintEditListener;
import com.epapyrus.plugpdf.core.BaseReaderControl;
import com.epapyrus.plugpdf.core.OutlineItem;
import com.epapyrus.plugpdf.core.PlugPDFUtility;
import com.epapyrus.plugpdf.core.viewer.ReaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class corresponds to the submenu that is activated in response to a higher-level menu selection.
 *
 * @author ePapyrus
 * @see <a href="http://www.plugpdf.com">http://www.plugpdf.com</a>
 */
public class SimpleReaderControlPanel {

    private SimpleReaderControlView mParent;
    private BaseReaderControl mController;

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private View mPageDisplayPanel;
    private View mBrightnessPanel;
    private View mOutlinePanel;

    private PopupWindow mPopupPanel;

    private boolean outlineEditMode;

    private boolean mIsModifiedOutline = false;

    private List<OutlineItem> outlineItemList;
    private OutlineAdapter.OutlintEditListener outlintEditListener = new OutlintEditListener() {

        @Override
        public void onClickToRemove(List<OutlineItem> list, int index) {
            removeOutlineItem(list, index);
        }

        @Override
        public void onClickToAdd(List<OutlineItem> list, int index, String title, boolean isChild) {
            addOutlineItem(list, index, title, ((ReaderView) mController).getPageIdx(), isChild);
        }
    };

    /**
     * Constructor.
     *
     * @param context    {@link Context}
     * @param parent     {@link SimpleReaderControlView} Object
     * @param controller {@link BaseReaderControl} Object
     */
    public SimpleReaderControlPanel(Context context,
                                    SimpleReaderControlView parent, BaseReaderControl controller) {
        mContext = context;
        mParent = parent;

        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mController = controller;

        createPageDisplayPanel();
        createBrightnessPanel();
        createOutlinePanel();
    }

    /**
     * Creates the outline panel.
     */
    private void createOutlinePanel() {
        mOutlinePanel = mLayoutInflater.inflate(R.layout.panel_outline, mParent, false);

        final ListView outlineList = (ListView) mOutlinePanel
                .findViewById(R.id.panel_outline_list);

        outlineList.setAdapter(null);
        outlineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!outlineEditMode) {
                    OutlineItem item = (OutlineItem) parent.getAdapter().getItem(position);
                    mController.goToPage(item.getPageIdx());
                    hide();
                }
            }
        });

        final ToggleButton addBtn = (ToggleButton) mOutlinePanel.findViewById(R.id.panel_outline_add);
        final ToggleButton removeBtn = (ToggleButton) mOutlinePanel.findViewById(R.id.panel_outline_remove);

        addBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (outlineItemList.size() == 0) {

                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    View promptView = layoutInflater.inflate(R.layout.outline_dialog, mParent, false);

                    final EditText input = (EditText) promptView.findViewById(R.id.outline_dialog_text_field);
                    final CheckBox childCheckBox = (CheckBox) promptView.findViewById(R.id.outline_dialog_child_check_box);
                    final InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((OutlineAdapter) outlineList.getAdapter()).setEnableAdd(true);
                                    addOutlineItem(outlineItemList, 0, input.getText().toString(), ((ReaderView) mController).getPageIdx(), childCheckBox.isChecked());
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

                } else {

                    outlineEditMode = addBtn.isChecked();
                    removeBtn.setChecked(false);

                    OutlineAdapter adapter = new OutlineAdapter(mLayoutInflater, outlineItemList, outlintEditListener);
                    adapter.setEnableAdd(outlineEditMode);

                    ListView outlineList = (ListView) mOutlinePanel
                            .findViewById(R.id.panel_outline_list);
                    outlineList.setAdapter(adapter);
                }
            }
        });
        removeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                outlineEditMode = removeBtn.isChecked();
                addBtn.setChecked(false);

                OutlineAdapter adapter = new OutlineAdapter(mLayoutInflater, outlineItemList, outlintEditListener);
                adapter.setEnableRemove(outlineEditMode);

                ListView outlineList = (ListView) mOutlinePanel
                        .findViewById(R.id.panel_outline_list);
                outlineList.setAdapter(adapter);
            }
        });
    }

    /**
     * Creates the page display panel.
     */
    private void createPageDisplayPanel() {
        mPageDisplayPanel = mLayoutInflater.inflate(R.layout.panel_doc_flow, mParent, false);

        Button displayHorizontalButton = (Button) mPageDisplayPanel
                .findViewById(R.id.panel_display_horizontal);
        displayHorizontalButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mParent.setHorizontalMode();
                hide();
            }
        });

        Button displayVerticalButton = (Button) mPageDisplayPanel
                .findViewById(R.id.panel_display_vertical);
        displayVerticalButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mParent.setVerticalMode();
                hide();
            }
        });

        Button displayBilateralButton = (Button) mPageDisplayPanel
                .findViewById(R.id.panel_display_bilateral);
        displayBilateralButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mParent.setBilateralHorizontalMode();
                hide();
            }
        });

        Button displayThumbnailButton = (Button) mPageDisplayPanel
                .findViewById(R.id.panel_display_thumbnail);
        displayThumbnailButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mParent.setThumbnailMode();
                hide();
            }
        });
    }

    /**
     * Creates the brightness panel.
     */
    private void createBrightnessPanel() {
        mBrightnessPanel = mLayoutInflater.inflate(R.layout.panel_brightness, mParent, false);

        SeekBar brightnessSeekBar = (SeekBar) mBrightnessPanel
                .findViewById(R.id.panel_brightness_bar);

        final int minBright = 20;

        brightnessSeekBar
                .setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar,
                                                  int progress, boolean fromUser) {
                        float brightness = (float) (progress + minBright) * 0.01f;
                        PlugPDFUtility.setDisplayBrightness(
                                mParent.mAct.getWindow(), brightness);
                    }
                });

        PlugPDFUtility.setDisplayBrightness(mParent.mAct.getWindow(),
                brightnessSeekBar.getProgress() + minBright * 0.01f);
    }

    /**
     * Shows the anchor according to the passed-in PanelType.
     *
     * @param panelType Panel type (DISPLAYMODE/BRIGHTNESS/OUTLINE)
     * @param anchor    {@link View}
     */
    public void show(PanelType panelType, View anchor) {

        View panel = null;
        int offsetW = 0;

        switch (panelType) {
            case DISPLAYMODE:
                panel = mPageDisplayPanel;
                offsetW = (int) PlugPDFUtility.convertDipToPx(mContext, 17);

                break;
            case BRIGHTNESS:
                panel = mBrightnessPanel;
                offsetW = (int) PlugPDFUtility.convertDipToPx(mContext, 45);
                break;
            case OUTLINE:
                panel = mOutlinePanel;
                offsetW = (int) PlugPDFUtility.convertDipToPx(mContext, 100);

                outlineItemList = mController.getOutlineItem();
                if (outlineItemList == null) {
                    outlineItemList = new ArrayList<OutlineItem>();
                }

                OutlineAdapter adapter = new OutlineAdapter(mLayoutInflater, outlineItemList, outlintEditListener);
                ListView outlineList = (ListView) mOutlinePanel
                        .findViewById(R.id.panel_outline_list);
                outlineList.setAdapter(adapter);

                ((ToggleButton) mOutlinePanel.findViewById(R.id.panel_outline_add)).setChecked(false);
                ((ToggleButton) mOutlinePanel.findViewById(R.id.panel_outline_remove)).setChecked(false);
                break;
            default:
                break;
        }

        mPopupPanel = new PopupWindow(panel, LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, true);
        mPopupPanel.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopupPanel.setOutsideTouchable(true);
        mPopupPanel.setBackgroundDrawable(new BitmapDrawable());
        mPopupPanel.showAsDropDown(anchor, -offsetW, 0);
        mPopupPanel.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mIsModifiedOutline) {
                    outlineEditMode = false;
                    mIsModifiedOutline = false;
                    mController.updateOutline(outlineItemList);
                }
            }
        });
    }

    /**
     * Hides the pop-up window.
     *
     * @see PopupWindow
     */
    private void hide() {
        if (mPopupPanel != null) {
            mPopupPanel.dismiss();
            mPopupPanel = null;
        }
    }

    /**
     * Refreshes the layout.
     */
    public void refreshLayout() {
        hide();
    }

    /**
     * Adds the {@link OutlineItem}
     *
     * @param list    {@link OutlineItem} Object
     * @param index   The outline element's depth level in the outline tree.
     * @param title   Outline title.
     * @param pageIdx Page number.
     * @param isChild If the outline element is a child, true; otherwise, false.
     */
    public void addOutlineItem(List<OutlineItem> list, int index, String title, int pageIdx, boolean isChild) {

        OutlineItem item = null;

        if (index < 1) {
            item = new OutlineItem(0, title, pageIdx);
        } else {
            if (isChild) {
                item = new OutlineItem(list.get(index - 1).getDeps() + (isChild ? 1 : 0), title, pageIdx);
            } else {
                int targetDeps = list.get(index - 1).getDeps();
                for (int i = index; i < list.size(); i++) {
                    if (list.get(i).getDeps() <= targetDeps) {
                        index = i;
                        break;
                    } else if (i + 1 == list.size()) {
                        index = i + 1;
                        break;
                    }
                }
                item = new OutlineItem(targetDeps + (isChild ? 1 : 0), title, pageIdx);
            }
        }

        list.add(index, item);
        //((ListView) mOutlinePanel.findViewById(R.id.panel_outline_list)).setAdapter(new OutlineAdapter(mLayoutInflater, list, outlintEditListener));
        ListView outlineList = (ListView) mOutlinePanel.findViewById(R.id.panel_outline_list);
        ((BaseAdapter) outlineList.getAdapter()).notifyDataSetChanged();
        mOutlinePanel.requestLayout();
        mIsModifiedOutline = true;
    }

    /**
     * Removes the {@link OutlineItem}
     *
     * @param list  {@link OutlineItem} Object.
     * @param index The outline element's depth level in the outline tree.
     */
    public void removeOutlineItem(List<OutlineItem> list, int index) {
        int targetDeps = list.get(index).getDeps();
        for (int i = index + 1; i < list.size(); ) {
            if (list.get(i).getDeps() > targetDeps) {
                list.remove(i);
            } else {
                break;
            }
        }
        list.remove(index);
        //((ListView) mOutlinePanel.findViewById(R.id.panel_outline_list)).setAdapter(new OutlineAdapter(mLayoutInflater, list, outlintEditListener));
        ListView outlineList = (ListView) mOutlinePanel.findViewById(R.id.panel_outline_list);
        ((BaseAdapter) outlineList.getAdapter()).notifyDataSetChanged();
        mOutlinePanel.requestLayout();
        mIsModifiedOutline = true;
    }

    // panel type
    public enum PanelType {
        DISPLAYMODE, BRIGHTNESS, OUTLINE
    }
}
