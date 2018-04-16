package qqcoder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HKH on 2018/4/15.
 */
public class Navi {


//    Krasovsky 1940
//    a = 6378245.0, 1/f = 298.3
//    b = a * (1 - f)
//    ee = (a^2 - b^2) / a^2;

    static double pi = 3.14159265358979324;
//GPS    static double a = 6378245.0;
    static double a = 6378137.0;
    static double ee = 0.00669342162296594323;

    public String gcj2wgs(double lon, double lat) {
        Map<String, Double> localHashMap = new HashMap<String, Double>();
        double lontitude = lon - (((Double) transform(lon, lat).get("lon")).doubleValue() - lon);
        double latitude = (lat - (((Double) (transform(lon, lat)).get("lat")).doubleValue() - lat));
        localHashMap.put("lon", lontitude);
        localHashMap.put("lat", latitude);
        String wgsLatLon = lontitude + "," + latitude;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trandata = new StringSelection(latitude + "," + lontitude);
        clipboard.setContents(trandata, null);
        return wgsLatLon;
    }

    public Map<String, Double> transform(double lon, double lat) {

        HashMap<String, Double> localHashMap = new HashMap<String, Double>();
        double dLat = transformLat(lon - 105.0, lat - 35.0);
        double dLon = transformLon(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * pi;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
        double mgLat = lat + dLat;
        double mgLon = lon + dLon;
        localHashMap.put("lon", mgLon);
        localHashMap.put("lat", mgLat);
        return localHashMap;
    }

    static double transformLat(double x, double y) {
        double ret = -100 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
        return ret;
    }
    public static void main(String[] args) {
        Navi navi = new Navi();
        System.out.println(navi.gcj2wgs(108.06366, 22.57129));

    }
}
