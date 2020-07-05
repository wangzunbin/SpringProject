package com.wangzunbin.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ClassName:Customer  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/4 23:17   <br/>
 */

// 在这里要注意, 不能使用@data, 因为使用了lombok的@data就会死循环
@Getter
@Setter
@Entity
@Table(name = "cst_customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 主表
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


    //配置客户和联系人之间的关系（一对多关系）
    /**(第一版本)
     * 使用注解的形式配置多表关系
     * 1.声明关系
     *
     * @OneToMany : 配置一对多关系
     * targetEntity ：对方对象的字节码对象
     * 2.配置外键（中间表）
     * @JoinColumn : 配置外键
     * name：外键字段名称
     * referencedColumnName：参照的主表的主键字段名称
     * <p>
     * * 在客户实体类上（一的一方）添加了外键了配置，所以对于客户而言，也具备了维护外键的作用
     */
//    @OneToMany(targetEntity = LinkMan.class)
//    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")

    /**(第二版本)
     * 放弃外键维护权
     *      mappedBy：对方配置关系的属性名称\
     * cascade : 配置级联（可以配置到设置多表的映射关系的注解上）
     *      CascadeType.all         : 所有
     *                  MERGE       ：更新
     *                  PERSIST     ：保存
     *                  REMOVE      ：删除
     *
     * fetch : 配置关联对象的加载方式
     *          EAGER   ：立即加载
     *          LAZY    ：延迟加载

     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<LinkMan> linkMans;

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custAddress='" + custAddress + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custName='" + custName + '\'' +
                ", custPhone='" + custPhone + '\'' +
                ", custSource='" + custSource + '\'' +
                ", linkMans=" + linkMans +
                '}';
    }
}
