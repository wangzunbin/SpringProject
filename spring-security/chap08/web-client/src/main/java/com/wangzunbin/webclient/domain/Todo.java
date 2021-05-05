package com.wangzunbin.webclient.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:Todo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 23:32
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo implements Serializable {

    private Long id;
    private String title;
    private String desc;
    private String createdBy;

}
