package com.supercanvasser.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ResponseBean<T> implements Serializable {

    private Object status;

//    @JsonProperty("Msg")
    private String msg;

    private T data;


    /**
     * For building http response with data
     * @param status the status code (as {@code HttpStatus} or as {@code Integer} value)
     * @param message
     * @param details
     */
    public ResponseBean(Object status, String message, T details) {
        this.status = status;
        this.msg = message;
        this.data = details;
    }


    /**
     * For building http response without data
     * @param status
     * @param message
     */
    public ResponseBean(Object status, String message){
        this(status, message, null);
    }
    /**
     * Return the HTTP status code of the response.
     * @return the HTTP status as an HttpStatus enum entry
     */
    public HttpStatus getStatus() {
        if (this.status instanceof HttpStatus) {
            return (HttpStatus) this.status;
        }
        else {
            return HttpStatus.valueOf(Integer.valueOf(this.status.toString()));
        }
    }



    /**
     * Return the HTTP status code of the response.
     * @return the HTTP status as an int value
     * @since 4.3
     */
    @JsonIgnore
    public Integer getStatusCode() {
        if (this.status instanceof HttpStatus) {
            return ((HttpStatus) this.status).value();
        }
        else {
            return Integer.valueOf(this.status.toString());
        }
    }


    public void setStatus(Object status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        builder.append("\"msg\": \"");
        builder.append(this.msg);
        builder.append("\", ");
        builder.append("\"data\": ");
        builder.append(this.data);
        builder.append(", ");
        if (this.status instanceof HttpStatus) {
            builder.append("\"statusCode\": ");
            builder.append(((HttpStatus) this.status).toString());
            builder.append(", ");
            builder.append("\"status\": ");
            builder.append(((HttpStatus) this.status).getReasonPhrase());
        }
        else {
            builder.append("\"statusCode\": ");
            builder.append(this.status);
        }
        builder.append('}');
        return builder.toString();
    }

}
