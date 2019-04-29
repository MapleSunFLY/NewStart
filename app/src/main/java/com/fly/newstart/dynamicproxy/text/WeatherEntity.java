package com.fly.newstart.dynamicproxy.text;

import java.util.List;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.newstart.dynamicproxy.text
 * 作    者 : FLY
 * 创建时间 : 2019/4/28
 * 描述: 天气
 */
public class WeatherEntity {

    /**
     * city : 温州
     * realtime : {"temperature":"24","humidity":"68","info":"多云","wid":"01","direct":"东南风","power":"1级","aqi":"48"}
     * future : [{"date":"2019-04-28","temperature":"16/23℃","weather":"小雨转多云","wid":{"day":"07","night":"01"},"direct":"持续无风向"},{"date":"2019-04-29","temperature":"20/29℃","weather":"阴转小雨","wid":{"day":"02","night":"07"},"direct":"东北风转持续无风向"},{"date":"2019-04-30","temperature":"17/22℃","weather":"中到大雨转中雨","wid":{"day":"22","night":"08"},"direct":"持续无风向"},{"date":"2019-05-01","temperature":"14/25℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"},{"date":"2019-05-02","temperature":"14/25℃","weather":"多云","wid":{"day":"01","night":"01"},"direct":"持续无风向"}]
     */

    private String city;
    private RealtimeBean realtime;
    private List<FutureBean> future;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RealtimeBean getRealtime() {
        return realtime;
    }

    public void setRealtime(RealtimeBean realtime) {
        this.realtime = realtime;
    }

    public List<FutureBean> getFuture() {
        return future;
    }

    public void setFuture(List<FutureBean> future) {
        this.future = future;
    }

    public static class RealtimeBean {
        /**
         * temperature : 24
         * humidity : 68
         * info : 多云
         * wid : 01
         * direct : 东南风
         * power : 1级
         * aqi : 48
         */

        private String temperature;
        private String humidity;
        private String info;
        private String wid;
        private String direct;
        private String power;
        private String aqi;

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getWid() {
            return wid;
        }

        public void setWid(String wid) {
            this.wid = wid;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }
    }

    public static class FutureBean {
        /**
         * date : 2019-04-28
         * temperature : 16/23℃
         * weather : 小雨转多云
         * wid : {"day":"07","night":"01"}
         * direct : 持续无风向
         */

        private String date;
        private String temperature;
        private String weather;
        private WidBean wid;
        private String direct;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public WidBean getWid() {
            return wid;
        }

        public void setWid(WidBean wid) {
            this.wid = wid;
        }

        public String getDirect() {
            return direct;
        }

        public void setDirect(String direct) {
            this.direct = direct;
        }

        public static class WidBean {
            /**
             * day : 07
             * night : 01
             */

            private String day;
            private String night;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getNight() {
                return night;
            }

            public void setNight(String night) {
                this.night = night;
            }
        }
    }
}
