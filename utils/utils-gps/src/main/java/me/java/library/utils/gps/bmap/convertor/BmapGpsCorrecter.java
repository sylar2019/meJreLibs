package me.java.library.utils.gps.bmap.convertor;

import me.java.library.utils.gps.AbstractGpsCorrecter;
import me.java.library.utils.gps.common.BasicPosition;

/**
 * @author :  luhao
 * @FileName :  BmapGpsCorrecter
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
public class BmapGpsCorrecter extends AbstractGpsCorrecter {
    private String bmapGeoconvUrl;

    /**
     * wgs84转百度坐标系
     *
     * @param basicPosition
     */
    public static <T extends BasicPosition> void wgs2bmap(T basicPosition) {
//        RestTemplate restTemplate = new RestTemplate();
    }

    public static double[] wgs2bmap(double wgLon, double wgLat) {
        double[] result = new double[2];

        return result;
    }

}
