package com.alonsnarf.theenglish.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

import com.alonsnarf.theenglish.object.Twin;

public class VerbDao
{
  private static final VerbDao verbDao = new VerbDao();
  private SQLiteDatabase database;
  private static DbHelper dbHelper;
  private String[] allColumnsTwin =
      {
          TwinTable.ID,
          TwinTable.ENGLISH,
          TwinTable.UKRAINIAN
      };

  private VerbDao()
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

  public void close()
  {
    dbHelper.close();
  }

  public Set<Twin> getAllTwins()
  {
    Set<Twin> twinList = new HashSet<Twin>();
    openForRead();
    Cursor cursor = database.query(TwinTable.TABLE_TWINS, allColumnsTwin, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
      Twin twin = cursorToVerb(cursor);
      twinList.add(twin);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return twinList;
  }

  public Twin getTwinById(int id)
  {
    Twin twin = new Twin();
    openForRead();
    Cursor cursor = database.query(TwinTable.TABLE_TWINS, allColumnsTwin, "_id=" + id, null, null, null, null);
    if (cursor.moveToFirst())
    {
      twin = cursorToVerb(cursor);
    }
    cursor.close();
    close();
    return twin;
  }

  private Twin cursorToVerb(Cursor cursor)
  {
    Twin twin = new Twin();
    twin.setId(cursor.getInt(0));
    twin.setEnglish(cursor.getString(1));
    twin.setUkrainian(cursor.getString(2));

    return twin;
  }

  public static VerbDao getVerbDao(Context context)
  {
    dbHelper = new DbHelper(context);
    return verbDao;
  }
}
