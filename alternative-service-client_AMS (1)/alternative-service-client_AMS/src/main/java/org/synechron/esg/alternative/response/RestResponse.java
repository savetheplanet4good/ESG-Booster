package org.synechron.esg.alternative.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Rest response.
 */
public class RestResponse implements Serializable {


    /**
     * Instantiates a new Rest response.
     *
     * @param status  the status
     * @param message the message
     */
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

    /**
     * Gets status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * Gets metadata.
     *
     * @return the metadata
     */
    public Object getMetadata() {
        return metadata;
    }

    /**
     * Sets metadata.
     *
     * @param metadata the metadata
     */
    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public Object getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RestResponse [status=" + status + ", message=" + message + ", data=" + data + ", metadata=" + metadata
                + ", error=" + error + "]";
    }


}
