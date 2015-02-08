package com.alonsnarf.theenglish.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.alonsnarf.theenglish.object.Setting;

public class SettingDao
{

  private static final SettingDao settingDao = new SettingDao();
  private SQLiteDatabase database;
  private static DbHelper dbHelper;

  private String[] allColumnsSetting =
      {
          SettingTable.ID,
          SettingTable.EMAIL,
          SettingTable.MAIN_WORD,
          SettingTable.TIME,
      };

  private SettingDao()
  {
  }

  public void openForWrite() throws SQLException
  {
    database = dbHelper.getWritableDatabase();
  }

  public void openForRead() throws SQLException
  {
    database = dbHelper.getReadableDatabase();
  }

  private Setting cursorToSetting(Cursor cursor)
  {
    Setting setting = new Setting();
    setting.setId(cursor.getInt(0));
    setting.setEmail(cursor.getString(1));
    setting.setMainWord(cursor.getInt(2));
    setting.setTime(cursor.getInt(3));

    return setting;
  }

  public Setting getSetting()
  {
    Setting setting = new Setting();
    openForRead();
    //TODO: delete hardcore
    Cursor cursor = database.query(SettingTable.TABLE_SETTING, allColumnsSetting, "_id=" + 1, null, null, null, null);
    if (cursor.moveToFirst())
    {
      setting = cursorToSetting(cursor);
    }
    cursor.close();
    close();
    return setting;
  }

  public void close()
  {
    dbHelper.close();
  }

  public static SettingDao getSettingDao(Context context)
  {
    dbHelper = new DbHelper(context);
    return settingDao;
  }

  public void updateSetting(Setting setting)
  {
    ContentValues dataToInsert = new ContentValues();
    dataToInsert.put("email", setting.getEmail());
    dataToInsert.put("main_word", setting.getMainWord());
    dataToInsert.put("time", setting.getTime());
    String where = "_id=?";
    String[] whereArgs = new String[] {String.valueOf(1)};
    try{
      openForWrite();
      database.update(SettingTable.TABLE_SETTING, dataToInsert, where, whereArgs);
      close();
    }
    catch (Exception e){
      String error =  e.getMessage().toString();
      close();
    }
  }
}
