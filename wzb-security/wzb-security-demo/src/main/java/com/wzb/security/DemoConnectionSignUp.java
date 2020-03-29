package com.wzb.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * ClassName:DemoConnectionSignUp  <br/>
 * Function: 根据社交用户信息默认创建用户并返回用户唯一标识 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/30 0:14   <br/>
 */
@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

    /* (non-Javadoc)
     * @see org.springframework.social.connect.ConnectionSignUp#execute(org.springframework.social.connect.Connection)
     */
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }

}