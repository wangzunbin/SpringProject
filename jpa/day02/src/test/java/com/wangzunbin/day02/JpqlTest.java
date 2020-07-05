package com.wangzunbin.day02;

import com.wangzunbin.day02.dao.ICustomerDao;
import com.wangzunbin.day02.domain.Customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName:JpqlTest  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/5 12:09   <br/>
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpqlTest {

    @Autowired
    private ICustomerDao customerDao;

    /**
     * 简单
     */
    @Test
    public void testFindJPQL() {
        List<Customer> customers = customerDao.findJpql("wangzunbin");
        System.out.println(customers);
    }


    @Test
    public void testFindCustNameAndId() {
        List<Customer> customers = customerDao.findCustNameAndId(5L, "程序员");
        System.out.println(customers);
    }

    /**
     * 测试jpql的更新操作
     *  * springDataJpa中使用jpql完成 更新/删除操作
     *         * 需要手动添加事务的支持
     *         * 默认会执行结束之后，回滚事务
     *   @Rollback : 设置是否自动回滚
     *          false | true
     */
    @Test
    @Transactional //添加事务的支持
    @Rollback(value = false)
    public void testUpdateCustomer() {
        customerDao.updateCustomer(1l,"程序员12131");
    }

    //测试sql查询
    @Test
    public void testFindSql() {
        List<Object[]> list = customerDao.findSql("wang%");
        for(Object [] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    //测试方法命名规则的查询
    @Test
    public void testNaming() {
        List<Customer> customers = customerDao.findByCustName("程序员");
        System.out.println(customers);
    }

    //测试方法命名规则的查询
    @Test
    public void testFindByCustNameLike() {
        List<Customer> list = customerDao.findByCustNameLike("wang%");
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }

    //测试方法命名规则的查询
    @Test
    public void testFindByCustNameLikeAndCustIndustry() {
        List<Customer> customers = customerDao.findByCustNameLikeAndCustIndustry("程%", "it教育");
        System.out.println(customers);
    }
}
