package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class ActivityUtils
{
  public static void runActivity(Activity activity, int activityId)
  {
    activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    activity.setContentView(activityId);

  }
}
