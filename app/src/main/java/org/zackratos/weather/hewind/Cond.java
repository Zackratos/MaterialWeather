package org.zackratos.weather.hewind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Cond implements Parcelable {

    /**
     * code : 104
     * txt : 阴
     */

    private String code;
    private String txt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.txt);
    }

    public Cond() {
    }

    protected Cond(Parcel in) {
        this.code = in.readString();
        this.txt = in.readString();
    }

    public static final Parcelable.Creator<Cond> CREATOR = new Parcelable.Creator<Cond>() {
        @Override
        public Cond createFromParcel(Parcel source) {
            return new Cond(source);
        }

        @Override
        public Cond[] newArray(int size) {
            return new Cond[size];
        }
    };
}
