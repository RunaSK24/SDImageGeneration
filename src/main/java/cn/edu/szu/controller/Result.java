package cn.edu.szu.controller;

/**
 * 返回给前端的数据类型
 * (统一返回数据格式)
 */
public class Result {
    private Integer Code;
    private Object Data;
    private String msg;

    public Result() {
    }

    public Result(Integer code, Object data) {
        Code = code;
        Data = data;
    }

    public Result(Integer code, Object data, String msg) {
        Code = code;
        Data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
