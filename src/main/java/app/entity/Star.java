package app.entity;

/**
 * Created by xdcao on 2017/7/30.
 */
public class Star {

    private Long userId;
    private Long answerId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
}
