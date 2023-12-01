package com.example.studiowedding.utils;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.studiowedding.R;
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

    /**
     * Hàm thay đổi hình ảnh của nút hiển thị password và thay đổi thuộc tính InputType
     */
    public static void togglePasswordVisibleWithImage(EditText passwordEditText, ImageView passwordToggleImage) {
        TransformationMethod transformationMethod;
        int imgResource;

        boolean isPasswordVisible = passwordEditText.getTransformationMethod() instanceof HideReturnsTransformationMethod;
        if (isPasswordVisible) {
            transformationMethod = PasswordTransformationMethod.getInstance();
            imgResource = R.drawable.ic_eye;
        } else {
            transformationMethod = HideReturnsTransformationMethod.getInstance();
            imgResource = R.drawable.ic_eye_true;
        }

        passwordEditText.setTransformationMethod(transformationMethod);
        passwordToggleImage.setImageResource(imgResource);
    }
}
