package org.zackratos.weather.hewind;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Daily implements Parcelable {

    /**
     * astro : {"mr":"07:18","ms":"21:19","sr":"05:08","ss":"19:19"}
     * cond : {"code_d":"104","code_n":"101","txt_d":"阴","txt_n":"多云"}
     * date : 2017-06-26
     * hum : 68
     * pcpn : 0.0
     * pop : 2
     * pres : 1005
     * tmp : {"max":"32","min":"23"}
     * uv : 7
     * vis : 15
     * wind : {"deg":"284","dir":"西南风","sc":"微风","spd":"4"}
     */

    private Astro astro;
    private Cond cond;
    private String date;
    private String hum;
    private String pcpn;
    private String pop;
    private String pres;
    private Tmp tmp;
    private String uv;
    private String vis;
    private Wind wind;

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

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

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public Tmp getTmp() {
        return tmp;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
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

    public static class Astro implements Parcelable {
        /**
         * mr : 07:18
         * ms : 21:19
         * sr : 05:08
         * ss : 19:19
         */

        private String mr;
        private String ms;
        private String sr;
        private String ss;

        public String getMr() {
            return mr;
        }

        public void setMr(String mr) {
            this.mr = mr;
        }

        public String getMs() {
            return ms;
        }

        public void setMs(String ms) {
            this.ms = ms;
        }

        public String getSr() {
            return sr;
        }

        public void setSr(String sr) {
            this.sr = sr;
        }

        public String getSs() {
            return ss;
        }

        public void setSs(String ss) {
            this.ss = ss;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.mr);
            dest.writeString(this.ms);
            dest.writeString(this.sr);
            dest.writeString(this.ss);
        }

        public Astro() {
        }

        protected Astro(Parcel in) {
            this.mr = in.readString();
            this.ms = in.readString();
            this.sr = in.readString();
            this.ss = in.readString();
        }

        public static final Creator<Astro> CREATOR = new Creator<Astro>() {
            @Override
            public Astro createFromParcel(Parcel source) {
                return new Astro(source);
            }

            @Override
            public Astro[] newArray(int size) {
                return new Astro[size];
            }
        };
    }

    public static class Cond implements Parcelable {
        /**
         * code_d : 104
         * code_n : 101
         * txt_d : 阴
         * txt_n : 多云
         */

        private String code_d;
        private String code_n;
        private String txt_d;
        private String txt_n;

        public String getCode_d() {
            return code_d;
        }

        public void setCode_d(String code_d) {
            this.code_d = code_d;
        }

        public String getCode_n() {
            return code_n;
        }

        public void setCode_n(String code_n) {
            this.code_n = code_n;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code_d);
            dest.writeString(this.code_n);
            dest.writeString(this.txt_d);
            dest.writeString(this.txt_n);
        }


        protected Cond(Parcel in) {
            this.code_d = in.readString();
            this.code_n = in.readString();
            this.txt_d = in.readString();
            this.txt_n = in.readString();
        }

        public static final Creator<Cond> CREATOR = new Creator<Cond>() {
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

    public static class Tmp implements Parcelable {
        /**
         * max : 32
         * min : 23
         */

        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.max);
            dest.writeString(this.min);
        }


        protected Tmp(Parcel in) {
            this.max = in.readString();
            this.min = in.readString();
        }

        public static final Creator<Tmp> CREATOR = new Creator<Tmp>() {
            @Override
            public Tmp createFromParcel(Parcel source) {
                return new Tmp(source);
            }

            @Override
            public Tmp[] newArray(int size) {
                return new Tmp[size];
            }
        };
    }

/*    public static class Wind {
        *//**
         * deg : 284
         * dir : 西南风
         * sc : 微风
         * spd : 4
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
        dest.writeParcelable(this.astro, flags);
        dest.writeParcelable(this.cond, flags);
        dest.writeString(this.date);
        dest.writeString(this.hum);
        dest.writeString(this.pcpn);
        dest.writeString(this.pop);
        dest.writeString(this.pres);
        dest.writeParcelable(this.tmp, flags);
        dest.writeString(this.uv);
        dest.writeString(this.vis);
        dest.writeParcelable(this.wind, flags);
    }


    protected Daily(Parcel in) {
        this.astro = in.readParcelable(Astro.class.getClassLoader());
        this.cond = in.readParcelable(Cond.class.getClassLoader());
        this.date = in.readString();
        this.hum = in.readString();
        this.pcpn = in.readString();
        this.pop = in.readString();
        this.pres = in.readString();
        this.tmp = in.readParcelable(Tmp.class.getClassLoader());
        this.uv = in.readString();
        this.vis = in.readString();
        this.wind = in.readParcelable(Wind.class.getClassLoader());
    }

    public static final Parcelable.Creator<Daily> CREATOR = new Parcelable.Creator<Daily>() {
        @Override
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        @Override
        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
