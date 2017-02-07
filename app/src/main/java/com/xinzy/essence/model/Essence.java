package com.xinzy.essence.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.xinzy.essence.util.Macro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xinzy on 17/1/16.
 */
public class Essence implements Parcelable
{
    @SerializedName(value = "_id", alternate = {"ganhuo_id"})
    private String id;
    private Date createdAt;
    private String publishedAt;
    @SerializedName("desc")
    private String content;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private String[] images;
    private String readability;

    public Essence()
    {
    }

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
        if (createdAt != null)
        {
            return createdAt;
        }
        if (! TextUtils.isEmpty(publishedAt))
        {
            String publish = publishedAt;
            String[] temp = publish.split("\\.");
            if (temp.length == 2 && temp[1].endsWith("000"))
            {
                publish = temp[0] + '.' + temp[1].substring(0, temp[1].length() -3) + 'Z';
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            try
            {
                return sdf.parse(publish);
            } catch (ParseException e)
            {
                // do noting
            }
        }
        return new Date();
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getPublishedAt()
    {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    public String[] getImages()
    {
        return images;
    }

    public void setImages(String[] images)
    {
        this.images = images;
    }

    public boolean hasImage()
    {
        return images != null && images.length > 0;
    }

    public String getImage()
    {
        if (hasImage())
        {
            return images[0];
        }
        return "";
    }

    public String getReadability()
    {
        return readability;
    }

    public void setReadability(String readability)
    {
        this.readability = readability;
    }

    @Override
    public String toString()
    {
        if (Macro.DEBUG)
        {
            return "Essence{" +
                    "id='" + id + '\'' +
                    ", createdAt=" + createdAt +
                    ", publishedAt=" + publishedAt +
                    ", content='" + content + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
        return "";
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.id);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeString(this.publishedAt);
        dest.writeString(this.content);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
        dest.writeString(this.readability);
    }

    protected Essence(Parcel in)
    {
        this.id = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        this.publishedAt = in.readString();
        this.content = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
        this.readability = in.readString();
    }

    public static final Creator<Essence> CREATOR = new Creator<Essence>()
    {
        @Override
        public Essence createFromParcel(Parcel source)
        {
            return new Essence(source);
        }

        @Override
        public Essence[] newArray(int size)
        {
            return new Essence[size];
        }
    };
}
