package com.alonsnarf.theenglish.dao;

import android.database.sqlite.SQLiteDatabase;

public class TwinTable
{
  public static final String TABLE_TWINS = "word";
  public static final String ID = "_id";
  public static final String ENGLISH = "english";
  public static final String UKRAINIAN = "ukrainian";

  private static final String DATABASE_CREATE = "create table "
      + TABLE_TWINS
      + "("
      + ID + " integer primary key autoincrement, "
      + ENGLISH + " text not null, "
      + UKRAINIAN + " text not null"
      + ");";

  public static void onCreate(SQLiteDatabase database)
  {
    database.execSQL(DATABASE_CREATE);
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
  {
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TWINS);
    onCreate(database);
  }
}
