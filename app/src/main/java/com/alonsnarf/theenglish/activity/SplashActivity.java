package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.content.Intent;

import com.alonsnarf.theenglish.R;
import com.alonsnarf.theenglish.service.VerbService;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;

import static android.view.Window.FEATURE_INDETERMINATE_PROGRESS;
import static android.view.Window.FEATURE_NO_TITLE;

@Fullscreen
@EActivity(R.layout.activity_splash)
@WindowFeature({FEATURE_NO_TITLE, FEATURE_INDETERMINATE_PROGRESS })
public class SplashActivity extends Activity
{

  @Override
  protected void onStart()
  {
    super.onStart();
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(1000);
          VerbService.getVerbService(SplashActivity.this).getAllTwins();
          Intent intent = new Intent(SplashActivity.this, MainActivity_.class);
          startActivity(intent);
          finish();
        } catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
