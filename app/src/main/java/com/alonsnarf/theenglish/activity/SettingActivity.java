package com.alonsnarf.theenglish.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.alonsnarf.theenglish.R;
import com.alonsnarf.theenglish.object.Setting;
import com.alonsnarf.theenglish.service.SettingService;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import static android.view.Window.FEATURE_INDETERMINATE_PROGRESS;
import static android.view.Window.FEATURE_NO_TITLE;

@Fullscreen
@EActivity(R.layout.activity_setting)
@WindowFeature({FEATURE_NO_TITLE, FEATURE_INDETERMINATE_PROGRESS })
public class SettingActivity extends Activity
{
  @ViewById
  TextView accountEmail;

//  @ViewById
//  Spinner spinnerMainWord;

  @ViewById
  Spinner spinnerTime;

  @Override
  protected void onStart()
  {
    super.onStart();
    SettingService settingService = SettingService.getSettingService(this);
    Setting setting = settingService.getSetting();
    accountEmail.setText(setting.getEmail());
//    mainWord.setSelection(setting.getMainWord());
    spinnerTime.setSelection(setting.getTime());
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    SettingService settingService = SettingService.getSettingService(this);
    Setting setting = new Setting();
    setting.setEmail(accountEmail.getText().toString());
//    setting.setMainWord(mainWord.getSelectedItemPosition());
    setting.setTime(spinnerTime.getSelectedItemPosition());
    settingService.updateSetting(setting);
  }
}
