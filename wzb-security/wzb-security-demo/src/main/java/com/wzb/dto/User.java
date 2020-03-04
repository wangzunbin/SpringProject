package com.wzb.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.wzb.validator.MyConstraint;
import lombok.Data;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * ClassName:User  <br/>
 * Funtion:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 21:08   <br/>
 */

@Data
public class User {

    public interface UserSimpleView{};
    public interface UserDetailView extends UserSimpleView{}

    //使用Jsonview解决忽略字段(PS: 这里是password)
    @JsonView(UserSimpleView.class)
    @MyConstraint(message = "这是一个测试")
    private String username;

    @JsonView(UserDetailView.class)
    private String password;


    private Integer id;

    @Past
    private Date birthDay;
}
