package com.example.studiowedding.view.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studiowedding.R;
import com.example.studiowedding.adapter.ServiceAdapter;
import com.example.studiowedding.model.Service;
import com.example.studiowedding.network.ApiClient;
import com.example.studiowedding.network.ApiService;
import com.example.studiowedding.view.activity.contract.AddContractActivity;
import com.example.studiowedding.view.activity.detail_contract.ServerResponse;
import com.example.studiowedding.view.activity.services.AddServiceActivity;
import com.example.studiowedding.view.activity.services.ServiceResponse;
import com.example.studiowedding.view.activity.services.UpdateServiceActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment implements ServiceAdapter.ItemListener {
    public static final String SERVICE_UPDATE_EXTRA = "SERVICE_UPDATE_EXTRA";
    private RecyclerView serviceRecyclerView;
    private FloatingActionButton fabAdd;
    private EditText searchEditText;
    private ServiceAdapter serviceAdapter;
    private ImageView imgFillter;
    private List<Service> serviceList;
    private CoordinatorLayout container;
    BottomSheetDialog bottomSheetDialogFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Khởi tạo dialog lọc theo giá
        bottomSheetDialogFilter = new BottomSheetDialog(getContext());
        bottomSheetDialogFilter.setContentView(R.layout.layout_fillter_service);
        bottomSheetDialogFilter.setCancelable(false);

        initView(view);
        setUpRecyclerView();
        callApiGetServices();
        performSearchServices();
        setListeners();
    }


    private void initView(@NonNull View view) {
        serviceRecyclerView = view.findViewById(R.id.serviceRecyclerView);
        fabAdd = view.findViewById(R.id.fabAdd);
        searchEditText = view.findViewById(R.id.searchEditText);
        imgFillter = view.findViewById(R.id.imgFilter);
        container = view.findViewById(R.id.container);
    }

    private void setListeners() {
        imgFillter.setOnClickListener(v -> showBottomSheetDialog());
        fabAdd.setOnClickListener(view -> startAddServiceActivity());
    }

    public void startAddServiceActivity() {
        startActivity(new Intent(getActivity(), AddServiceActivity.class));
    }

    private void setUpRecyclerView() {
        serviceAdapter = new ServiceAdapter(this);
        serviceRecyclerView.setAdapter(serviceAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        serviceRecyclerView.setLayoutManager(layoutManager);
    }

    // Gọi API lấy danh sách dịch vụ
    private void callApiGetServices() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Service>> call = apiService.getServices();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    List<Service> services = response.body();
                    if (services != null) {
                        serviceAdapter.setServices(services);
                        serviceList = services;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });
    }

    // Tìm kiếm dịch vụ theo tên
    private void performSearchServices() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchQuery = charSequence.toString().toLowerCase();
                if (searchQuery.isEmpty()) {
                    callApiGetServices();
                } else {
                    List<Service> filteredServices = new ArrayList<>();
                    for (Service service : serviceList) {
                        if (service.getName().toLowerCase().contains(searchQuery)) {
                            filteredServices.add(service);
                        }
                    }
                    serviceAdapter.setServices(filteredServices);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // Hiển thi bottom sheet lọc giá dịch vụ
    private void showBottomSheetDialog() {
        ImageView imgClose = bottomSheetDialogFilter.findViewById(R.id.imgClose);
        TextView doneTextView = bottomSheetDialogFilter.findViewById(R.id.tvDone);
        RadioGroup radioGroup = bottomSheetDialogFilter.findViewById(R.id.radio_group_price);

        imgClose.setOnClickListener(view -> bottomSheetDialogFilter.dismiss());

        doneTextView.setOnClickListener(view -> {
            List<Service> filteredServices = new ArrayList<>();
            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            for (Service service : serviceList) {
                switch (checkedRadioButtonId) {
                    case R.id.radio_button_all:
                        // Lọc tất cả
                        callApiGetServices();
                        break;
                    case R.id.radio_button_under_1_million:
                        // Lọc từ 0 đến 1 triệu
                        if (service.getPrice() > 0 && service.getPrice() < 1000000) {
                            filteredServices.add(service);
                        }
                        break;
                    case R.id.radio_button_from_1_to_3_million:
                        // Lọc từ 1 triệu đến 3 triệu
                        if (service.getPrice() >= 1000000 && service.getPrice() <= 3000000) {
                            filteredServices.add(service);
                        }
                        break;
                    case R.id.radio_button_from_3_to_5_million:
                        // Lọc từ 3 triệu đến 5 triệu
                        if (service.getPrice() >= 3000000 && service.getPrice() <= 5000000) {
                            filteredServices.add(service);
                        }
                        break;
                    case R.id.radio_button_over_5_million:
                        // Lọc lớn hơn 5 triệu
                        if (service.getPrice() > 5000000) {
                            filteredServices.add(service);
                        }
                        break;
                    default:
                        break;
                }
            }
            serviceAdapter.setServices(filteredServices);
            bottomSheetDialogFilter.dismiss();
        });

        bottomSheetDialogFilter.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        callApiGetServices();
    }

    @Override
    public void startUpdateServieActivity(Service service) {
        Intent intent = new Intent(getContext(), UpdateServiceActivity.class);
        intent.putExtra(SERVICE_UPDATE_EXTRA, service);
        startActivity(intent);
    }

    @Override
    public void showConfirmDeleteService(Service service) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Xác nhận xoá dịch vụ: " + service.getName());
        builder.setNegativeButton("Huỷ", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("Xoá", (dialogInterface, i) -> {
            // Thực hiện xoá dịch vụ theo mã dịch vụ
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ServiceResponse> call = apiService.removeService(service.getId());
            call.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    if (response.isSuccessful()) {
                        ServiceResponse serviceResponse = response.body();
                        if (serviceResponse != null) {
                            if (serviceResponse.isSuccess()) {
                                showSnackbar("Xoá dịch vụ thành công.");
                                callApiGetServices();
                            } else if(serviceResponse.isFailure()) {
                                showSnackbar("Xoá dịch vụ không thành công.");
                            } else {
                                showSnackbar("Xảy ra lỗi khi xoá dịch vụ.");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {

                }
            });
        });
        builder.show();
    }

    // Hiển thị thông báo
    private void showSnackbar(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }
}
