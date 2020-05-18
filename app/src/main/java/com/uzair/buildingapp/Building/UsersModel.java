package com.uzair.buildingapp.Building;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("national_id_no")
    @Expose
    private String nationalIdNo;
    @SerializedName("national_id_no_type")
    @Expose
    private String nationalIdNoType;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("last_login_at")
    @Expose
    private Object lastLoginAt;
    @SerializedName("last_seen")
    @Expose
    private Object lastSeen;
    @SerializedName("is_guest")
    @Expose
    private Integer isGuest;
    @SerializedName("app_pin")
    @Expose
    private Object appPin;
    @SerializedName("preffered_payment_mode")
    @Expose
    private Object prefferedPaymentMode;
    @SerializedName("device_id")
    @Expose
    private Integer deviceId;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;
    @SerializedName("land_size")
    @Expose
    private String landSize;
    @SerializedName("star_rating")
    @Expose
    private String starRating;
    @SerializedName("geo_location")
    @Expose
    private Object geoLocation;
    @SerializedName("synced_at")
    @Expose
    private String syncedAt;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("state_id")
    @Expose
    private Integer stateId;
    @SerializedName("substate_id")
    @Expose
    private Integer substateId;
    @SerializedName("ward_id")
    @Expose
    private Integer wardId;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("workflow")
    @Expose
    private Object workflow;
    @SerializedName("meta")
    @Expose
    private Object meta;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("is_online")
    @Expose
    private Boolean isOnline;
    @SerializedName("companies")
    @Expose
    private List<Company> companies = null;
    @SerializedName("groups")
    @Expose
    private List<Object> groups = null;
    @SerializedName("roles")
    @Expose
    private List<Object> roles = null;
    @SerializedName("wallets")
    @Expose
    private List<Object> wallets = null;
    @SerializedName("supervisors")
    @Expose
    private List<Supervisor> supervisors = null;
    @SerializedName("tasks")
    @Expose
    private List<Object> tasks = null;
    @SerializedName("pivot")
    @Expose
    private Pivot__ pivot;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalIdNo() {
        return nationalIdNo;
    }

    public void setNationalIdNo(String nationalIdNo) {
        this.nationalIdNo = nationalIdNo;
    }

    public String getNationalIdNoType() {
        return nationalIdNoType;
    }

    public void setNationalIdNoType(String nationalIdNoType) {
        this.nationalIdNoType = nationalIdNoType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Object lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Object getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Object lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Integer getIsGuest() {
        return isGuest;
    }

    public void setIsGuest(Integer isGuest) {
        this.isGuest = isGuest;
    }

    public Object getAppPin() {
        return appPin;
    }

    public void setAppPin(Object appPin) {
        this.appPin = appPin;
    }

    public Object getPrefferedPaymentMode() {
        return prefferedPaymentMode;
    }

    public void setPrefferedPaymentMode(Object prefferedPaymentMode) {
        this.prefferedPaymentMode = prefferedPaymentMode;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getLandSize() {
        return landSize;
    }

    public void setLandSize(String landSize) {
        this.landSize = landSize;
    }

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public Object getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(Object geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getSyncedAt() {
        return syncedAt;
    }

    public void setSyncedAt(String syncedAt) {
        this.syncedAt = syncedAt;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getSubstateId() {
        return substateId;
    }

    public void setSubstateId(Integer substateId) {
        this.substateId = substateId;
    }

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Object getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Object workflow) {
        this.workflow = workflow;
    }

    public Object getMeta() {
        return meta;
    }

    public void setMeta(Object meta) {
        this.meta = meta;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public List<Object> getGroups() {
        return groups;
    }

    public void setGroups(List<Object> groups) {
        this.groups = groups;
    }

    public List<Object> getRoles() {
        return roles;
    }

    public void setRoles(List<Object> roles) {
        this.roles = roles;
    }

    public List<Object> getWallets() {
        return wallets;
    }

    public void setWallets(List<Object> wallets) {
        this.wallets = wallets;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public List<Object> getTasks() {
        return tasks;
    }

    public void setTasks(List<Object> tasks) {
        this.tasks = tasks;
    }

    public Pivot__ getPivot() {
        return pivot;
    }

    public void setPivot(Pivot__ pivot) {
        this.pivot = pivot;
    }


    public class Company {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("slug")
        @Expose
        private String slug;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("meta")
        @Expose
        private Object meta;
        @SerializedName("category_id")
        @Expose
        private Object categoryId;
        @SerializedName("categories")
        @Expose
        private Object categories;
        @SerializedName("due_date")
        @Expose
        private String dueDate;
        @SerializedName("responsible_person")
        @Expose
        private String responsiblePerson;
        @SerializedName("started_at")
        @Expose
        private String startedAt;
        @SerializedName("ended_at")
        @Expose
        private String endedAt;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("company_type")
        @Expose
        private Integer companyType;
        @SerializedName("contact_persons")
        @Expose
        private Object contactPersons;
        @SerializedName("logo")
        @Expose
        private String logo;
        @SerializedName("pivot")
        @Expose
        private Pivot pivot;
        @SerializedName("attachments")
        @Expose
        private List<Object> attachments = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getMeta() {
            return meta;
        }

        public void setMeta(Object meta) {
            this.meta = meta;
        }

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public Object getCategories() {
            return categories;
        }

        public void setCategories(Object categories) {
            this.categories = categories;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }

        public String getResponsiblePerson() {
            return responsiblePerson;
        }

        public void setResponsiblePerson(String responsiblePerson) {
            this.responsiblePerson = responsiblePerson;
        }

        public String getStartedAt() {
            return startedAt;
        }

        public void setStartedAt(String startedAt) {
            this.startedAt = startedAt;
        }

        public String getEndedAt() {
            return endedAt;
        }

        public void setEndedAt(String endedAt) {
            this.endedAt = endedAt;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getCompanyType() {
            return companyType;
        }

        public void setCompanyType(Integer companyType) {
            this.companyType = companyType;
        }

        public Object getContactPersons() {
            return contactPersons;
        }

        public void setContactPersons(Object contactPersons) {
            this.contactPersons = contactPersons;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Pivot getPivot() {
            return pivot;
        }

        public void setPivot(Pivot pivot) {
            this.pivot = pivot;
        }

        public List<Object> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<Object> attachments) {
            this.attachments = attachments;
        }

    }


    public class Pivot {

        @SerializedName("user_guid")
        @Expose
        private String userGuid;
        @SerializedName("company_guid")
        @Expose
        private String companyGuid;

        public String getUserGuid() {
            return userGuid;
        }

        public void setUserGuid(String userGuid) {
            this.userGuid = userGuid;
        }

        public String getCompanyGuid() {
            return companyGuid;
        }

        public void setCompanyGuid(String companyGuid) {
            this.companyGuid = companyGuid;
        }

    }


    public class Pivot_ {

        @SerializedName("user_guid")
        @Expose
        private String userGuid;
        @SerializedName("supervisor_guid")
        @Expose
        private String supervisorGuid;
        @SerializedName("guid")
        @Expose
        private String guid;

        public String getUserGuid() {
            return userGuid;
        }

        public void setUserGuid(String userGuid) {
            this.userGuid = userGuid;
        }

        public String getSupervisorGuid() {
            return supervisorGuid;
        }

        public void setSupervisorGuid(String supervisorGuid) {
            this.supervisorGuid = supervisorGuid;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

    }


    public class Pivot__ {

        @SerializedName("supervisor_guid")
        @Expose
        private String supervisorGuid;
        @SerializedName("user_guid")
        @Expose
        private String userGuid;
        @SerializedName("guid")
        @Expose
        private String guid;

        public String getSupervisorGuid() {
            return supervisorGuid;
        }

        public void setSupervisorGuid(String supervisorGuid) {
            this.supervisorGuid = supervisorGuid;
        }

        public String getUserGuid() {
            return userGuid;
        }

        public void setUserGuid(String userGuid) {
            this.userGuid = userGuid;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

    }


    public class Supervisor {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("guid")
        @Expose
        private String guid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("national_id_no")
        @Expose
        private String nationalIdNo;
        @SerializedName("national_id_no_type")
        @Expose
        private String nationalIdNoType;
        @SerializedName("date_of_birth")
        @Expose
        private String dateOfBirth;
        @SerializedName("last_login_at")
        @Expose
        private Object lastLoginAt;
        @SerializedName("last_seen")
        @Expose
        private String lastSeen;
        @SerializedName("is_guest")
        @Expose
        private Integer isGuest;
        @SerializedName("app_pin")
        @Expose
        private Object appPin;
        @SerializedName("preffered_payment_mode")
        @Expose
        private Object prefferedPaymentMode;
        @SerializedName("device_id")
        @Expose
        private Object deviceId;
        @SerializedName("firebase_id")
        @Expose
        private Object firebaseId;
        @SerializedName("land_size")
        @Expose
        private String landSize;
        @SerializedName("star_rating")
        @Expose
        private String starRating;
        @SerializedName("geo_location")
        @Expose
        private Object geoLocation;
        @SerializedName("synced_at")
        @Expose
        private Object syncedAt;
        @SerializedName("country_id")
        @Expose
        private Integer countryId;
        @SerializedName("state_id")
        @Expose
        private Integer stateId;
        @SerializedName("substate_id")
        @Expose
        private Integer substateId;
        @SerializedName("ward_id")
        @Expose
        private Integer wardId;
        @SerializedName("created_by")
        @Expose
        private Object createdBy;
        @SerializedName("updated_by")
        @Expose
        private Object updatedBy;
        @SerializedName("deleted_by")
        @Expose
        private Object deletedBy;
        @SerializedName("company_id")
        @Expose
        private Integer companyId;
        @SerializedName("workflow")
        @Expose
        private Object workflow;
        @SerializedName("meta")
        @Expose
        private Object meta;
        @SerializedName("status")
        @Expose
        private Object status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("is_online")
        @Expose
        private Boolean isOnline;
        @SerializedName("pivot")
        @Expose
        private Pivot_ pivot;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getNationalIdNo() {
            return nationalIdNo;
        }

        public void setNationalIdNo(String nationalIdNo) {
            this.nationalIdNo = nationalIdNo;
        }

        public String getNationalIdNoType() {
            return nationalIdNoType;
        }

        public void setNationalIdNoType(String nationalIdNoType) {
            this.nationalIdNoType = nationalIdNoType;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Object getLastLoginAt() {
            return lastLoginAt;
        }

        public void setLastLoginAt(Object lastLoginAt) {
            this.lastLoginAt = lastLoginAt;
        }

        public String getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(String lastSeen) {
            this.lastSeen = lastSeen;
        }

        public Integer getIsGuest() {
            return isGuest;
        }

        public void setIsGuest(Integer isGuest) {
            this.isGuest = isGuest;
        }

        public Object getAppPin() {
            return appPin;
        }

        public void setAppPin(Object appPin) {
            this.appPin = appPin;
        }

        public Object getPrefferedPaymentMode() {
            return prefferedPaymentMode;
        }

        public void setPrefferedPaymentMode(Object prefferedPaymentMode) {
            this.prefferedPaymentMode = prefferedPaymentMode;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public Object getFirebaseId() {
            return firebaseId;
        }

        public void setFirebaseId(Object firebaseId) {
            this.firebaseId = firebaseId;
        }

        public String getLandSize() {
            return landSize;
        }

        public void setLandSize(String landSize) {
            this.landSize = landSize;
        }

        public String getStarRating() {
            return starRating;
        }

        public void setStarRating(String starRating) {
            this.starRating = starRating;
        }

        public Object getGeoLocation() {
            return geoLocation;
        }

        public void setGeoLocation(Object geoLocation) {
            this.geoLocation = geoLocation;
        }

        public Object getSyncedAt() {
            return syncedAt;
        }

        public void setSyncedAt(Object syncedAt) {
            this.syncedAt = syncedAt;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public Integer getSubstateId() {
            return substateId;
        }

        public void setSubstateId(Integer substateId) {
            this.substateId = substateId;
        }

        public Integer getWardId() {
            return wardId;
        }

        public void setWardId(Integer wardId) {
            this.wardId = wardId;
        }

        public Object getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Object createdBy) {
            this.createdBy = createdBy;
        }

        public Object getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(Object updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getDeletedBy() {
            return deletedBy;
        }

        public void setDeletedBy(Object deletedBy) {
            this.deletedBy = deletedBy;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Object getWorkflow() {
            return workflow;
        }

        public void setWorkflow(Object workflow) {
            this.workflow = workflow;
        }

        public Object getMeta() {
            return meta;
        }

        public void setMeta(Object meta) {
            this.meta = meta;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Boolean getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(Boolean isOnline) {
            this.isOnline = isOnline;
        }

        public Pivot_ getPivot() {
            return pivot;
        }

        public void setPivot(Pivot_ pivot) {
            this.pivot = pivot;
        }

    }


}