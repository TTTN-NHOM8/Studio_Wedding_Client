package com.example.studiowedding.network;

public class ManagerUrl {

    public static final String BASE_URL = "http://192.168.1.73:3000/api/";

    //Customer
    public static final String READ_CUSTOMER = "customer";
    public static final String UPDATE_Customer = "customer/update/{idKhachHang}";


    // Account
    public static final String ACCOUNT = "account/Login";

    // Contract
    public  static  final String CONTRACTS="contracts";
    public  static  final String CONTRACTS_ID="contract/{idHopDong}";
    public  static  final String CONTRACTS_PAYMENT="contracts/payment";
    public  static  final String CONTRACTS_PROGESS="contracts/progess";
    public  static  final String CONTRACTS_INCURRENT="contracts/incurrent";
    public  static  final String ADD_CONTRACT="contract/add";
    public  static  final String CONTRACT_UPDATE="contract/update/{idHopDong}";
    public  static  final String CONTRACT_DELETE="contract/delete/{idHopDong}";
    public  static  final String INCURRENT="incurrent";
    public  static  final String INCURRENT_ADD="incurrent/add";
    public  static  final String INCURRENT_DELETE="incurrent/delete/{idPhatSinh}";
    public  static  final String CONTRACT_CLIENTS="contracts/clients";
    public  static  final String CONTRACT_DETAIL_CONTRACT="contracts/detail-contract/{idHDTamThoi}";

    // Detail Contract
    public static final String CONTRACT_DETAILS_URL = "contract-details";
    public static final String INSERT_CONTRACT_DETAIL_PRODUCT = "contract-details/with-product";
    public static final String INSERT_CONTRACT_DETAIL_SERVICE = "contract-details/with-service";
    public static final String UPDATE_CONTRACT_DETAIL_PRODUCT = "contract-details/{contractDetailID}/with-product";
    public static final String UPDATE_CONTRACT_DETAIL_SERVICE = "contract-details/{contractDetailID}/with-service";
    public static final String CONTRACT_DETAIL_SERVICES = "contract-details-services";
    public static final String CONTRACT_DETAIL_PRODUCTS = "contract-details-products";
    public static final String DELETE_CONTRACT_DETAIL_BY_CONTRACT_DETAIL_ID = "contract-details/{contractDetailID}/single";
    public static final String CONTRACT_DETAIL_IDHD="contract-details/{contractIDTemporary}/contract";
    public static final String CONTRACT_DETAIL_REMOVE="contract-details/{contractIDTemporary}";
    public static final String CONTRACT_DETAIL_IDCONTRACT="contract-details/{contractID}";

    // Task
    public static final String READ_TASKS = "tasks";
    public static final String READ_TASKS_ROLE = "tasks/role";
    public static final String UPDATE_TASKS = "update/task/{id}";
    public static final String DELETE_TASKS = "delete/task/{id}";
  // product
    public static final String GET_PRODUCT = "products";
    public static final String GET_PRODUCT_BY_NAME= "products/search";
    public static final String DELETE_PRODUCT = "products/delete/{id}";
    public static final String ADD_PRODUCT = "products/add";
    public static final String UPDATE_PRODUCT = "products/update/{idSanPham}";


    public  static  final String UPDATE_CUSTOMER="customer/update/{id}";

    //API Employee
    public static final String URL_GET_EMPLOYEE = "employee";
    public static final String URL_ADD_EMPLOYEE = "employee/add";
    public static final String URL_UPDATE_EMPLOYEE = "employee/update/{idNhanVien}";

}
