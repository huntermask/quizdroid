package edu.washington.hmask.quizdroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Hunter Mask on 4/23/15.
 */
public class Question {

    private String text;
    private List<String> answers = new ArrayList<>();
    private int correctAnswerIndex;

    public Question(String text, Collection<String> answers, int correctAnswerIndex) {
        this.setText(text);
        this.answers.addAll(answers);
        this.setCorrectAnswerIndex(correctAnswerIndex);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public List<String> getAnswers() {
        return this.answers;
    }

    public String getCorrectAnswer() {
        return answers.get(correctAnswerIndex);
    }
}
