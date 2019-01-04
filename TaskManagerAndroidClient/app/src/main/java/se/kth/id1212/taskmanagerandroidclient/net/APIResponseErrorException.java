package se.kth.id1212.taskmanagerandroidclient.net;


/**
 * Takes the error message from the API server when something goes wrong.
 */
public class APIResponseErrorException extends Exception {

    private String msg;

    public APIResponseErrorException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
