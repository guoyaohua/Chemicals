/*
 * Copyright (C) 2013 ePapyrus, Inc. All rights reserved.
 *
 * This file is part of the PlugPDF Android project.
 */

/*
 * SimpleReaderFactory.java
 *
 * Version:
 *       id
 *
 * Revision:
 *      logs
 */

package com.epapyrus.plugpdf;

import android.app.Activity;

/**
 * Custom PDF viewers can be created by using the PDFDocument class directly.
 *
 * @author ePapyrus
 * @brief Generates and returns the SimpleDocumnetViewer.
 * @see <a href="http://www.plugpdf.com">http://www.plugpdf.com</a>
 */
public class SimpleReaderFactory {

    /**
     * Creates and returns a PDF viewer used for viewing, reading, and
     * navigating a PDF document.
     *
     * @param act      The current application's activity.
     * @param listener The SimpleDocumentViewerListener instance.
     * @return SimpleDocumentViewer The default PDF document viewer instance.
     */
    public static SimpleDocumentReader createSimpleViewer(Activity act,
                                                          SimpleDocumentReaderListener listener) {

        SimpleDocumentReader viewer = new SimpleDocumentReader(act);
        viewer.setListener(listener);

        return viewer;
    }
}
