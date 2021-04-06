package com.wangzunbin.uaa.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:Auth
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/4/5 0:09
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Auth implements Serializable {

    private String accessToken;
    private String refreshToken;

}
