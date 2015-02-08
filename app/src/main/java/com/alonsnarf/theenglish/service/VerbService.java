package com.alonsnarf.theenglish.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.alonsnarf.theenglish.dao.VerbDao;
import com.alonsnarf.theenglish.object.Twin;
import com.alonsnarf.theenglish.object.Verb;

public class VerbService
{
  private static final VerbService verbService = new VerbService();
  private static VerbDao verbDao;
  Set<Twin> twins;
  private int size = 0;

  private VerbService()
  {
  }

  public static VerbService getVerbService(Context context)
  {
    verbDao = VerbDao.getVerbDao(context);
    return verbService;
  }

  public Set<Verb> getAllVerbs()
  {
    Set<Verb> verbList = new HashSet<Verb>();
    if (null == twins)
      twins = getAllTwins();
    for (Twin twin : twins)
    {
      boolean isExist = false;
      for (Verb verb : verbList)
        if (verb.getEnglish().equals(twin.getEnglish()))
        {
          verb.addUkrainian(twin.getUkrainian());
          isExist = true;
        }
      if (!isExist)
      {
        Verb verb = new Verb();
        verb.setEnglish(twin.getEnglish());
        verb.addUkrainian(twin.getUkrainian());
        verbList.add(verb);
      }
    }

    return verbList;
  }

  public Twin getTwin()
  {
    if (size == 0)
      size = getAllTwins().size();
    return verbDao.getTwinById(1 + new Random().nextInt(size));
  }

  public Verb getVerbByEnglish(String english)
  {
    for (Verb verb : getAllVerbs())
      if (english.equals(verb.getEnglish()))
        return verb;

    return null;
  }

  public Set<String> getAllUkrainianWords()
  {
    Set<Twin> twinSet = getAllTwins();
    Set<String> ukrainians = new HashSet<String>();
    for (Twin twin : twinSet)
      ukrainians.add(twin.getUkrainian());
    return ukrainians;
  }

  public List<String> getUkrainianWordsForChapter(Twin correctTwin)
  {
    String english = correctTwin.getEnglish();
    List<String> ukrainians = new ArrayList<String>();
    Twin twin;
    while (ukrainians.size() < 4)
    {
      twin = getTwin();
      if (!twin.getEnglish().equals(english) && !ukrainians.contains(twin.getUkrainian()))
        ukrainians.add(twin.getUkrainian());
    }
    int rand = new Random().nextInt(ukrainians.size());
    ukrainians.set(rand, correctTwin.getUkrainian());
    return ukrainians;
  }

  public Set<Twin> getAllTwins()
  {
    return verbDao.getAllTwins();
  }
}
