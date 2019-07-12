package me.util.gps.util;

import me.util.gps.enums.CoorType;
import me.util.gps.enums.GpsCoorType;

/**
 * @author :  luhao
 * @FileName :  GpsUtil
 * @CreateDate :  2016/8/25
 * @Description :
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) www.XXXXX.com   All Rights Reserved
 * *******************************************************************************************
 */
public class GpsUtil {
    private static final double X_PI = Math.PI * 3000.0 / 180.0;

    /**
     * 经纬度转换
     *
     * @param lon  经度
     * @param lat  纬度
     * @param from 源坐标类型
     * @param to   目标坐标类型
     * @return 转换后的经纬度
     */
    public static double[] gpsConvertor(double lon, double lat, CoorType from, CoorType to) {
        if (CoorType.GCJ02.equals(from)) {
            if (CoorType.BD09LL.equals(to)) {
                return gcj02ToBd09(lon, lat);
            } else if (CoorType.WGS84.equals(to)) {
                return gcj02ToWgs84(lon, lat);
            }
        } else if (CoorType.BD09LL.equals(from)) {
            if (CoorType.WGS84.equals(to)) {
                double[] bd09ToGcj02 = bd09ToGcj02(lon, lat);
                return gcj02ToWgs84(bd09ToGcj02[0], bd09ToGcj02[1]);
            } else if (CoorType.GCJ02.equals(to)) {
                return bd09ToGcj02(lon, lat);
            }
        } else if (CoorType.WGS84.equals(from)) {
            if (CoorType.BD09LL.equals(to)) {
                double[] wgs84ToGcj02 = wgs84ToGcj02(lon, lat);
                return gcj02ToBd09(wgs84ToGcj02[0], wgs84ToGcj02[1]);
            } else if (CoorType.GCJ02.equals(to)) {
                return wgs84ToGcj02(lon, lat);
            }
        }

        return new double[]{lon, lat};
    }

    /**
     * 地图坐标转换
     *
     * @param wgLon
     * @param wgLat
     * @param coorType 请参照 GpsCoorType
     * @return
     */
    public static double[] gpsConvertor(double wgLon, double wgLat, GpsCoorType coorType) {
        if (GpsCoorType.A2B.equals(coorType)) {
            return gcj02ToBd09(wgLon, wgLat);
        } else if (GpsCoorType.B2A.equals(coorType)) {
            return bd09ToGcj02(wgLon, wgLat);
        } else if (GpsCoorType.A2W.equals(coorType)) {
            return gcj02ToWgs84(wgLon, wgLat);
        } else if (GpsCoorType.B2W.equals(coorType)) {
            double[] bd09ToGcj02 = bd09ToGcj02(wgLon, wgLat);
            return gcj02ToWgs84(bd09ToGcj02[0], bd09ToGcj02[1]);
        } else if (GpsCoorType.W2A.equals(coorType)) {
            return wgs84ToGcj02(wgLon, wgLat);
        } else if (GpsCoorType.W2B.equals(coorType)) {
            double[] wgs84ToGcj02 = wgs84ToGcj02(wgLon, wgLat);
            return gcj02ToBd09(wgs84ToGcj02[0], wgs84ToGcj02[1]);
        } else {
            throw new RuntimeException("coor type is error.");
        }
    }

    /**
     * GCJ-02 to WGS-84
     *
     * @param wgLon
     * @param wgLat
     * @return
     */
    private static double[] gcj02ToWgs84(double wgLon, double wgLat) {
        double initDelta = 0.01;
        double threshold = 0.000000001;
        double dLat = initDelta, dLon = initDelta;
        double mLat = wgLat - dLat, mLon = wgLon - dLon;
        double pLat = wgLat + dLat, pLon = wgLon + dLon;
        double wgsLat, wgsLon, i = 0;
        while (true) {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            double[] tmp = wgs84ToGcj02(wgsLon, wgsLat);
            dLat = tmp[1] - wgLat;
            dLon = tmp[0] - wgLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold)) {
                break;
            }

            if (dLat > 0) {
                pLat = wgsLat;
            } else {
                mLat = wgsLat;
            }
            if (dLon > 0) {
                pLon = wgsLon;
            } else {
                mLon = wgsLon;
            }

            if (++i > 10000) {
                break;
            }
        }

        return new double[]{wgsLon, wgsLat};
    }


    /**
     * WGS-84 to GCJ-02
     *
     * @param wgLon
     * @param wgLat
     * @return
     */
    private static double[] wgs84ToGcj02(double wgLon, double wgLat) {
        if (outOfChina(wgLon, wgLat)) {
            return new double[]{wgLon, wgLat};
        }

        double[] d = delta(wgLon, wgLat);
        return new double[]{wgLon + d[0], wgLat + d[1]};
    }


    /**
     * GCJ-02 to BD-09
     *
     * @param wgLon
     * @param wgLat
     * @return
     */
    private static double[] gcj02ToBd09(double wgLon, double wgLat) {
        double x = wgLon, y = wgLat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
        double bdLon = z * Math.cos(theta) + 0.0065;
        double bdLat = z * Math.sin(theta) + 0.006;
        return new double[]{bdLon, bdLat};
    }

    /**
     * BD-09 to GCJ-02
     *
     * @param wgLon
     * @param wgLat
     * @return
     */
    private static double[] bd09ToGcj02(double wgLon, double wgLat) {
        double x = wgLon - 0.0065, y = wgLat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
        double gcjLon = z * Math.cos(theta);
        double gcjLat = z * Math.sin(theta);

        return new double[]{gcjLon, gcjLat};
    }

    private static double[] delta(double wgLon, double wgLat) {
        double a = 6378245.0;
        double ee = 0.00669342162296594323;
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);

        double radLat = wgLat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * Math.PI);

        return new double[]{dLon, dLat};
    }

    private static boolean outOfChina(double lon, double lat) {
        double lonMin = 72.004;
        double lonMax = 137.8347;

        if (lon < lonMin || lon > lonMax) {
            return true;
        }

        return lat < 0.8293 || lat > 55.8271;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    public static void main(String[] args) {
        float a = 1.20f;
        float b = 0.60f;
        System.out.print(a + b);
    }

    /**
     * WGS-84 to Web mercator
     * mercatorLat -> y mercatorLon -> x
     */
    private double[] wsg84Tomercator(double wgsLon, double wgsLat) {
        double x = wgsLon * 20037508.34 / 180.;
        double y = Math.log(Math.tan((90. + wgsLat) * Math.PI / 360.)) / (Math.PI / 180.);
        y = y * 20037508.34 / 180.;
        return new double[]{y, x};

    }

    /**
     * Web mercator to WGS-84
     * mercatorLat -> y mercatorLon -> x
     */
    private double[] mercatorToWsg84(double mercatorLon, double mercatorLat) {
        double x = mercatorLon / 20037508.34 * 180.;
        double y = mercatorLat / 20037508.34 * 180.;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180.)) - Math.PI / 2);
        return new double[]{y, x};
    }

    /**
     * two point's distance
     */
    private double distance(double latA, double lonA, double latB, double lonB) {
        double earthR = 6371000.;
        double x = Math.cos(latA * Math.PI / 180.) * Math.cos(latB * Math.PI / 180.) * Math.cos((lonA - lonB) * Math
                .PI / 180);
        double y = Math.sin(latA * Math.PI / 180.) * Math.sin(latB * Math.PI / 180.);
        double s = x + y;
        if (s > 1) {
            s = 1;
        }

        if (s < -1) {
            s = -1;
        }
        double alpha = Math.acos(s);
        double distance = alpha * earthR;
        return distance;
    }
}
