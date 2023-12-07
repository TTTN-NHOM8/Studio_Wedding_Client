package com.example.studiowedding.network;

public class ManagerUrl {



    public static final String BASE_URL = "http://192.168.1.4:3000/api/";

    //account
    public static final String ACCOUNT = "account/Login";
    public static final String ACCOUNT_ALL = "account/get-updated-info/{idNhanVien}";
    public static final String ACCOUNT_CHANGEPASSWORK = "account/changePassword";
    public static final String ACCOUNT_UPDATE_EMPLOYEE_INFO = "account/update";
    public static final String ACCOUNT_DOANHTHU = "getDailyRevenue/{ngay}";
    public static final String ACCOUNT_DOANHTHUMONTH = "getDailyRevenueMonth/{thang}";
    public static final String ACCOUNT_DOANHTHUYERT = "getDailyRevenueyert/{nam}";



    //Customer
    public static final String READ_CUSTOMER = "customer";
    public static final String UPDATE_Customer = "customer/update/{idKhachHang}";



    // Account

    // Contract
    public  static  final String CONTRACTS="contracts";
    public  static  final String CONTRACTS_ID="contract/{idHopDong}";
    public  static  final String ADD_CONTRACT="contract/add";
    public  static  final String CONTRACT_UPDATE="contract/update/{idHopDong}";
    public  static  final String CONTRACT_DELETE="contract/delete/{idHopDong}";
    public  static  final String INCURRENT_LIST="incurrent/incurrentList/{idHopDong}";
    public  static  final String INCURREN_UPDATE="incurrent/update/{idPhatSinh}";
    public  static  final String INCURREN_UPDATE_NONE="incurrent/update/none/{idPhatSinh}";
    public  static  final String CONTRACT_CLIENTS="contracts/clients";
    public  static  final String CONTRACT_DETAIL_CONTRACT="contracts/detail-contract/{idHDTamThoi}";
    public  static  final String CONTRACT_DELETE_TASK="contract/task/delete/{idHDTamThoi}";



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

    public static final String READ_TASK_EMPLOYEE = "task/employees";
    public static final String READ_EMPLOYEE = "employees/{idHDCT}";
    public static final String READ_EMPLOYEE_ID_TASK = "join/task/employees";
    public static final String INSERT_EMPLOYEE = "insert/employee";
    public static final String DELETE_EMPLOYEE = "delete/employee/{idJoin}";

    // Service
    public static final String GET_SERVICES_URL = "services";
    public static final String INSERT_SERVICE_URL = "services";
    public static final String UPDATE_SERVICE_URL = "services/{serviceID}/update";
    public static final String REMOVE_SERVICE_URL = "services/{serviceID}/delete";
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
