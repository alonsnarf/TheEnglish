package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.alonsnarf.theenglish.R;
import com.alonsnarf.theenglish.object.Setting;
import com.alonsnarf.theenglish.service.SettingService;

public class SettingActivity extends Activity
{
  private TextView email;
  private Spinner mainWord;
  private Spinner time;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    ActivityUtils.runActivity(this, R.layout.activity_setting);
    email = (TextView) findViewById(R.id.account_email);
//    mainWord = (Spinner) findViewById(R.id.spinner_main_word);
    time = (Spinner) findViewById(R.id.spinner_time);
  }

  @Override
  protected void onStart()
  {
    super.onStart();
    SettingService settingService = SettingService.getSettingService(this);
    Setting setting = settingService.getSetting();
    email.setText(setting.getEmail());
//    mainWord.setSelection(setting.getMainWord());
    time.setSelection(setting.getTime());
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    SettingService settingService = SettingService.getSettingService(this);
    Setting setting = new Setting();
    setting.setEmail(email.getText().toString());
//    setting.setMainWord(mainWord.getSelectedItemPosition());
    setting.setTime(time.getSelectedItemPosition());
    settingService.updateSetting(setting);
  }
}
