package com.wangzunbin.day02.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName:Customer  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/4 23:17   <br/>
 */

@Data
@Entity
@Table(name = "cst_customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "cust_address")
    private String custAddress;

    @Column(name = "cust_industry")
    private String custIndustry;

    @Column(name = "cust_level")
    private String custLevel;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_phone")
    private String custPhone;

    @Column(name = "cust_source")
    private String custSource;
}
