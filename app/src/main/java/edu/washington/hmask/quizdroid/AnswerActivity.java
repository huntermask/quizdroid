package edu.washington.hmask.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnswerActivity extends ActionBarActivity {

    private List<Question> remainingQuestions = new ArrayList<>();

    private int correctCount;

    private int totalCount;

    private Question currentQuestion;

    private int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        if (savedInstanceState != null) {
            // The activity was resumed
            remainingQuestions = savedInstanceState.getParcelableArrayList("remainingQuestions");
            correctCount = savedInstanceState.getInt("correctCount");
            totalCount = savedInstanceState.getInt("totalCount");
            currentQuestion = savedInstanceState.getParcelable("currentQuestion");
            selectedIndex = savedInstanceState.getInt("selectedIndex");
        } else {
            // The activity came from an intent/previous activity
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                remainingQuestions = extras.getParcelableArrayList("remainingQuestions");
                correctCount = extras.getInt("correctCount");
                totalCount = extras.getInt("totalCount");
                currentQuestion = extras.getParcelable("currentQuestion");
                selectedIndex = extras.getInt("selectedIndex");
            }
        }

        TextView yourAnswerTextView = (TextView)findViewById(R.id.yourAnswerTextView);
        yourAnswerTextView.setText(currentQuestion.getAnswers().get(selectedIndex));

        TextView correctAnswerTextView = (TextView)findViewById(R.id.correctAnswerTextView);
        correctAnswerTextView.setText(currentQuestion.getCorrectAnswer());

        TextView correctCountTextView = (TextView)findViewById(R.id.correctCountTextView);
        correctCountTextView.setText("You have " + correctCount + " out of " + totalCount + " correct");

        Button nextButton = (Button)findViewById(R.id.nextButton);
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

    }

    public void returnToMenu() {
        Intent i = new Intent(this, TopicListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void loadNextQuestion() {
        Intent i = new Intent(this, QuestionActivity.class);
        i.putParcelableArrayListExtra("remainingQuestions", (ArrayList)remainingQuestions);
        i.putExtra("totalCount", totalCount);
        i.putExtra("correctCount", correctCount);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_answer, menu);
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
