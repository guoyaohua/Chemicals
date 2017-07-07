/*
 * Copyright (C) 2013 ePapyrus, Inc. All rights reserved.
 *
 * This file is part of the PlugPDF Android project.
 */

/*
 * PasswordDialog.java
 * 
 * Version:
 *       id
 *       
 * Revision:
 *      logs
 */

package com.epapyrus.plugpdf;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * Creates the password dialog.
 *
 * @author ePapyrus
 * @see <a href="http://www.plugpdf.com">http://www.plugpdf.com</a>
 */
public abstract class PasswordDialog {

    private EditText password;
    private Dialog dialog;

    /**
     * Constructor.
     *
     * @param context {@link Context}
     */
    public PasswordDialog(Context context) {

        dialog = createDialog(context);
    }

    /**
     * Creates the password dialog.
     *
     * @param context {@link Context}
     * @return Alert {@link Dialog}
     */
    public Dialog createDialog(Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 10, 10, 10);

        TextView label = new TextView(context);
        password = new EditText(context);

        label.setText("PASSWORD: ");

        password.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password.setTransformationMethod(PasswordTransformationMethod
                .getInstance());

        label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        password.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        layout.addView(label);
        layout.addView(password);

        alertDialogBuilder.setView(layout);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onInputtedPassword(password.getText().toString());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return alertDialogBuilder.create();
    }

    /**
     * Shows the password dialog.
     */
    public void show() {
        dialog.show();
    }

    /**
     * If the password is inputted.
     *
     * @param password To open the encrypted PDF document.
     */
    public abstract void onInputtedPassword(String password);
}
