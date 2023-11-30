package cn.edu.szu.controller;

//状态码
public class Code {
    //操作成功代码
    public static final Integer SAVE_OK = 20011;
    public static final Integer DELETE_OK = 20021;
    public static final Integer UPDATE_OK = 20031;
    public static final Integer GET_OK = 20041;

    //操作失败代码
    public static final Integer SAVE_ERR = 20010;
    public static final Integer DELETE_ERR = 20020;
    public static final Integer UPDATE_ERR = 20030;
    public static final Integer GET_ERR = 20040;

    //登录验证
    public static final Integer USER_CHECK_OK = 21001;
    public static final Integer USER_CHECK_ERR = 21000;

    //存储验证
    public static final Integer HIS_LOAD_OK = 22001;
    public static final Integer HIS_LOAD_ERR = 22000;


}
