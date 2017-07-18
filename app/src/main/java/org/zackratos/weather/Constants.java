package org.zackratos.weather;

/**
 * Created by Administrator on 2017/6/17.
 */

public class Constants {



    public static final class Http {


        public static final String HE_WIND_URL = "https://free-api.heweather.com/v5/";

        public static final String HE_WIND_ICON = "https://cdn.heweather.com/cond_icon/";

    }


    public static final class AddPlace {
        public static final String PROVINCE_ID = "province_id";
        public static final String CITY_ID = "city_id";
        public static final String COUNTY_ID = "county_id";
        public static final String WEATHER_ID = "weather_id";
        public static final String EXTRA_BASIC = "basic";
        public static final int LOCATE_RESULT = 1;
        public static final int SELECT_RESULT = 2;
    }


    public static final class Drawer {
        public static final int REQUEST_CODE = 1;
    }



}
