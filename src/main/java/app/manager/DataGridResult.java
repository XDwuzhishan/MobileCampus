package app.manager;

import java.util.List;

/**
 * Created by xdcao on 2017/8/20.
 */
public class DataGridResult {

    private long total;

    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
