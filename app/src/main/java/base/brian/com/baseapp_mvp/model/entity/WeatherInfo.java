package base.brian.com.baseapp_mvp.model.entity;

/**
 * Created by brian on 16/6/5.
 */
public class WeatherInfo {
    String city;
    String cityID;
    String temp;
    String WD;
    String WS;
    String SD;
    String WSE;
    String time;
    String isRadar;
    String Radar;
    String njd;
    String qy;
    String rain;

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "city='" + city + '\'' +
                ", cityID='" + cityID + '\'' +
                ", temp='" + temp + '\'' +
                ", WD='" + WD + '\'' +
                ", WS='" + WS + '\'' +
                ", SD='" + SD + '\'' +
                ", WSE='" + WSE + '\'' +
                ", time='" + time + '\'' +
                ", isRadar='" + isRadar + '\'' +
                ", Radar='" + Radar + '\'' +
                ", njd='" + njd + '\'' +
                ", qy='" + qy + '\'' +
                ", rain='" + rain + '\'' +
                '}';
    }
}
