package com.alonsnarf.theenglish.service;

import android.content.Context;

import com.alonsnarf.theenglish.dao.SettingDao;
import com.alonsnarf.theenglish.object.Setting;

public class SettingService
{
  private static final SettingService settingService = new SettingService();
  private static SettingDao settingDao;
  private SettingService()
  {
  }

  public static SettingService getSettingService(Context context)
  {
    settingDao = SettingDao.getSettingDao(context);
    return settingService;
  }

  public Setting getSetting()
  {
    return settingDao.getSetting();
  }

  public void updateSetting(Setting setting)
  {
    settingDao.updateSetting(setting);
  }


}
