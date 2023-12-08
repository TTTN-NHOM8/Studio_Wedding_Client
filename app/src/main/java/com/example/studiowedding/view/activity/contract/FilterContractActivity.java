package com.example.studiowedding.view.activity.contract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studiowedding.R;

public class FilterContractActivity extends Dialog {

    private LinearLayout linearFilterContract;
    private TextView tvCancel,tvAll,tvPaid,tvUnpaid,tvOnGoing,tvHadDone,tvHaveOncurrent;
    private Context context;
    private OnFilterSelectedListener filterSelectedListener;

    public FilterContractActivity(@NonNull Context context) {
        super(context);

        this.context=context;
    }
    public void setFilterSelectedListener(OnFilterSelectedListener listener) {
        this.filterSelectedListener = listener;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.horizontalMargin = 0;
        params.verticalMargin = 0;
        window.setAttributes(params);
        window.setWindowAnimations(R.style.DialogAnimation);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_filter_contract);
        intitView();
        onClickListener();

    }
    private void intitView(){
        linearFilterContract=findViewById(R.id.linearFilterContract);
        tvCancel=findViewById(R.id.tvCancelFilterContract);
        tvAll=findViewById(R.id.tvFilterAllContract);
        tvPaid=findViewById(R.id.tvPaidFilterContract);
        tvUnpaid=findViewById(R.id.tvUnPaidFilterContract);
        tvOnGoing=findViewById(R.id.tvOnGoingFilterContract);
        tvHadDone=findViewById(R.id.tvHadDoneFilterContract);
        tvHaveOncurrent=findViewById(R.id.tvHaveOncurrentFilterContract);
    }
    private void onClickListener(){
        tvPaid.setOnClickListener(view -> triggerFilterSelected("Đã thanh toán"));
        tvUnpaid.setOnClickListener(view -> triggerFilterSelected("Chưa thanh toán"));
        tvOnGoing.setOnClickListener(view -> triggerFilterSelected("Đang thực hiện"));
        tvHadDone.setOnClickListener(view -> triggerFilterSelected("Đã hoàn thành"));
        tvHaveOncurrent.setOnClickListener(view -> triggerFilterSelected("Có phát sinh"));
        tvAll.setOnClickListener(view -> triggerFilterSelected("Tất cả"));
        tvCancel.setOnClickListener(view -> dismiss());
    }
    private void triggerFilterSelected(String selectedStatus) {
        if (filterSelectedListener != null) {
            filterSelectedListener.onFilterSelected(selectedStatus);
        }
        dismiss();
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(String selectedStatus);
    }


}