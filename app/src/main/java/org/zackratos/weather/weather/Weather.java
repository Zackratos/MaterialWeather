package org.zackratos.weather.weather;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/21.
 */

public class Weather extends DataSupport {

    private int id;

    private int index;


    private String countyName;

    private String weatherId;

    private Weather(Builder builder) {

        this.countyName = builder.countyName;
        this.weatherId = builder.weatherId;
        this.index = builder.index;
//        this.checked = builder.checked;
    }


    public int getId() {
        return id;
    }


    /*    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }*/

    public String getCountyName() {
        return countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public static class Builder {


        private String countyName;

        private String weatherId;

        private int index;

        public Builder countyName(String countyName) {
            this.countyName = countyName;
            return this;
        }

        public Builder weatherId(String weatherId) {
            this.weatherId = weatherId;
            return this;
        }


        public Builder index(int index) {
            this.index = index;
            return this;
        }


        public Weather build() {
            return new Weather(this);
        }

    }





/*    @Override
    public boolean equals(Object obj) {

        return id == ((Weather) obj).id;

    }*/
}
