package org.synechron.portfolio.response.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;

public class RestResponse implements Serializable {

    public RestResponse(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        this.data = new Object();
        this.metadata = new Object();
        this.error = new ArrayList<>();
    }

    private HttpStatus status;
    private String message = "";
    private Object data;
    private Object metadata;
    private Object error;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RestResponse [status=" + status + ", message=" + message + ", data=" + data + ", metadata=" + metadata
                + ", error=" + error + "]";
    }
}
