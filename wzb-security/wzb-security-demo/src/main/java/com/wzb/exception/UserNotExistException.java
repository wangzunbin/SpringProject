package com.wzb.exception;

/**
 * ClassName:UserNotExistException  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/4 10:52   <br/>
 */
public class UserNotExistException  extends RuntimeException{



    private String id;

    public UserNotExistException() {
        super("user not exit");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
