/*
 * Copyright (C) 2013 ePapyrus, Inc. All rights reserved.
 *
 * This file is part of the PlugPDF Android project.
 */

/*
 * SimpleDocumentReader.java
 *
 * Version:
 *       id
 *
 * Revision:
 *      logs
 */

package com.epapyrus.plugpdf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.epapyrus.plugpdf.core.CoordConverter;
import com.epapyrus.plugpdf.core.PDFDocument;
import com.epapyrus.plugpdf.core.PropertyManager;
import com.epapyrus.plugpdf.core.annotation.AnnotEventListener;
import com.epapyrus.plugpdf.core.annotation.AnnotToolEventListener;
import com.epapyrus.plugpdf.core.annotation.acroform.FieldEventListener;
import com.epapyrus.plugpdf.core.annotation.tool.AnnotToolTransform;
import com.epapyrus.plugpdf.core.viewer.BasePlugPDFDisplay.PageDisplayMode;
import com.epapyrus.plugpdf.core.viewer.DocumentState;
import com.epapyrus.plugpdf.core.viewer.DocumentState.OPEN;
import com.epapyrus.plugpdf.core.viewer.PageViewListener;
import com.epapyrus.plugpdf.core.viewer.ReaderListener;
import com.epapyrus.plugpdf.core.viewer.ReaderView;

import java.io.FileInputStream;

/**
 * Custom PDF viewers can be created by using the PDFDocument class directly, without using the
 * SimpleDocumentViewer at all.
 *
 * @author ePapyrus
 * @brief Provides the fundamental PDF viewer model for all Android apps.
 * @see <a href="http://www.plugpdf.com">http://www.plugpdf.com</a>
 */
public class SimpleDocumentReader implements ReaderListener {

    private ReaderView mReaderView;
    private SimpleReaderControlView mControlView;
    private SimpleDocumentReaderListener mListener;
    private Activity mAct;
    private String mFilePath = null;
    private byte[] mFileData = null;

    /**
     * Constructor which initializes the Reader UI layout.
     *
     * @param act Passed {@link Activity}.
     */
    public SimpleDocumentReader(Activity act) {
        mAct = act;
        mReaderView = new ReaderView(mAct);
        mReaderView.setReaderListener(this);

        mControlView = (SimpleReaderControlView) SimpleReaderControlView
                .inflate(mAct, R.layout.simple_reader_control, null);
        mControlView.createUILayout(mReaderView);
    }

    /**
     * Registers a callback to be invoked when the document is open.
     *
     * @param listener An implementation of SimpleDocumentViewerListener.
     */
    public void setListener(SimpleDocumentReaderListener listener) {
        mListener = listener;
    }

    /**
     * Registers a callback to be invoked when the page load is complete.
     *
     * @param listener An implementation of PageViewListener.
     */
    public void setPageViewListener(PageViewListener listener) {
        mReaderView.setPageViewListener(listener);
    }

    /**
     * Registers a callback to be invoked when an annotation event occurs.
     *
     * @param listener An implementation of PlugPDFAnnotEventListener.
     */
    public void setAnnotEventLisener(AnnotEventListener listener) {
        mReaderView.setAnnotEventListener(listener);
    }

    public void setAnnotToolEventListener(AnnotToolEventListener listener) {
        mReaderView.setAnnotToolListener(listener);
    }

    public void setFieldEventListener(FieldEventListener listener) {
        mReaderView.setFieldEventListener(listener);
    }


    /**
     * Returns the ReaderView object.
     *
     * @return Reader view object.
     */
    public ReaderView getReaderView() {
        return mReaderView;
    }

    /**
     * Opens a SimpleDocumentViewer object at the specified path.
     *
     * @param filePath The path and the name of the accessible PDF.
     * @param password The password allowing to unlock the document, if encrypted.
     */
    public void openFile(String filePath, String password) {
        mFilePath = filePath;
        mReaderView.openFile(filePath, password);
    }

    public void openStream(FileInputStream stream, int length, String password) {
        mReaderView.openStream(stream, length, password);
    }

    /**
     * Opens a PDF document with the specified data.
     *
     * @param data     A byte array representing a PDF document.
     * @param len      The data length.
     * @param password The password allowing to unlock the document, if encrypted.
     */
    public void openData(byte[] data, int len, String password) {
        mFileData = data;
        mReaderView.openData(data, len, password);
    }

    /**
     * Opens a PDF document from a JetStream Server.
     *
     * @param url      The url data of the JetStream server encapsulated in a String object.
     * @param port     The port number of the JetStream server.
     * @param filename The PDF file name to be opened on the JetStream server.
     * @param password The password allowing to unlock the document, if encrypted.
     */
    public void openJetStreamUrl(String url, int port, String filename,
                                 String password) {
        mReaderView.openJetStreamUrl(url, port, filename, password);
    }

    /**
     * Opens a PDF document from a JetStream Server.
     *
     * @param url      The url data of the JetStream server encapsulated as a String object.
     * @param port     The port number of the JetStream server.
     * @param filename The PDF file name to be opened on the JetStream server.
     * @param password The password allowing to unlock the document, if encrypted.
     * @param service  The service name of the JetStream server. Default is "/jss/jetStream.service".
     */
    public void openJetStreamUrl(String url, int port, String filename,
                                 String password, String service) {
        mReaderView.openJetStreamUrl(url, port, filename, password, service);
    }

    /**
     * Opens a PDF document with the given URL.
     *
     * @param url      The url data representing a web address as a String object.
     * @param password The password allowing to unlock the document, if encrypted.
     */
    public void openUrl(String url, String password) {
        mReaderView.openUrl(url, password);
    }

    /**
     * Restores the state of the PDF according to the passed-in object.
     *
     * @param object The state to be restored.
     */
    public void restoreReaderState(Object object) {
        mReaderView.restoreSavedState(object);
    }

    /**
     * Returns the current state's value.
     *
     * @return The current PDF's state.
     */
    public Object getReaderState() {
        return mReaderView.getState();
    }

    /**
     * Saves the bundle object of the controller state.
     *
     * @param state The bundle object.
     */
    public void saveControlState(Bundle state) {
        mControlView.saveState(state);
    }

    /**
     * Restores the state of the controller with the passed-in bundle object.
     *
     * @param state The bundle object containing the controller state's info.
     */
    public void restoreControlState(Bundle state) {
        mControlView.restoreState(state);
    }


    /**
     * Sets the control view's title.
     *
     * @param title Title to be set on the control view.
     */
    public void setTitle(String title) {
        mControlView.setTitle(title);
    }


    /**
     * Gets the PDF document.
     *
     * @return PDF document.
     */
    public PDFDocument getDocument() {
        return mReaderView.getDocument();
    }

    /**
     * Sets the given index's to the current page.
     *
     * @param pageIdx The (zero-based) index of the page to be displayed.
     */
    public void goToPage(int pageIdx) {
        mReaderView.goToPage(pageIdx);
    }

    /**
     * Returns the (zero-based) index of the current display page.
     */
    public int getPageIdx() {
        return mReaderView.getPageIdx();
    }

    /**
     * Finds the given string, displaying the hit on the page if there is a match.
     *
     * @param keyword   The string to be found.
     * @param direction The direction of the next page (1 : front , -1 : reverse)
     */
    public void search(String keyword, int direction) {
        mReaderView.search(keyword, direction);
    }

    /**
     * Stops the current search, deleting the hit from any page view.
     */
    public void stopSearch() {
        mReaderView.stopSearch();
    }

    /**
     * Refreshes the current layout.
     */
    public void refreshLayout() {
        if (mReaderView != null) {
            mReaderView.refreshLayout();
        }

        if (mControlView != null) {
            mControlView.refreshLayout();
        }
    }

    /**
     * Enables/disables the Control Bar's Annotation Menu.
     *
     * @param enable true if the Annotation Menu is shown; otherwise false.
     */
    public void enableAnnotationMenu(boolean enable) {
        mControlView.showEditButton(enable);
    }

    /**
     * Enables the annotation feature of the specified type.
     *
     * @param types  The annotation type as a String object. Eg. "INK:NOTE:LINK"
     * @param enable true if the annotation is enabled; otherwise false.
     */
    public void enableAnnotationFeature(String types, boolean enable) {
        mControlView.enableAnnotFeature(types, enable);
        mReaderView.enableAnnotFeature(types, enable);
    }

    /**
     * Sets the list's display mode and changes the screen.
     * (BILATERAL and THUMBNAIL mode not supported yet)
     *
     * @param mode This value is one of those available in the "PageDisplayMode" enumeration.
     */
    public void setPageDisplayMode(PageDisplayMode mode) {

        switch (mode) {
            case HORIZONTAL:
                mControlView.setHorizontalMode();
                break;
            case VERTICAL:
                mControlView.setVerticalMode();
                break;
            case CONTINUOS:
                mControlView.setContinuosMode();
                break;
            case BILATERAL_VERTICAL:
                mControlView.setBilateralVerticalMode();
                break;
            case BILATERAL_HORIZONTAL:
                mControlView.setBilateralHorizontalMode();
                break;
            case BILATERAL_REALISTIC:
                mControlView.setBilateralRealisticMode();
                break;
            case THUMBNAIL:
                mControlView.setThumbnailMode();
                break;
            case REALISTIC:
                mControlView.setRealisticMode();
                break;
        }
    }

    /**
     * Sets the double tap's zoom level. 1 means 100%.
     *
     * @param scale
     */
    public void setDoubleTapZoomLevel(double scale) {
        PropertyManager.setDoubleTapZoomLevel(scale);
    }

    public void flattenFormFields(boolean includedButtonField) {
        mReaderView.flattenFromFields(includedButtonField);
    }

    public void flattenAnnots() {
        mReaderView.flattenAnnots();
    }

    /**
     * Saves the PDF document.
     */
    public void save() {
        mReaderView.save();
    }

    /**
     * Saves the document as a PDF file into the specified path.
     *
     * @param filePath The given file path.
     * @return Saved file path.
     */
    public String saveAsFile(String filePath) {
        return mReaderView.saveAsFile(filePath);
    }

    // / @cond DOXYGEN_HIDE

    /**
     * This is called when the PDF load is finished.
     *
     * @param state {@link OPEN_STATE} The state value is the result of the PDF document load.
     */
    @SuppressLint("InflateParams")
    @Override
    public void onLoadFinish(DocumentState.OPEN state) {
        if (state == OPEN.SUCCESS) {
            mControlView.init(mAct);

            RelativeLayout layout = new RelativeLayout(mAct);
            layout.addView(mReaderView);
            layout.addView(mControlView);

            mAct.setContentView(layout);

            CoordConverter.initCoordConverter(mAct, mReaderView);

            goToPage(0);
        } else if (state == OPEN.WRONG_PASSWD) {

            PasswordDialog dialog = new PasswordDialog(mAct) {

                @Override
                public void onInputtedPassword(String password) {
                    if (null != mFilePath) {
                        mReaderView.openFile(mFilePath, password);
                    } else if (null != mFileData) {
                        mReaderView.openData(mFileData, mFileData.length, password);
                    }
                }
            };
            dialog.show();
        }

        if (mListener != null) {
            mListener.onLoadFinish(state);
        }
    }

    /**
     * Called when the search on the PDF document is finished.
     *
     * @param success Returns true if the search is successful; otherwise, returns false.
     */
    @Override
    public void onSearchFinish(boolean success) {
        if (success) {
            if (mReaderView.getPageDisplayMode() == PageDisplayMode.THUMBNAIL) {
                mControlView.setHorizontalMode();
            }
        }
    }

    /**
     * Called when moving to another page.
     *
     * @param pageIdx   Target page index.
     * @param pageCount Total page count.
     */
    @Override
    public void onGoToPage(int pageIdx, int pageCount) {
        mControlView.updatePageNumber(pageIdx, pageCount);
    }

    /**
     * This is called when the screen touch event occurs.
     *
     * @param e Event info object.
     */
    @Override
    public void onSingleTapUp(MotionEvent e) {
        if (!(mReaderView.getAnnotToolType() instanceof AnnotToolTransform))
            mControlView.toggleControlTabBar();
    }

    /**
     * This is called when the double screen touch event occurs.
     *
     * @param e Event info object.
     */
    @Override
    public void onDoubleTapUp(MotionEvent e) {

    }

    /**
     * Called when the scroll event occurs.
     */
    @Override
    public void onScroll(int distanceX, int distanceY) {
        mControlView.hideTopMenu();
    }

    /**
     * This is called when the page's display mode is changed.
     *
     * @param mode {@link PageDisplayMode}
     */
    @Override
    public void onChangeDisplayMode(PageDisplayMode mode, int pageIndex) {
        if (mode == PageDisplayMode.HORIZONTAL) {
            mControlView.setHorizontalMode();
        } else if (mode == PageDisplayMode.VERTICAL) {
            mControlView.setVerticalMode();
        } else if (mode == PageDisplayMode.CONTINUOS) {
            mControlView.setContinuosMode();
        } else if (mode == PageDisplayMode.BILATERAL_VERTICAL) {
            mControlView.setBilateralVerticalMode();
        } else if (mode == PageDisplayMode.BILATERAL_HORIZONTAL) {
            mControlView.setBilateralHorizontalMode();
        } else if (mode == PageDisplayMode.BILATERAL_REALISTIC) {
            mControlView.setBilateralRealisticMode();
        } else if (mode == PageDisplayMode.THUMBNAIL) {
            mControlView.setThumbnailMode();
        }
    }

    /**
     * Called when there is a change in the zoom level.
     *
     * @param zoomLevel New level value.
     */
    @Override
    public void onChangeZoom(double zoomLevel) {

    }

    /**
     * Encrypts the document.
     *
     * @param userpass  User password (open password)
     * @param ownerpass Owner password (edit password)
     * @param perm      Permissions. For setting permissions refer to {@link #getUserAccessPermissionsWithPrint(boolean, boolean, boolean, boolean, boolean, boolean, boolean) getUserAccessPermissionsWithPrint(...)}.
     * @return true if successful; otherwise false.
     */
    public boolean setEncrypt(String userpass, String ownerpass, int perm) {
        return mReaderView.setEncrypt(userpass, ownerpass, perm);
    }


    /**
     * Gets the page text of the PDF document.
     *
     * @param pageIdx Index of the page to be displayed.
     * @return Page text.
     */
    public String getPageText(int pageIdx) {
        return mReaderView.getPageText(pageIdx);
    }

    /**
     * Clears the reader.
     */
    public void clear() {
        mReaderView.clear();
        mFileData = null;
        mFilePath = null;
    }

    // / @endcond

    @Override
    public void onLongPress(MotionEvent e) {

    }
}
