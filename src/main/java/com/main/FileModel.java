package com.main;

import java.util.Date;

public class FileModel {
    private Date utcDateTime;
    private String latitude;
    private String longitude;
    private String timeZone;

    public FileModel() {}

    public FileModel(Date utcDateTime, String latitude, String longitude) {
        this.utcDateTime = utcDateTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Date getUtcDateTime() {
        return utcDateTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
