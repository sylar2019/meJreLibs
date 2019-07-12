package me.util.gps.amap.convertor;


import me.util.gps.AbstractGpsCorrecter;
import me.util.gps.common.BasicPosition;

/**
 * 高德偏转
 *
 * @author sylar
 */
public class AmapGpsCorrecter extends AbstractGpsCorrecter {
    final static double PI = 3.14159265358979324;
    final static double A = 6378245.0;
    final static double EE = 0.00669342162296594323;

    /**
     * wgs84转高德坐标系
     *
     * @param basicPosition
     */
    public static <T extends BasicPosition> void wgs2amap(T basicPosition) {
        double x = basicPosition.getLongitude();
        double y = basicPosition.getLatitude();
        double[] result = wgs2amap(x, y);
        basicPosition.setLongitudeDone(result[0]);
        basicPosition.setLatitudeDone(result[1]);
        basicPosition.setDone(true);
    }

    public static double[] wgs2amap(double wgLon, double wgLat) {
        double[] result = new double[2];
        if (outOfChina(wgLat, wgLon)) {
            result[0] = wgLon;
            result[1] = wgLat;
        } else {
            double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
            double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
            double radLat = wgLat / 180.0 * PI;
            double magic = Math.sin(radLat);
            magic = 1 - EE * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
            dLon = (dLon * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
            result[0] = wgLon + dLon;
            result[1] = wgLat + dLat;
        }
        return result;
    }

    private static boolean outOfChina(double lat, double lon) {
        double lonMin = 72.004;
        double lonMax = 137.8347;
        if (lon < lonMin || lon > lonMax) {
            return true;
        }

        double latMin = 0.8293;
        double latMax = 55.8271;
        if (lat < latMin || lat > latMax) {
            return true;
        }
        return false;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }
}
