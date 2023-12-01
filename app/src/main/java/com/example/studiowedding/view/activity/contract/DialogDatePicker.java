package com.example.studiowedding.view.activity.contract;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.studiowedding.utils.FormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogDatePicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // set hiển thị tiếng việt cho calendar
    public static Locale getLocaleForCalendarInstance(Context context) {
        Locale locale = new Locale("vi","VN");
        LocaleList locales = new LocaleList(locale);
        context.getResources().getConfiguration().setLocales(locales);
        context.getResources().updateConfiguration(context.getResources().getConfiguration(),
                context.getResources().getDisplayMetrics());

        return locale;
    }
    //    Dialog date picker
    public void showDatePicker(EditText editText, Context context) throws ParseException {
        Locale locale = new Locale("vi", "VN");
        Locale.setDefault(locale);

        Calendar calendar = Calendar.getInstance(getLocaleForCalendarInstance(context));

        if (editText != null && !TextUtils.isEmpty(editText.getText())) {
            Date currentDate = FormatUtils.parserStringToDate(editText.getText().toString());
            if (currentDate != null) {
                calendar.setTime(currentDate);
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String selectedDate = FormatUtils.formatDateToString(calendar.getTime());
                    editText.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Thay đổi tiêu đề của 2 button trong dialog
        datePickerDialog.setOnShowListener(dialog -> {
            Button positiveButton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            if (positiveButton != null) {
                positiveButton.setText("Chọn");
            }

            if (negativeButton != null) {
                negativeButton.setText("Hủy");
            }
        });
        datePickerDialog.show();
    }
}