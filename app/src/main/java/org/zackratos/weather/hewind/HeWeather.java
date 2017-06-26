package org.zackratos.weather.hewind;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26.
 */

public class HeWeather {

    private Aqi aqi;

    private Basic basic;

    @SerializedName("daily_forecast")
    private List<Daily> dailies;

    @SerializedName("daily_forecast")
    private List<Hourly> hourlies;

    private Now now;

    private String status;

    private Suggestion suggestion;





    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(List<Daily> dailies) {
        this.dailies = dailies;
    }

    public List<Hourly> getHourlies() {
        return hourlies;
    }

    public void setHourlies(List<Hourly> hourlies) {
        this.hourlies = hourlies;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
