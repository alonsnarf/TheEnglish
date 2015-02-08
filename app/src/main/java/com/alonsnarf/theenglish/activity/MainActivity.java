package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alonsnarf.theenglish.R;

public class MainActivity extends Activity implements View.OnClickListener
{

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    ActivityUtils.runActivity(this, R.layout.activity_main);
    Button beginButton = (Button) findViewById(R.id.begin);
    Button settingButton = (Button) findViewById(R.id.setting);
    beginButton.setOnClickListener(this);
    settingButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    switch (v.getId())
    {
      case R.id.begin:
        startActivity(new Intent(this, GameActivity.class));
        break;
      case R.id.setting:
        startActivity(new Intent(this, SettingActivity.class));
        break;
    }
  }
}
