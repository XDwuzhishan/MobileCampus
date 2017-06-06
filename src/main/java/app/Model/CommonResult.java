package app.Model;

import java.util.List;

/**
 * Created by xdcao on 2017/6/6.
 */
public class CommonResult {

    private int status;
    private String message;
    private List data;

    public CommonResult(int status, String message, List data) {
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

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
