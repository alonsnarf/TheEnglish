package com.alonsnarf.theenglish.object;

public class Twin
{
  private int id;
  private String english;
  private String ukrainian;

  public Twin()
  {
  }

  public Twin(int id, String english, String ukrainian)
  {
    this.id = id;
    this.english = english;
    this.ukrainian = ukrainian;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getEnglish()
  {
    return english;
  }

  public void setEnglish(String english)
  {
    this.english = english;
  }

  public String getUkrainian()
  {
    return ukrainian;
  }

  public void setUkrainian(String ukrainian)
  {
    this.ukrainian = ukrainian;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Twin twin = (Twin) o;

    return id == twin.id && english.equals(twin.english) && ukrainian.equals(twin.ukrainian);
  }

  @Override
  public int hashCode()
  {
    int result = english.hashCode();
    result = 31 * result + ukrainian.hashCode();
    result = 31 * result + id;
    return result;
  }
}
