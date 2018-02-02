package cn.ghostcloud.cmal.web.model;

public class RangeEventData {
    private String code;
    private String carnum;
    private String driverno;
    private String eventid;
    private String gpsno;
    private String imei;
    private String intTruckId;
    private double latitude;
    private double longitude;
    private boolean media;
    private int nextId;
    private String oid;
    private String orgcode;
    private String pointName;
    private long time;
    private int driverType;
    private int type;


    public static class Builder {
        private String code;
        private String carnum;
        private String driverno;
        private String eventid;
        private String gpsno;
        private String imei;
        private String intTruckId;
        private double latitude;
        private double longitude;
        private boolean media;
        private int nextId;
        private String oid;
        private String orgcode;
        private String pointName;
        private long time;
        private int driverType;
        private int type;

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Builder setCarnum(String carnum) {
            this.carnum = carnum;
            return this;
        }

        public Builder setDriverno(String driverno) {
            this.driverno = driverno;
            return this;
        }

        public Builder setEventid(String eventid) {
            this.eventid = eventid;
            return this;
        }

        public Builder setGpsno(String gpsno) {
            this.gpsno = gpsno;
            return this;
        }

        public Builder setImei(String imei) {
            this.imei = imei;
            return this;
        }

        public Builder setIntTruckId(String intTruckId) {
            this.intTruckId = intTruckId;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setMedia(boolean media) {
            this.media = media;
            return this;
        }

        public Builder setNextId(int nextId) {
            this.nextId = nextId;
            return this;
        }

        public Builder setOid(String oid) {
            this.oid = oid;
            return this;
        }

        public Builder setOrgcode(String orgcode) {
            this.orgcode = orgcode;
            return this;
        }

        public Builder setPointName(String pointName) {
            this.pointName = pointName;
            return this;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public Builder setDriverType(int driverType) {
            this.driverType = driverType;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public RangeEventData build() {
            RangeEventData data = new RangeEventData();
            data.carnum = this.carnum;
            data.code = this.code;
            data.driverno = this.driverno;
            data.driverType = this.driverType;
            data.eventid = this.eventid;
            data.gpsno = this.gpsno;
            data.imei = this.imei;
            data.intTruckId = this.intTruckId;
            data.latitude = this.latitude;
            data.longitude = this.longitude;
            data.media = this.media;
            data.nextId = this.nextId;
            data.oid = this.oid;
            data.orgcode = this.orgcode;
            data.pointName = this.pointName;
            data.time = this.time;
            data.type = this.type;
            return data;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getDriverno() {
        return driverno;
    }

    public void setDriverno(String driverno) {
        this.driverno = driverno;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getGpsno() {
        return gpsno;
    }

    public void setGpsno(String gpsno) {
        this.gpsno = gpsno;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIntTruckId() {
        return intTruckId;
    }

    public void setIntTruckId(String intTruckId) {
        this.intTruckId = intTruckId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isMedia() {
        return media;
    }

    public void setMedia(boolean media) {
        this.media = media;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDriverType() {
        return driverType;
    }

    public void setDriverType(int driverType) {
        this.driverType = driverType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
