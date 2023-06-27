package com.example.shopping.utils;

/**
 * 网络连接配置
 */
public class Constants {


//    public static final String BASE_REQ = "http://172.20.10.3:8080/static";
//    public static final String BASE_REQ2 = "http://172.20.10.3:8080/shop_ssm_war";
    public static final String BASE_REQ = "http://192.168.31.162:8080/static";
    public static final String BASE_REQ2 = "http://192.168.31.162:8080/shop_ssm_war";
    // 请求loginController
    public static final String LoginController = BASE_REQ + "/login/getByEmailAndPassword";

    public static final String Login_GetVerificationCode = LoginController + "/getVerificationCode";
    public static final String Login_Register = LoginController + "/register";
    public static final String Login_ForgetPassword = LoginController + "/forgetPassword";
    public static final String Login_UpdatePassword = LoginController + "/updatePassword";

    // 首页
    public static final String HomeController = BASE_REQ + "/home";
    public static final String User_update = HomeController + "/updateUser";

    // Channel 进入 GoodsList
    public static final String GoodsList = BASE_REQ + "/channel";

    // 根据user_id获得GoodsList
    public static final String GoodsList_ByUserId = BASE_REQ + "/getByUserId";

    // 上传商品
    public static final String UploadGood = BASE_REQ + "/upload";

    public static final String Goods_get = BASE_REQ + "/getGoods";
    public static final String Goods_delete = BASE_REQ +"/deleteById";
    public static final String Goods_update = BASE_REQ +"/updatePro";


    // 获得动态消息
    public static final String Dynamic = BASE_REQ + "/dynamic";
    public static final String Dynamic_get = Dynamic + "/isHot";
    public static final String Dynamic_add = Dynamic + "/addDynamic";
    public static final String Dynamic_update = Dynamic + "/updateDynamic";
    public static final String Dynamic_delete = Dynamic + "/deleteDynamic";

    //地址操作
    public static final String Location = BASE_REQ2 + "/location";
    public static final String Location_get = Location + "/getLocations";
    public static final String Location_add = Location + "/addLocation";
    public static final String Location_update = Location + "/updateLocation";
    public static final String Location_delete = Location + "/deleteLocation";

    //我的聊天页面
    private static final String ChatSellers = BASE_REQ2 + "/chat";
    public static final String ChatSellers_get = ChatSellers + "/getChatSellers";
    public static final String ChatContents_get = ChatSellers + "/getChatContent";
    public static final String Seller_get = BASE_REQ + "/getSeller";


    // 搜索功能
    public static final String Search_Goods = BASE_REQ + "/getByLikeName";



    public static Boolean isBackHome = false;
}
