package com.example.studiowedding.utils;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class UIutils {
    /**
     * Hiển thị thông báo
     */
    public static void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void clearTextFields(EditText... editTexts) {
        for (EditText edt : editTexts) {
            edt.setText(null);
        }
    }
}
