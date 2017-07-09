package org.zackratos.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/17.
 */

public class Province extends Place {


    @SerializedName("id")
    private int code;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
