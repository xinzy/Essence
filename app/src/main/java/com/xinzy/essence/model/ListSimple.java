package com.xinzy.essence.model;

import com.xinzy.essence.util.Macro;

import java.util.List;

/**
 * Created by xinzy on 17/1/16.
 */
public class ListSimple<T>
{
    private boolean error;
    private List<T> results;

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public List<T> getResults()
    {
        return results;
    }

    public void setResults(List<T> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        if (Macro.DEBUG)
        {
            return "ListSimple{" + "error=" + error + ", results=" + results + '}';
        }
        return "";
    }
}
