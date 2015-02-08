package com.alonsnarf.theenglish.object;

import java.util.HashSet;
import java.util.Set;

public class Verb
{
  private String english = "";
  private Set<String> ukrainianList = new HashSet<String>();

  public Verb()
  {
  }

  public Verb(String english, Set<String> ukrainianList)
  {
    this.english = english;
    this.ukrainianList = ukrainianList;
  }

  public String getEnglish()
  {
    return english;
  }

  public void setEnglish(String english)
  {
    this.english = english;
  }

  public Set<String> getUkrainianList()
  {
    return ukrainianList;
  }

  public void setUkrainianList(Set<String> ukrainianList)
  {
    this.ukrainianList = ukrainianList;
  }

  public void addUkrainian(String ukrainian)
  {
    getUkrainianList().add(ukrainian);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Verb verb = (Verb) o;

    return english.equals(verb.english) && ukrainianList.equals(verb.ukrainianList);
  }

  @Override
  public int hashCode()
  {
    int result = english.hashCode();
    result = 31 * result + ukrainianList.hashCode();
    return result;
  }
}
