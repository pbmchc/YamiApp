package com.example.piotrek.yami.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;

/**
 * Created by Piotrek on 2015-01-18.
 */
public class GlobalData implements Parcelable
{
    private int id;
    private String sessionId;

    public GlobalData()
    {
        super();
    }

    public GlobalData(Parcel parcel)
    {
        this();
        this.setId(parcel.readInt());
        this.setSessionId(parcel.readString());

    }
    public static final Creator<GlobalData> CREATOR = new Creator<GlobalData>()
    {
        @Override
        public GlobalData createFromParcel(Parcel source) {
            return new GlobalData(source);
        }

        @Override
        public GlobalData[] newArray(int size) {
            return new GlobalData[size];
        }
    };

    public void setId(int id)
    {
        this.id = id;
    }
    public void setSessionId(String sessionId)
    {
        this.sessionId = sessionId;
    }
    public int getId()
    {
        return id;
    }
    public String getSessionId()
    {
        return sessionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getSessionId());

    }
}
