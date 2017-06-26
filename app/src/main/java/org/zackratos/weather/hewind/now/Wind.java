package org.zackratos.weather.hewind.now;

/**
 * Created by Administrator on 2017/6/25.
 */

public class Wind {


    /**
     * deg : 40
     * dir : 东北风
     * sc : 4-5
     * spd : 24
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
}
