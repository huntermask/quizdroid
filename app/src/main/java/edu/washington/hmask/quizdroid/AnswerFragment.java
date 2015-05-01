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
        //setContentView(R.layout.activity_answer);

        if (savedInstanceState != null) {
            // The activity was resumed
            remainingQuestions = savedInstanceState.getParcelableArrayList("remainingQuestions");
            setCorrectCount(savedInstanceState.getInt("correctCount"));
            setTotalCount(savedInstanceState.getInt("totalCount"));
            setCurrentQuestion((Question) savedInstanceState.getParcelable("currentQuestion"));
            setSelectedIndex(savedInstanceState.getInt("selectedIndex"));
        } /*else {
            // The activity came from an intent/previous activity
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                remainingQuestions = extras.getParcelableArrayList("remainingQuestions");
                correctCount = extras.getInt("correctCount");
                totalCount = extras.getInt("totalCount");
                currentQuestion = extras.getParcelable("currentQuestion");
                selectedIndex = extras.getInt("selectedIndex");
            }
        }*/



    }

    public void returnToMenu() {
        Intent i = new Intent(getActivity(), TopicListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void loadNextQuestion() {
        /*Intent i = new Intent(this, QuestionFragment.class);
        i.putParcelableArrayListExtra("remainingQuestions", (ArrayList)remainingQuestions);
        i.putExtra("totalCount", totalCount);
        i.putExtra("correctCount", correctCount);
        startActivity(i);*/
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.setRemainingQuestions(remainingQuestions);
        questionFragment.setTotalCount(totalCount);
        questionFragment.setCorrectCount(correctCount);

        ft.replace(getId(), questionFragment);


        ft.commit();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View answerView = inflater.inflate(R.layout.activity_answer, container, false);

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
