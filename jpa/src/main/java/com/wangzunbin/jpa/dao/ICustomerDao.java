package com.wangzunbin.jpa.dao;

import com.wangzunbin.jpa.bean.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * ClassName:CustomerDao  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/27 21:15   <br/>
 */

/**
 * 符合SpringDataJpa的dao层接口规范
 * JpaRepository<操作的实体类类型，实体类中主键属性的类型> 封装了基本CRUD操作
 * JpaSpecificationExecutor<操作的实体类类型> 封装了复杂查询（分页）
 */
@Repository
public interface ICustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

}
