package app.entity;

import java.util.Date;

/**
 * Created by xdcao on 2017/6/16.
 */
public class Comment {

    private Long id;
    private Long answer_id;
    private String content;
    private Date created;
    private Date updated;
    private Long ownerId;
    private String ownerName;
    private int repNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(Long answer_id) {
        this.answer_id = answer_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getRepNum() {
        return repNum;
    }

    public void setRepNum(int repNum) {
        this.repNum = repNum;
    }
}
