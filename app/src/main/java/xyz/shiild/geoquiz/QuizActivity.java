package xyz.shiild.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    /********** Fields **********/

    /** The True button. */
    private Button mTrueButton;

    /** The False button. */
    private Button mFalseButton;

    /** The Next button. */
    private ImageButton mNextButton;

    /** The Cheat button. */
    private Button mCheatButton;

    /** The Previous button. */
    private ImageButton mPrevButton;

    /** The Question text view. */
    private TextView mQuestionTextView;

    /** Array of Question objects. Would be created/stored elsewhere in a more complex project. */
    private Question[] mQuestionBank = new Question[] {
        new Question(R.string.question_oceans, true),
        new Question(R.string.question_mideast, false),
        new Question(R.string.question_africa, false),
        new Question(R.string.question_americas, true),
        new Question(R.string.question_asia, true)
    };

    /** Index for the array of question objects. */
    private int mCurrentIndex = 0;

    /** Tag constant for the class name. */
    private static final String TAG = "QuizActivity";

    /** Key for the key-value pair that will be stored in the Bundle. */
    private static final String KEY_INDEX = "index";

    /** User-defined key used to track/differentiate multiple activities. */
    private static final int REQUEST_CODE_CHEAT = 0;


    /********** Methods **********/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        // Get a reference for the TextView and set its text to the question at the current index.
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // Get a reference to the Next button.
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        // Set a listener on it to increment the index and update the TextView's text.
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Point the current index to the next Question.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Set a listener for the Question TextView as well such that clicking it will also
        // proceed to the next question (just like clicking the next Button).
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Point the current index to the next Question.
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // Get a reference to the Previous button.
        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        // Set a listener on it to decrement the index and update the TextView's text.
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Point the current index to the previous question.
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        // To implement the listener for Cheat button, first get a reference to the Cheat button.
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        // Now set a listener on it.
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store the correct answer in answerIsTrue variable
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                // Create an Intent that includes the Activity and the correct answer.
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                // Pass this intent into startActivityForResult to allow QuizActivity to
                // "hear back" from the CheatActivity class.
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        // If savedInstanceState exists, assign the appropriate saved value to mCurrentIndex.
        // This ensures the question text remains the same when the screen is rotated.
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        // Update this Question to the next one.
        updateQuestion();
    }

    /**
     * Update the current Question in the TextView to the Question at current index.
     */
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    /**
     * Accepts a boolean variable that identifies whether the user pressed the True or False
     * button. Then, it will check the user's answer against the answer in the current Question
     * object. Finally, after determining whether the user answered correctly, it will make a
     * Toast that displays the appropriate message to the user.
     * @param userPressedTrue   True if user presses the True button, False if the False button.
     */
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
}
