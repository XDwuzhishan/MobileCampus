package app.Model;

import app.entity.Answer;
import app.entity.User;

import java.net.UnknownServiceException;

/**
 * Created by xdcao on 2017/7/30.
 */
public class AnswerAndAuthor {

    private AnswerWithStar answerWithStar;
    private User user;

    public AnswerWithStar getAnswerWithStar() {
        return answerWithStar;
    }

    public void setAnswerWithStar(AnswerWithStar answerWithStar) {
        this.answerWithStar = answerWithStar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
