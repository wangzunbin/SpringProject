package com.wzb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:UserQueryCondition  <br/>
 * Function: User的查询条件 <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/2 21:13   <br/>
 */

@Data
public class UserQueryCondition {

    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "年龄起始值")
    private Integer ageStart;
    @ApiModelProperty(value = "年龄结束值")
    private Integer ageEnd;
    private String xxxx;

}
