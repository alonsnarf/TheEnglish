package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import com.alonsnarf.theenglish.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import static android.view.Window.FEATURE_INDETERMINATE_PROGRESS;
import static android.view.Window.FEATURE_NO_TITLE;

@Fullscreen
@EActivity(R.layout.activity_main)
@WindowFeature({FEATURE_NO_TITLE, FEATURE_INDETERMINATE_PROGRESS})
public class MainActivity extends Activity
{
  @ViewById
  Button begin;

  @ViewById
  Button setting;

  @Click
  public void begin()
  {
    startActivity(new Intent(this, GameActivity_.class));
  }

  @Click
  public void setting()
  {
    startActivity(new Intent(this, SettingActivity_.class));
  }
}
