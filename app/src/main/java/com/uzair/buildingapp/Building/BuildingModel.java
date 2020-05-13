package com.uzair.buildingapp.Building;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuildingModel {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("company_guid")
        @Expose
        private String companyGuid;
        @SerializedName("no_of_floor")
        @Expose
        private Integer noOfFloor;
        @SerializedName("contact_person_guid")
        @Expose
        private String contactPersonGuid;
        @SerializedName("geo_cordinate")
        @Expose
        private GeoCordinate geoCordinate;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("workflow")
        @Expose
        private Object workflow;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_by")
        @Expose
        private Integer updatedBy;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompanyGuid() {
            return companyGuid;
        }

        public void setCompanyGuid(String companyGuid) {
            this.companyGuid = companyGuid;
        }

        public Integer getNoOfFloor() {
            return noOfFloor;
        }

        public void setNoOfFloor(Integer noOfFloor) {
            this.noOfFloor = noOfFloor;
        }

        public String getContactPersonGuid() {
            return contactPersonGuid;
        }

        public void setContactPersonGuid(String contactPersonGuid) {
            this.contactPersonGuid = contactPersonGuid;
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

        public Object getWorkflow() {
            return workflow;
        }

        public void setWorkflow(Object workflow) {
            this.workflow = workflow;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public Integer getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Integer updatedBy) {
            this.updatedBy = updatedBy;
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





