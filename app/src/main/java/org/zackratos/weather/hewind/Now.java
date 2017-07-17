package org.zackratos.weather.hewind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Now implements Parcelable {

    /**
     * cond : {"code":"104","txt":"阴"}
     * fl : 30
     * hum : 87
     * pcpn : 0
     * pres : 1007
     * tmp : 25
     * vis : 7
     * wind : {"deg":"190","dir":"南风","sc":"3-4","spd":"13"}
     */

    private Cond cond;
    private String fl;
    private String hum;
    private String pcpn;
    private String pres;
    private String tmp;
    private String vis;
    private Wind wind;

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

/*    public static class Cond {
        *//**
         * code : 104
         * txt : 阴
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
         * deg : 190
         * dir : 南风
         * sc : 3-4
         * spd : 13
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
        dest.writeString(this.tmp);
    }


    protected Now(Parcel in) {
        this.cond = in.readParcelable(Cond.class.getClassLoader());
        this.tmp = in.readString();
    }

    public static final Parcelable.Creator<Now> CREATOR = new Parcelable.Creator<Now>() {
        @Override
        public Now createFromParcel(Parcel source) {
            return new Now(source);
        }

        @Override
        public Now[] newArray(int size) {
            return new Now[size];
        }
    };
}
