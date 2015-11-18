package com.example.hate.bignerd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity
{
    private static final String KEY_INDEX = "index";
    private static final String TAG = "QuizActivity";
    private static final int REQUEST_KEY_CHEAT = 0;

    private ImageButton mNextButton,mBackButton;
    private Button mTrueButton,mFalseButton,mCheatButton;
    private TextView mTextView;
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.question_1,false),
                    new Question(R.string.question_2,true),
                    new Question(R.string.question_3,true),
                    new Question(R.string.question_4,true),
                    new Question(R.string.question_5,false),
                    new Question(R.string.question_6,true),
                    new Question(R.string.question_7,true),
                    new Question(R.string.question_8,false),
                    new Question(R.string.question_9,false),
                    new Question(R.string.question_10,true),
                    new Question(R.string.question_11,true),
                    new Question(R.string.question_12,true),
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate(Bundle) called");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                startActivityForResult(intent,REQUEST_KEY_CHEAT);
            }
        });
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(true);
            }
        });
        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer(false);
            }
        });
        mTextView = (TextView)findViewById(R.id.textView);
        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        });
        mBackButton = (ImageButton)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    mCurrentIndex = (mCurrentIndex-1) % mQuestionBank.length;
                    updateQuestion();
                }
                catch (IndexOutOfBoundsException e)
                {
                    AlertDialog dialog = builder.create();
                    dialog.setMessage("No questions taken yet");
                    dialog.show();
                    mCurrentIndex = 0;
                }

            }
        });
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        updateQuestion();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if (requestCode == REQUEST_KEY_CHEAT)
        {
            if (data == null)
            {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstance Called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int msgResId = 0;
        if (mIsCheater)
        {
            msgResId = R.string.judgement_toast;
        }
        else
        {
            if (userPressedTrue == answerIsTrue)
            {
                msgResId = R.string.correct_snack;
            }
            else
            {
                msgResId = R.string.wrong_snack;
            }
        }
        Toast.makeText(this,msgResId,Toast.LENGTH_SHORT).show();
    }
    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mTextView.setText(question);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

}
