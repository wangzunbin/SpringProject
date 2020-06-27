package com.wangzunbin.jpa;

import com.wangzunbin.jpa.bean.Customer;
import com.wangzunbin.jpa.dao.ICustomerDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JpaApplicationTests {

    @Autowired
    private ICustomerDao customerDao;

    @Test
    public void testSave() throws Exception {
        Customer customer = Customer.builder().custName("wangzunbin").custSource("sutpc").custIndustry("dada").custLevel("高级")
                .custAddress("广东深圳").custPhone("110").build();
        Customer save = customerDao.save(customer);
        log.info("save: {}", save.toString());
    }

    @Test
    public void selectCustomer() throws Exception{
        Customer customer = customerDao.findById(4L).get();
        log.info("获取到: {}", customer);
    }

    @Test
    public void findAll() throws Exception {
        List<Customer> customers = customerDao.findAll();
        log.info("获取到: {}", customers);
    }
}
