package com.uzair.buildingapp.HomeDashBoard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LogModel {


    private double distance;
    private double lat;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    private double lng;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("building_guid")
    @Expose
    private String buildingGuid;
    @SerializedName("detection_type")
    @Expose
    private String detectionType;
    @SerializedName("detection_decice_hash")
    @Expose
    private String detectionDeciceHash;
    @SerializedName("mobile_device_id")
    @Expose
    private String mobileDeviceId;
    @SerializedName("geo_cordinate")
    @Expose
    private GeoCordinate geoCordinate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("log")
    @Expose
    private Object log;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getBuildingGuid() {
        return buildingGuid;
    }

    public void setBuildingGuid(String buildingGuid) {
        this.buildingGuid = buildingGuid;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
    }

    public String getDetectionDeciceHash() {
        return detectionDeciceHash;
    }

    public void setDetectionDeciceHash(String detectionDeciceHash) {
        this.detectionDeciceHash = detectionDeciceHash;
    }

    public String getMobileDeviceId() {
        return mobileDeviceId;
    }

    public void setMobileDeviceId(String mobileDeviceId) {
        this.mobileDeviceId = mobileDeviceId;
    }

    public GeoCordinate getGeoCordinate() {
        return geoCordinate;
    }

    public void setGeoCordinate(GeoCordinate geoCordinate) {
        this.geoCordinate = geoCordinate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getLog() {
        return log;
    }

    public void setLog(Object log) {
        this.log = log;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }


    public class GeoCordinate {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("coordinates")
        @Expose
        private List<Double> coordinates = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }


    }
}

