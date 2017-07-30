package app.Model;

import app.entity.Answer;

/**
 * Created by xdcao on 2017/7/30.
 */
public class AnswerWithStar {

    private Answer answer;
    private boolean isStared;

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public boolean isStared() {
        return isStared;
    }

    public void setStared(boolean stared) {
        isStared = stared;
    }
}
