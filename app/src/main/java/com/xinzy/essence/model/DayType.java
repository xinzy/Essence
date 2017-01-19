package com.xinzy.essence.model;

import com.xinzy.essence.util.Macro;

import java.util.List;
import java.util.Map;

/**
 * Created by Xinzy on 2017-01-19.
 */
public class DayType
{
    private boolean error;
    private List<String> category;
    private Map<String, Essence> results;

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public List<String> getCategory()
    {
        return category;
    }

    public void setCategory(List<String> category)
    {
        this.category = category;
    }

    public Map<String, Essence> getResults()
    {
        return results;
    }

    public void setResults(Map<String, Essence> results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        if (Macro.DEBUG)
        {
            return "DayType{" + "error=" + error + ", category=" + category + ", results=" + results + '}';
        }
        return "";
    }
}
