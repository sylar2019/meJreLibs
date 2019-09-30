package me.java.library.utils.gps.common;

/**
 * @author :  luhao
 * @FileName :  BasicPosition
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
public class BasicPosition {
    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    private boolean done;

    private double longitudeDone;

    private double latitudeDone;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public double getLongitudeDone() {
        return longitudeDone;
    }

    public void setLongitudeDone(double longitudeDone) {
        this.longitudeDone = longitudeDone;
    }

    public double getLatitudeDone() {
        return latitudeDone;
    }

    public void setLatitudeDone(double latitudeDone) {
        this.latitudeDone = latitudeDone;
    }
}
