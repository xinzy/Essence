package com.xinzy.essence.model;

import com.xinzy.essence.util.Macro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Xinzy on 2017-01-19.
 */
public class DayType
{
    private boolean error;
    private List<String> category;
    private Map<String, List<Essence>> results;

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

    public Map<String, List<Essence>> getResults()
    {
        return results;
    }

    public void setResults(Map<String, List<Essence>> results)
    {
        this.results = results;
    }

    public List<Object> getItems()
    {
        List<Object> items = new ArrayList<>();
        if (! error && results !=null)
        {
            Set<String> keys = results.keySet();
            for (String key : keys)
            {
                items.add(key);

                List<Essence> essences = results.get(key);
                if (essences != null && essences.size() > 0)
                {
                    items.addAll(essences);
                }
            }
        }

        return items;
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
