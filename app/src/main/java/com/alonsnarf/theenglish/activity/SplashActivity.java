package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alonsnarf.theenglish.R;
import com.alonsnarf.theenglish.service.VerbService;

public class SplashActivity extends Activity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    ActivityUtils.runActivity(this, R.layout.activity_splash);
  }

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
          Intent intent = new Intent(SplashActivity.this, MainActivity.class);
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
