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
public class Topic implements Parcelable {

    private String name;
    private String shortDescription;
    private String longDescription;
    private List<Question> questions = new ArrayList<>();

    public Topic(String name, String shortDescription, String longDescription, Collection<Question> questions) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.questions.addAll(questions);
    }

    public Topic(String name, String shortDescription, String longDescription) {
        this(name, shortDescription, longDescription, new ArrayList<Question>());
    }

    public Topic(Parcel in) {
        name = in.readString();
        shortDescription = in.readString();
        questions = Arrays.asList((Question[])in.readParcelableArray(null));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(shortDescription);
        Question[] qArray = new Question[questions.size()];
        dest.writeParcelableArray(questions.toArray(qArray), flags);
    }

    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator() {
        public Topic createFromParcel(Parcel in) {
            return new Topic(in);
        }

        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
