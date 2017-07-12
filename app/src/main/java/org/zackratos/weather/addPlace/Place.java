package org.zackratos.weather.addPlace;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/19.
 */

public abstract class Place extends DataSupport {

    @SerializedName("sid")
    protected int id;

    protected String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
