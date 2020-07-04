package com.wangzunbin.day02;

import com.wangzunbin.day02.dao.ICustomerDao;
import com.wangzunbin.day02.domain.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName:CustomerDaoTest  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/4 23:26   <br/>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")

public class ICustomerDaoTest {

    @Autowired
    private ICustomerDao customerDao;

    @Test
    public void testFindOne() {
        Customer customer = customerDao.findOne(1L);
        System.out.println(customer);
    }

    @Test
    public void testSave() throws Exception {
        Customer customer = Customer.builder().custName("wangzunbin").custSource("sutpc").custIndustry("dada").custLevel("高级")
                .custAddress("广东深圳").custPhone("110").build();
        Customer save = customerDao.save(customer);
        System.out.println(save.toString());
    }

    @Test
    @Transactional
    public void selectCustomer() throws Exception{
        // 由于jpa版本太低, 所以此特性没用
        Customer customer = customerDao.getOne(7L);
        System.out.println(customer);
    }

    @Test
    public void selectCustomerById() {
        Customer customer = customerDao.findByCustId(7L);
        System.out.println(customer);
    }

    @Test
    public void findAll() throws Exception {
        List<Customer> customers = customerDao.findAll();
        System.out.println(customers.toString());
}
}
