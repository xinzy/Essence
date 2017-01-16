package com.xinzy.essence.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by xinzy on 17/1/16.
 */
public class Essence
{
    @SerializedName("_id")
    private String id;
    private Date createdAt;
    private Date publishedAt;
    private String desc;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getPublishedAt()
    {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean isUsed()
    {
        return used;
    }

    public void setUsed(boolean used)
    {
        this.used = used;
    }

    public String getWho()
    {
        return who;
    }

    public void setWho(String who)
    {
        this.who = who;
    }

    @Override
    public String toString()
    {
        return "Essence{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", publishedAt=" + publishedAt +
                ", desc='" + desc + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }
}
