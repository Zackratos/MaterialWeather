package org.zackratos.weather.hewind;

/**
 * Created by Administrator on 2017/6/26.
 */

public class Suggestion {

    /**
     * comf : {"brf":"较不舒适","txt":"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。"}
     * cw : {"brf":"较适宜","txt":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"}
     * drsg : {"brf":"炎热","txt":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"}
     * flu : {"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"}
     * sport : {"brf":"较不宜","txt":"阴天，且天气炎热，建议停止户外运动，选择在室内进行低强度运动。"}
     * trav : {"brf":"较适宜","txt":"天气较好，天气较热，总体来说还是好天气，还是较适宜旅游的，您仍可陶醉于大自然的美丽风光中。"}
     * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
     */

    private SuggestionBean comf;
    private SuggestionBean cw;
    private SuggestionBean drsg;
    private SuggestionBean flu;
    private SuggestionBean sport;
    private SuggestionBean trav;
    private SuggestionBean uv;

    public SuggestionBean getComf() {
        return comf;
    }

    public void setComf(SuggestionBean comf) {
        this.comf = comf;
    }

    public SuggestionBean getCw() {
        return cw;
    }

    public void setCw(SuggestionBean cw) {
        this.cw = cw;
    }

    public SuggestionBean getDrsg() {
        return drsg;
    }

    public void setDrsg(SuggestionBean drsg) {
        this.drsg = drsg;
    }

    public SuggestionBean getFlu() {
        return flu;
    }

    public void setFlu(SuggestionBean flu) {
        this.flu = flu;
    }

    public SuggestionBean getSport() {
        return sport;
    }

    public void setSport(SuggestionBean sport) {
        this.sport = sport;
    }

    public SuggestionBean getTrav() {
        return trav;
    }

    public void setTrav(SuggestionBean trav) {
        this.trav = trav;
    }

    public SuggestionBean getUv() {
        return uv;
    }

    public void setUv(SuggestionBean uv) {
        this.uv = uv;
    }


    /*    public Comf getComf() {
        return comf;
    }

    public void setComf(Comf comf) {
        this.comf = comf;
    }

    public Cw getCw() {
        return cw;
    }

    public void setCw(Cw cw) {
        this.cw = cw;
    }

    public Drsg getDrsg() {
        return drsg;
    }

    public void setDrsg(Drsg drsg) {
        this.drsg = drsg;
    }

    public Flu getFlu() {
        return flu;
    }

    public void setFlu(Flu flu) {
        this.flu = flu;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Trav getTrav() {
        return trav;
    }

    public void setTrav(Trav trav) {
        this.trav = trav;
    }

    public Uv getUv() {
        return uv;
    }

    public void setUv(Uv uv) {
        this.uv = uv;
    }

    public static class Comf extends SuggestionBean {
    }

    public static class Cw extends SuggestionBean {
    }

    public static class Drsg extends SuggestionBean {
    }

    public static class Flu extends SuggestionBean {
    }

    public static class Sport extends SuggestionBean {
    }

    public static class Trav extends SuggestionBean {
    }

    public static class Uv extends SuggestionBean {
    }*/





    public static class SuggestionBean {
        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public void setBrf(String brf) {
            this.brf = brf;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }



}
