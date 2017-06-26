package org.zackratos.weather.hewind.srarch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/25.
 */

public class SearchBasic implements Parcelable {
    /**
     * city : 北京
     * cnty : 中国
     * id : CN101010100
     * lat : 39.904000
     * lon : 116.391000
     * prov : 直辖市
     */

    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private String prov;

    public SearchBasic() {
    }

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

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.city);
//        dest.writeString(this.cnty);
//        dest.writeString(this.lat);
//        dest.writeString(this.lon);
//        dest.writeString(this.prov);
    }



    protected SearchBasic(Parcel in) {
        this.id = in.readString();
        this.city = in.readString();
//        this.cnty = in.readString();
//        this.lat = in.readString();
//        this.lon = in.readString();
//        this.prov = in.readString();
    }

    public static final Parcelable.Creator<SearchBasic> CREATOR = new Parcelable.Creator<SearchBasic>() {
        @Override
        public SearchBasic createFromParcel(Parcel source) {
            return new SearchBasic(source);
        }

        @Override
        public SearchBasic[] newArray(int size) {
            return new SearchBasic[size];
        }
    };
}
