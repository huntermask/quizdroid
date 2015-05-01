package edu.washington.hmask.quizdroid;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnswerFragment extends Fragment {

    private List<Question> remainingQuestions = new ArrayList<>();

    private int correctCount;

    private int totalCount;

    private Question currentQuestion;

    private int selectedIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // If the activity was resumed
            remainingQuestions = savedInstanceState.getParcelableArrayList("remainingQuestions");
            setCorrectCount(savedInstanceState.getInt("correctCount"));
            setTotalCount(savedInstanceState.getInt("totalCount"));
            setCurrentQuestion((Question) savedInstanceState.getParcelable("currentQuestion"));
            setSelectedIndex(savedInstanceState.getInt("selectedIndex"));
        }

    }

    public void returnToMenu() {
        Intent i = new Intent(getActivity(), TopicListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void loadNextQuestion() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setRemainingQuestions(remainingQuestions);
        questionFragment.setTotalCount(totalCount);
        questionFragment.setCorrectCount(correctCount);

        ft.replace(getId(), questionFragment);

        ft.commit();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View answerView = inflater.inflate(R.layout.fragment_answer, container, false);

        TextView yourAnswerTextView = (TextView) answerView.findViewById(R.id.yourAnswerTextView);
        yourAnswerTextView.setText(getCurrentQuestion().getAnswers().get(getSelectedIndex()));

        TextView correctAnswerTextView = (TextView) answerView.findViewById(R.id.correctAnswerTextView);
        correctAnswerTextView.setText(getCurrentQuestion().getCorrectAnswer());

        TextView correctCountTextView = (TextView) answerView.findViewById(R.id.correctCountTextView);
        correctCountTextView.setText("You have " + getCorrectCount() + " out of " + getTotalCount() + " correct");

        Button nextButton = (Button) answerView.findViewById(R.id.nextButton);
        if (remainingQuestions.size() == 0) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remainingQuestions.size() == 0) {
                    returnToMenu();
                } else {
                    loadNextQuestion();
                }
            }
        });

        return answerView;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public List<Question> getRemainingQuestions() {
        return remainingQuestions;
    }

    public void setRemainingQuestions(List<Question> remainingQuestions) {
        this.remainingQuestions = remainingQuestions;
    }
}
