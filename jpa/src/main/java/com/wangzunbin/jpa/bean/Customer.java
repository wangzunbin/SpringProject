package com.wangzunbin.jpa.bean;

import java.io.Serializable;

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
 *         @Entity
 *                        作用：指定当前类是实体类。
 *         @Table
 *            作用：指定实体类和表之间的对应关系。
 *         	属性：
 *         		name：指定数据库表的名称
 *         @Id
 *            作用：指定当前字段是主键。
 *         @GeneratedValue
 *            作用：指定主键的生成方式。。
 *         	属性：
 *         		strategy ：指定主键生成策略。
 *         @Column
 *            作用：指定实体类属性和数据库表之间的对应关系
 *         	属性：
 *         		name：指定数据库表的列名称。
 *         		unique：是否唯一
 *         		nullable：是否可以为空
 *         		inserttable：是否可以插入
 *         		updateable：是否可以更新
 *         		columnDefinition: 定义建表时创建此列的DDL
 *         		secondaryTable: 从表名。如果此列不建在主表上（默认建在主表），该属性定义该列所在从表的名字搭建开发环境[重点]
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity //声明实体类
@Table(name="cst_customer") //建立实体类和表的映射关系
public class Customer implements Serializable {

    @Id//声明当前私有属性为主键
    // 配置主键的生成策略
    // IDENTITY:自增, 注: 底层数据库必须支持自动增长(底层数据库支持自动增长方式, 对ID自增)
    // SEQUENCE: 序列, oracle, 底层数据库必须支持序列
    // TABLE: jpa提供的一种机制, 通过一张数据表的形式帮助我们完成主键自增 (不推荐)
    // AUTO: 由程序自动的帮助我们选择主键生产策略  (不推荐)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="cust_id") //指定和表中cust_id字段的映射关系
    private Long custId;

    @Column(name="cust_name") //指定和表中cust_name字段的映射关系
    private String custName;

    @Column(name="cust_source")//指定和表中cust_source字段的映射关系
    private String custSource;

    @Column(name="cust_industry")//指定和表中cust_industry字段的映射关系
    private String custIndustry;

    @Column(name="cust_level")//指定和表中cust_level字段的映射关系
    private String custLevel;

    @Column(name="cust_address")//指定和表中cust_address字段的映射关系
    private String custAddress;

    @Column(name="cust_phone")//指定和表中cust_phone字段的映射关系
    private String custPhone;

}
