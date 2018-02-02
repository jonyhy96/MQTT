package cn.ghostcloud.cmal.web;

import java.io.Serializable;
import java.util.Objects;

public class DataHeader<D> implements Serializable {
    private String code;
    private String message;
    private String event;
    private Long pushTime;
    private D data;

    public DataHeader(){}

    public DataHeader(String code, String message, String event, Long pushTime, D data) {
        this.code = code;
        this.message = message;
        this.event = event;
        this.pushTime = pushTime;
        this.data = data;
    }

    public DataHeader(String event, D data) {
        this.event = event;
        this.data = data;
        this.pushTime = System.currentTimeMillis();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataHeader<?> that = (DataHeader<?>) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(message, that.message) &&
                Objects.equals(event, that.event) &&
                Objects.equals(pushTime, that.pushTime) &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, message, event, pushTime, data);
    }

    @Override
    public String toString() {
        return "DataHeader{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", event='" + event + '\'' +
                ", pushTime=" + pushTime +
                ", data=" + data +
                '}';
    }
}
