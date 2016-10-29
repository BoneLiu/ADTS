/*
 * Programmer:liuboen
 * Date:16/10/3
 */
package common;

public class Response<T> {
    private T result;
    private ResultCode code;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public Response(T result) {
        this.result = result;
        this.code = ResultCode.SUCCESS;
    }

    public Response(ResultCode code) {
        this.code = code;
        this.result=null;
    }
    public boolean isSuccess(){
        return getCode().equals(ResultCode.SUCCESS);
    }
}
