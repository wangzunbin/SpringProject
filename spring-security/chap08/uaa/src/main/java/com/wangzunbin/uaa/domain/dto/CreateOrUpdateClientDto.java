package com.wangzunbin.uaa.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:CreateOrUpdateClientDto
 * Function:
 *
 * @author WangZunBin
 * @version 1.0 2021/5/5 21:44
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateClientDto implements Serializable {
 private static final long serialVersionUID = 1L;

 private String clientId;
 private String clientSecret;
 private List<String> scopes;
 private List<String> grantTypes;
 private List<String> autoApproves;
 private Set<String> redirectUris;
 private int accessTokenValidity;
 private int refreshTokenValidity;
}
