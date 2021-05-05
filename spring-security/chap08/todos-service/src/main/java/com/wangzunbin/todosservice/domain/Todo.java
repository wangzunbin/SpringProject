package com.wangzunbin.todosservice.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName:Todo
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/4 21:56
 */

@Builder
@Data
public class Todo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String desc;
    private String createdBy;
}
