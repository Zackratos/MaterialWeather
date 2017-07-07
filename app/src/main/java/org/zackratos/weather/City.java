package org.zackratos.weather;


import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/17.
 */

public class City extends Place {


    @SerializedName("id")
    private int code;

    private int provinceId;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
