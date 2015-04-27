package edu.washington.hmask.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QuestionActivity extends ActionBarActivity implements View.OnClickListener {

    private List<Question> remainingQuestions = new ArrayList<>();

    private int correctCount;

    private int totalCount;

    private Question currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        if (savedInstanceState != null) {
            // The activity was resumed
            remainingQuestions = savedInstanceState.getParcelableArrayList("remainingQuestions");
            correctCount = savedInstanceState.getInt("correctCount");
            totalCount = savedInstanceState.getInt("totalCount");
        } else {
            // The activity came from an intent/previous activity
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                remainingQuestions = extras.getParcelableArrayList("remainingQuestions");
                correctCount = extras.getInt("correctCount");
                totalCount = extras.getInt("totalCount");
            }
        }

        currentQuestion = remainingQuestions.remove(0);

        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        questionTextView.setText(currentQuestion.getText());

        RadioGroup options = (RadioGroup) findViewById(R.id.options);

        RadioButton optionOne = (RadioButton) options.findViewById(R.id.optionOne);
        optionOne.setText(currentQuestion.getAnswers().get(0));
        optionOne.setOnClickListener(this);

        RadioButton optionTwo = (RadioButton) options.findViewById(R.id.optionTwo);
        optionTwo.setText(currentQuestion.getAnswers().get(1));
        optionTwo.setOnClickListener(this);

        RadioButton optionThree = (RadioButton) options.findViewById(R.id.optionThree);
        optionThree.setText(currentQuestion.getAnswers().get(2));
        optionThree.setOnClickListener(this);

        RadioButton optionFour = (RadioButton) options.findViewById(R.id.optionFour);
        optionFour.setText(currentQuestion.getAnswers().get(3));
        optionFour.setOnClickListener(this);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setEnabled(false);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
            }
        });
    }

    public void showAnswer() {
        RadioGroup options = (RadioGroup) findViewById(R.id.options);
        RadioButton selectedOption = (RadioButton) options.findViewById(options.getCheckedRadioButtonId());
        int selectedIndex = options.indexOfChild(selectedOption);

        totalCount++;
        if (currentQuestion.getCorrectAnswerIndex() == selectedIndex) {
            correctCount++;
        }

        Intent i = new Intent(this, AnswerActivity.class);
        i.putParcelableArrayListExtra("remainingQuestions", (ArrayList)remainingQuestions);
        i.putExtra("totalCount", totalCount);
        i.putExtra("correctCount", correctCount);
        i.putExtra("currentQuestion", currentQuestion);
        i.putExtra("selectedIndex", selectedIndex);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if (v.getClass().equals(RadioButton.class)) {
            Button submitButton = (Button) findViewById(R.id.submitButton);
            submitButton.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
