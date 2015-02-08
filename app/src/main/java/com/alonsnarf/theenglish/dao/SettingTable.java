package com.alonsnarf.theenglish.dao;

import android.database.sqlite.SQLiteDatabase;

public class SettingTable
{
  public static final String TABLE_SETTING = "setting";
  public static final String ID = "_id";
  public static final String EMAIL = "email";
  public static final String MAIN_WORD = "main_word";
  public static final String TIME = "time";

  private static final String DATABASE_CREATE = "create table "
      + TABLE_SETTING
      + "("
      + ID + " integer primary key autoincrement, "
      + EMAIL + " text, "
      + MAIN_WORD + " text not null, "
      + TIME + " integer not null"
      + ");";

  public static void onCreate(SQLiteDatabase database)
  {
    database.execSQL(DATABASE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
  {
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTING);
    onCreate(database);
  }
}
