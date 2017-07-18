package org.zackratos.weather.hewind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Wind implements Parcelable {

    /**
     * deg : 190
     * dir : 南风
     * sc : 3-4
     * spd : 13
     */

    private String deg;
    private String dir;
    private String sc;
    private String spd;

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deg);
        dest.writeString(this.dir);
        dest.writeString(this.sc);
        dest.writeString(this.spd);
    }


    protected Wind(Parcel in) {
        this.deg = in.readString();
        this.dir = in.readString();
        this.sc = in.readString();
        this.spd = in.readString();
    }

    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel source) {
            return new Wind(source);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };
}
