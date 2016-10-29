package common;/*
 * Programmer:liuboen
 * Date:16/10/3
 */

public enum ResultCode {
    SUCCESS("S00000","success"),
    ANOMALY("A55555","anomaly"),
    FAILURE("F99999","failure");
    private String code;
    private String msg;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
