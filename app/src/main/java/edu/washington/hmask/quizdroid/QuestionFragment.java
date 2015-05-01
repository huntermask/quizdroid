package edu.washington.hmask.quizdroid;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class QuestionFragment extends Fragment implements View.OnClickListener {

    private List<Question> remainingQuestions = new ArrayList<>();

    private int correctCount;

    private int totalCount;

    private Question currentQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // If the activity was resumed
            remainingQuestions = savedInstanceState.getParcelableArrayList("remainingQuestions");
            setCorrectCount(savedInstanceState.getInt("correctCount"));
            setTotalCount(savedInstanceState.getInt("totalCount"));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("remainingQuestions", new ArrayList<Parcelable>(remainingQuestions));
        outState.putInt("correctCount", correctCount);
        outState.putInt("totalCount", totalCount);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View questionView = inflater.inflate(R.layout.fragment_question, container, false);

        setCurrentQuestion(getRemainingQuestions().remove(0));

        TextView questionTextView = (TextView) questionView.findViewById(R.id.questionTextView);
        questionTextView.setText(getCurrentQuestion().getText());

        RadioGroup options = (RadioGroup) questionView.findViewById(R.id.options);

        RadioButton optionOne = (RadioButton) options.findViewById(R.id.optionOne);
        optionOne.setText(getCurrentQuestion().getAnswers().get(0));
        optionOne.setOnClickListener(this);

        RadioButton optionTwo = (RadioButton) options.findViewById(R.id.optionTwo);
        optionTwo.setText(getCurrentQuestion().getAnswers().get(1));
        optionTwo.setOnClickListener(this);

        RadioButton optionThree = (RadioButton) options.findViewById(R.id.optionThree);
        optionThree.setText(getCurrentQuestion().getAnswers().get(2));
        optionThree.setOnClickListener(this);

        RadioButton optionFour = (RadioButton) options.findViewById(R.id.optionFour);
        optionFour.setText(getCurrentQuestion().getAnswers().get(3));
        optionFour.setOnClickListener(this);

        Button submitButton = (Button) questionView.findViewById(R.id.submitButton);
        submitButton.setEnabled(false);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
            }
        });

        return questionView;
    }

    public void showAnswer() {
        RadioGroup options = (RadioGroup) getView().findViewById(R.id.options);
        RadioButton selectedOption = (RadioButton) options.findViewById(options.getCheckedRadioButtonId());
        int selectedIndex = options.indexOfChild(selectedOption);

        setTotalCount(getTotalCount() + 1);
        if (getCurrentQuestion().getCorrectAnswerIndex() == selectedIndex) {
            setCorrectCount(getCorrectCount() + 1);
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left);

        AnswerFragment answerFragment = new AnswerFragment();
        answerFragment.setRemainingQuestions(getRemainingQuestions());
        answerFragment.setCorrectCount(getCorrectCount());
        answerFragment.setTotalCount(getTotalCount());
        answerFragment.setCurrentQuestion(getCurrentQuestion());
        answerFragment.setSelectedIndex(selectedIndex);

        ft.replace(getId(), answerFragment);

        ft.commit();
    }

    @Override
    public void onClick(View v) {
        if (v.getClass().equals(RadioButton.class)) {
            Button submitButton = (Button) getView().findViewById(R.id.submitButton);
            submitButton.setEnabled(true);
        }
    }

    public List<Question> getRemainingQuestions() {
        return remainingQuestions;
    }

    public void setRemainingQuestions(List<Question> remainingQuestions) {
        this.remainingQuestions = remainingQuestions;
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
}
