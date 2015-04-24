package edu.washington.hmask.quizdroid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Hunter Mask on 4/23/15.
 */
public class Topic {

    private String name;
    private String description;
    private List<Question> questions = new ArrayList<>();

    public Topic(String name, String description, Collection<Question> questions) {
        this.name = name;
        this.description = description;
        this.questions.addAll(questions);
    }

    public Topic(String name, String description) {
        this(name, description, new ArrayList<Question>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
