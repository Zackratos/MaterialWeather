package org.zack.weather;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/21.
 */

public class Weather extends DataSupport {

    private int id;

    private String countyName;

    private String weatherId;


    private Weather(Builder builder) {
        this.id = builder.id;
        this.countyName = builder.countyName;
        this.weatherId = builder.weatherId;
    }


    public String getCountyName() {
        return countyName;
    }

    public static class Builder {

        private int id;

        private String countyName;

        private String weatherId;


        public Builder countyName(String countyName) {
            this.countyName = countyName;
            return this;
        }

        public Builder weatherId(String weatherId) {
            this.weatherId = weatherId;
            return this;
        }


        public Weather build() {
            return new Weather(this);
        }


    }


}
