package cn.edu.szu.controller;

/**
 * 返回给前端的数据类型
 * (统一返回数据格式)
 */
public class Result {
    private Integer code; // 结果码
    private Object data; // 返回的数据
    private String msg; // 返回的消息

    public Result() {
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "Code=" + code +
                ", Data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
