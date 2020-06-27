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

/**
 * ClassName:JpqlTest  <br/>
 * Function: 暂不考虑 <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/6/27 23:59   <br/>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JpqlTest {

    @Autowired
    private ICustomerDao customerDao;

    @Test
    public void testFindAll() throws Exception{
        List<Customer> customers = customerDao.findAll();
        log.info("收到的全部数据: {}", customers.toString());
    }

    @Test
    public void testSelectDesc() throws Exception{
//        customerDao.findAll(Sort.sort())
    }
}
