package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alonsnarf.theenglish.R;
import com.alonsnarf.theenglish.object.Setting;
import com.alonsnarf.theenglish.object.Twin;
import com.alonsnarf.theenglish.service.SettingService;
import com.alonsnarf.theenglish.service.VerbService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameActivity extends Activity implements View.OnClickListener
{
  private static final String FORMAT = "%02d:%02d:%01d";
  private VerbService verbService;
  private Twin twin;

  FinishDialog finishDialog;
  private Button button1;
  private Button button2;
  private Button button3;
  private Button button4;
  private TextView english;
  private TextView score;
  private TextView time;
  private Integer valueScore;
  private CountDownTimer countDownTimer;
  private long s1;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    ActivityUtils.runActivity(this, R.layout.activity_game);

    verbService = VerbService.getVerbService(this);

    english = (TextView) findViewById(R.id.english);
    score = (TextView) findViewById(R.id.score);
    time = (TextView) findViewById(R.id.time);

    button1 = (Button) findViewById(R.id.ukrainian1);
    button2 = (Button) findViewById(R.id.ukrainian2);
    button3 = (Button) findViewById(R.id.ukrainian3);
    button4 = (Button) findViewById(R.id.ukrainian4);
    button1.setOnClickListener(this);
    button2.setOnClickListener(this);
    button3.setOnClickListener(this);
    button4.setOnClickListener(this);
    valueScore = 0;
    score.setText(valueScore.toString());
    newLap();
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    startTimer();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);
    outState.putInt("valueScore", valueScore);
    outState.putString("english", english.getText().toString());
    outState.putString("button1", button1.getText().toString());
    outState.putString("button2", button2.getText().toString());
    outState.putString("button3", button3.getText().toString());
    outState.putString("button4", button4.getText().toString());
    outState.putLong("time", s1);
    outState.putBoolean("dialog", null != finishDialog);
    countDownTimer.cancel();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState)
  {
    super.onRestoreInstanceState(savedInstanceState);
    valueScore = savedInstanceState.getInt("valueScore");
    score.setText(valueScore.toString());
    english.setText(savedInstanceState.getString("english"));
    button1.setText(savedInstanceState.getString("button1"));
    button2.setText(savedInstanceState.getString("button2"));
    button3.setText(savedInstanceState.getString("button3"));
    button4.setText(savedInstanceState.getString("button4"));
    s1 = savedInstanceState.getLong("time");
  }

  @Override
  public void onClick(final View view)
  {
    final RelativeLayout mainScreen = (RelativeLayout) findViewById(R.id.main_screen);
    final int childrenCount = mainScreen.getChildCount();
    for (int i = 0; i < childrenCount; i++)
      mainScreen.getChildAt(i).setClickable(false);
    if (((Button) view).getText().toString().equals(twin.getUkrainian()))
    {
      view.setBackgroundResource(R.drawable.green_button);
      valueScore++;
      score.setText(valueScore.toString());
    } else
    {
      view.setBackgroundResource(R.drawable.red_button);
      if (twin.getUkrainian().equals(button1.getText()))
        button1.setBackgroundResource(R.drawable.green_button);
      if (twin.getUkrainian().equals(button2.getText()))
        button2.setBackgroundResource(R.drawable.green_button);
      if (twin.getUkrainian().equals(button3.getText()))
        button3.setBackgroundResource(R.drawable.green_button);
      if (twin.getUkrainian().equals(button4.getText()))
        button4.setBackgroundResource(R.drawable.green_button);
      valueScore--;
      score.setText(valueScore.toString());
    }
    new Handler().postDelayed(new Runnable()
    {
      @Override
      public void run()
      {
        newLap();
        for (int i = 0; i < childrenCount; i++)
          mainScreen.getChildAt(i).setClickable(true);
      }
    }, 500);
    new Handler().postDelayed(new Runnable()
    {
      @Override
      public void run()
      {
        view.setBackgroundResource(R.drawable.blue_button);
        button1.setBackgroundResource(R.drawable.blue_button);
        button2.setBackgroundResource(R.drawable.blue_button);
        button3.setBackgroundResource(R.drawable.blue_button);
        button4.setBackgroundResource(R.drawable.blue_button);
      }
    }, 500);
  }

  public void newLap()
  {
    twin = verbService.getTwin();
    english.setText(twin.getEnglish());
    List<String> ukrainians = verbService.getUkrainianWordsForChapter(twin);
    button1.setText(ukrainians.get(0));
    button2.setText(ukrainians.get(1));
    button3.setText(ukrainians.get(2));
    button4.setText(ukrainians.get(3));
  }

  @Override
  public void onBackPressed()
  {
    super.onBackPressed();
    countDownTimer.cancel();
  }

  public void startTimer()
  {
    SettingService settingService = SettingService.getSettingService(this);
    Setting setting = settingService.getSetting();
    long gameTime = 0;
    switch (setting.getTime())
    {
      case 0:
        gameTime = 12000;
        break;
      case 1:
        gameTime = 30000;
        break;
      case 2:
        gameTime = 60000;
        break;
    }
    if (s1 > 50)
      gameTime = s1;
    countDownTimer = new MyCount(gameTime, 10);
    countDownTimer.start();
  }

  public TextView getScore()
  {
    return score;
  }

  public void setValueScore(Integer valueScore)
  {
    this.valueScore = valueScore;
  }

  public class MyCount extends CountDownTimer
  {
    public MyCount(long millisInFuture, long countDownInterval)
    {
      super(millisInFuture, countDownInterval);
    }

    @Override
    public void onFinish()
    {
      finishDialog = new FinishDialog(GameActivity.this);
      finishDialog.setCanceledOnTouchOutside(false);
      finishDialog.show();
      countDownTimer.cancel();
    }

    @Override
    public void onTick(long millisUntilFinished)
    {
      s1 = millisUntilFinished;
      time.setText("" + String.format(FORMAT,
          TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
          TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
              TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)),
          TimeUnit.MILLISECONDS.toMillis(millisUntilFinished) / 100 - TimeUnit.SECONDS.toMillis(
              TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)) / 100));
    }
  }
}
