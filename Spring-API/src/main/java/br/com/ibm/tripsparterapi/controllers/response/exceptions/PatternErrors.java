package br.com.ibm.tripsparterapi.controllers.response.exceptions;

import java.io.Serializable;

public class PatternErrors implements Serializable {
    private Integer status;
    private String massage, path;

    public PatternErrors(Integer status, String massage, String path) {
        this.status = status;
        this.massage = massage;
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
