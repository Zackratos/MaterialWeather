package org.zackratos.weather.hewind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Hourly implements Parcelable {


    /**
     * cond : {"code":"100","txt":"晴"}
     * date : 2016-08-30 12:00
     * hum : 47
     * pop : 0
     * pres : 1006
     * tmp : 29
     * wind : {"deg":"335","dir":"西北风","sc":"4-5","spd":"36"}
     */

    private Cond cond;
    private String date;
    private String hum;
    private String pop;
    private String pres;
    private String tmp;
    private Wind wind;

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

/*    public static class Cond {
        *//**
         * code : 100
         * txt : 晴
         *//*

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
    }*/

/*    public static class Wind {
        *//**
         * deg : 335
         * dir : 西北风
         * sc : 4-5
         * spd : 36
         *//*

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
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.cond, flags);
        dest.writeString(this.date);
        dest.writeString(this.hum);
        dest.writeString(this.pop);
        dest.writeString(this.pres);
        dest.writeString(this.tmp);
        dest.writeParcelable(this.wind, flags);
    }


    protected Hourly(Parcel in) {
        this.cond = in.readParcelable(Cond.class.getClassLoader());
        this.date = in.readString();
        this.hum = in.readString();
        this.pop = in.readString();
        this.pres = in.readString();
        this.tmp = in.readString();
        this.wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Parcelable.Creator<Hourly> CREATOR = new Parcelable.Creator<Hourly>() {
        @Override
        public Hourly createFromParcel(Parcel source) {
            return new Hourly(source);
        }

        @Override
        public Hourly[] newArray(int size) {
            return new Hourly[size];
        }
    };
}
