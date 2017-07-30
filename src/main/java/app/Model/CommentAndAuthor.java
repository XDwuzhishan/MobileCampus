package app.Model;

import app.entity.Comment;
import app.entity.User;

/**
 * Created by xdcao on 2017/7/30.
 */
public class CommentAndAuthor {

    private Comment comment;
    private User user;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
