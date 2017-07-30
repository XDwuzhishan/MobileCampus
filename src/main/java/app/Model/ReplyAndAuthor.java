package app.Model;

import app.entity.Reply;
import app.entity.User;

/**
 * Created by xdcao on 2017/7/30.
 */
public class ReplyAndAuthor {

    private Reply reply;
    private User user;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
