package com.asstnavi.generic.Enum;

public enum TimeSelect
{
    Day("day"),
    Week("week"),
    Month("month");

    private final String operator;
    private  TimeSelect(String time)
    {this.operator=time;

    }

    public String getOperator()
    {
        return operator;
    }
}
