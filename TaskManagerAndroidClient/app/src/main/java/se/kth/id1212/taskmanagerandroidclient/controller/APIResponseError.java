package se.kth.id1212.taskmanagerandroidclient.controller;

public class APIResponseError extends Exception {

    private String msg;

    public APIResponseError(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
