package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alonsnarf.theenglish.R;
import com.alonsnarf.theenglish.object.Setting;
import com.alonsnarf.theenglish.object.Twin;
import com.alonsnarf.theenglish.service.SettingService;
import com.alonsnarf.theenglish.service.VerbService;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.view.Window.FEATURE_INDETERMINATE_PROGRESS;
import static android.view.Window.FEATURE_NO_TITLE;

@Fullscreen
@EActivity(R.layout.activity_game)
@WindowFeature({FEATURE_NO_TITLE, FEATURE_INDETERMINATE_PROGRESS})
public class GameActivity extends Activity
{
  private static final String FORMAT = "%02d:%02d:%01d";
  private VerbService verbService;
  private Twin twin;
  FinishDialog finishDialog;
  CountDownTimer countDownTimer;

  @InstanceState
  long s1;
  @InstanceState
  Integer valueScore;

  @ViewById
  RelativeLayout mainScreen;
  @ViewById
  Button ukrainian1;
  @ViewById
  Button ukrainian2;
  @ViewById
  Button ukrainian3;
  @ViewById
  Button ukrainian4;
  @ViewById
  TextView english;
  @ViewById
  TextView score;
  @ViewById
  TextView time;

  @Override
  protected void onStart()
  {
    super.onStart();
    verbService = VerbService.getVerbService(this);
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
    outState.putString("english", english.getText().toString());
    outState.putString("ukrainian1", ukrainian1.getText().toString());
    outState.putString("ukrainian2", ukrainian2.getText().toString());
    outState.putString("ukrainian3", ukrainian3.getText().toString());
    outState.putString("ukrainian4", ukrainian4.getText().toString());
    outState.putLong("time", s1);
    outState.putBoolean("dialog", null != finishDialog);
    countDownTimer.cancel();
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState)
  {
    super.onRestoreInstanceState(savedInstanceState);
    score.setText(valueScore.toString());
    english.setText(savedInstanceState.getString("english"));
    ukrainian1.setText(savedInstanceState.getString("ukrainian1"));
    ukrainian2.setText(savedInstanceState.getString("ukrainian2"));
    ukrainian3.setText(savedInstanceState.getString("ukrainian3"));
    ukrainian4.setText(savedInstanceState.getString("ukrainian4"));
    s1 = savedInstanceState.getLong("time");
  }

  @Click
  public void english()
  {
    int resId = getResources().getIdentifier("raw/" + english.getText() + "_", null, this.getPackageName());
    MediaPlayer.create(this, resId).start();
  }

  @Click
  public void ukrainian1()
  {
    clickOnButton(ukrainian1);
  }

  @Click
  public void ukrainian2()
  {
    clickOnButton(ukrainian2);
  }

  @Click
  public void ukrainian3()
  {
    clickOnButton(ukrainian3);
  }

  @Click
  public void ukrainian4()
  {
    clickOnButton(ukrainian4);
  }

  private void clickOnButton(Button ukrainian)
  {
    final int childrenCount = mainScreen.getChildCount();
    for (int i = 0; i < childrenCount; i++)
      mainScreen.getChildAt(i).setClickable(false);
    if (ukrainian.getText().toString().equals(twin.getUkrainian()))
    {
      ukrainian.setBackgroundResource(R.drawable.green_button);
      valueScore++;
      score.setText(valueScore.toString());
    } else
    {
      ukrainian.setBackgroundResource(R.drawable.red_button);
      greenBox();
    }
    handler(childrenCount, ukrainian);
  }

  private void greenBox()
  {
    if (twin.getUkrainian().equals(ukrainian1.getText()))
      ukrainian1.setBackgroundResource(R.drawable.green_button);
    if (twin.getUkrainian().equals(ukrainian2.getText()))
      ukrainian2.setBackgroundResource(R.drawable.green_button);
    if (twin.getUkrainian().equals(ukrainian3.getText()))
      ukrainian3.setBackgroundResource(R.drawable.green_button);
    if (twin.getUkrainian().equals(ukrainian4.getText()))
      ukrainian4.setBackgroundResource(R.drawable.green_button);
    valueScore--;
    score.setText(valueScore.toString());
  }

  void handler(final int childrenCount, final Button ukrainian)
  {
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
        ukrainian1.setBackgroundResource(R.drawable.blue_button);
        ukrainian2.setBackgroundResource(R.drawable.blue_button);
        ukrainian3.setBackgroundResource(R.drawable.blue_button);
        ukrainian4.setBackgroundResource(R.drawable.blue_button);
      }
    }, 500);
  }

  public void newLap()
  {
    twin = verbService.getTwin();
    english.setText(twin.getEnglish());
    List<String> ukrainians = verbService.getUkrainianWordsForChapter(twin);
    ukrainian1.setText(ukrainians.get(0));
    ukrainian2.setText(ukrainians.get(1));
    ukrainian3.setText(ukrainians.get(2));
    ukrainian4.setText(ukrainians.get(3));
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
