package app.entity;


import java.util.Date;

/**
 * Created by xdcao on 2017/6/12.
 */
public class Question {

    private Long id;
    private Date created;
    private Date updated;
    private String title;
    private String desc;
    private Long ownerId;
    private int acknum;
    private String ownername;

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public int getAcknum() {
        return acknum;
    }

    public void setAcknum(int acknum) {
        this.acknum = acknum;
    }
}
