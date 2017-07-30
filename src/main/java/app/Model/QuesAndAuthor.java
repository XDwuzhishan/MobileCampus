package app.Model;

import app.entity.Question;
import app.entity.User;

/**
 * Created by xdcao on 2017/7/30.
 */
public class QuesAndAuthor {

    private Question question;
    private User user;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
