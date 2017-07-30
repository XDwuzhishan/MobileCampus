package app.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by xdcao on 2017/6/6.
 */
public class CommonResult {

    private int status;
    private String message;
    private Object data;

    public CommonResult() {
    }

    public CommonResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
