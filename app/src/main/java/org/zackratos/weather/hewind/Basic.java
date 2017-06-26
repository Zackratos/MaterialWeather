package org.zackratos.weather.hewind;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Basic {


    /**
     * city : 合肥
     * cnty : 中国
     * id : CN101220101
     * lat : 31.86119080
     * lon : 117.28304291
     * update : {"loc":"2017-06-26 22:50","utc":"2017-06-26 14:50"}
     */

    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private Update update;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public static class Update {
        /**
         * loc : 2017-06-26 22:50
         * utc : 2017-06-26 14:50
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }
}
