package com.example.studiowedding.view.activity.detail_contract;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.studiowedding.R;

public class UpdateContractProductActivity extends AppCompatActivity {
    private EditText
            contractIdEditText,
            productSelectEditText,
            priceEditText,
            dateOfHireEditText,
            dateOfReturnEditText;
    private RelativeLayout updateButton;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contract_product);

        initView();
        setListeners();
    }

    private void initView() {
        contractIdEditText = findViewById(R.id.contractIdEditText);
        productSelectEditText = findViewById(R.id.productSelectEditText);
        priceEditText = findViewById(R.id.priceEditText);
        dateOfHireEditText = findViewById(R.id.dateOfHireEditText);
        dateOfReturnEditText = findViewById(R.id.dateOfReturnEditText);
        updateButton = findViewById(R.id.updateButton);
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void setListeners() {
        backImageView.setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }
}