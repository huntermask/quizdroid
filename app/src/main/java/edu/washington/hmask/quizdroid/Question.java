package edu.washington.hmask.quizdroid;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Hunter Mask on 4/23/15.
 */
public class Question implements Parcelable {

    private String text;
    private List<String> answers = new ArrayList<>();
    private int correctAnswerIndex;

    public Question(String text, Collection<String> answers, int correctAnswerIndex) {
        this.setText(text);
        this.answers.addAll(answers);
        this.setCorrectAnswerIndex(correctAnswerIndex);
    }

    public Question(Parcel in) {
        text = in.readString();
        in.readStringList(answers);
        correctAnswerIndex = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeStringList(answers);
        dest.writeInt(correctAnswerIndex);
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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
