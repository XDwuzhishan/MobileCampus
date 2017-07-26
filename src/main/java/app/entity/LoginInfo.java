package app.entity;

/**
 * Created by xdcao on 2017/7/26.
 */
public class LoginInfo {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String noticer;
    private String date;
    private String subTitle;
    private String content;

    public String getNoticer() {
        return noticer;
    }

    public void setNoticer(String noticer) {
        this.noticer = noticer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
