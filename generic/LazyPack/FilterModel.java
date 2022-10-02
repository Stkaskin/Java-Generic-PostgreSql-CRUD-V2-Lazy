package com.asstnavi.generic.LazyPack;

import com.asstnavi.generic.Enum.MatchMode;

public class FilterModel
{
  private   MatchMode matchMode;
private     Object value;

    public MatchMode getMatchMode()
    {
        return matchMode;
    }

    public void setMatchMode(MatchMode matchMode)
    {
        this.matchMode = matchMode;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }
}
