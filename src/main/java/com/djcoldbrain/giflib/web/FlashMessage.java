package com.djcoldbrain.giflib.web;

public class   FlashMessage {

    private String message;
    private Status status;

    public FlashMessage(String message, Status status) {
        setMessage(message);
        setStatus(status);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static enum Status{
       SUCCESS, FAILURE
    }
}
