package com.wangzunbin;

import com.wangzunbin.dao.ICustomerDao;
import com.wangzunbin.dao.ILinkManDao;
import com.wangzunbin.domain.Customer;
import com.wangzunbin.domain.LinkMan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * ClassName:ObjectQueryTest  <br/>
 * Function: 对象导航查询, 只能一对多的关系 <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/5 20:13   <br/>
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private ILinkManDao linkManDao;

    //could not initialize proxy - no Session
    //测试对象导航查询（查询一个对象的时候，通过此对象查询所有的关联对象）
    @Test
    @Transactional // 解决在java代码中的no session问题
    public void  testQuery1() {
        //查询id为1的客户
        Customer customer = customerDao.getOne(1l);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkMans = customer.getLinkMans();
        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }
    /**
     * (不推荐使用)
     * 对象导航查询：
     *      默认使用的是延迟加载的形式查询的
     *          调用get方法并不会立即发送查询，而是在使用关联对象的时候才会差和讯
     *      延迟加载！
     * 修改配置，将延迟加载改为立即加载
     *      fetch，需要配置到多表映射关系的注解上
     *
     */

    @Test
    @Transactional // 解决在java代码中的no session问题
    public void  testQuery2() {
        //查询id为1的客户
        Customer customer = customerDao.findOne(1l);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkMans = customer.getLinkMans();
        System.out.println(linkMans);
        System.out.println(linkMans.size());
    }



    /**
     * 从联系人对象导航查询他的所属客户
     *      * 默认 ： 立即加载
     *  延迟加载：
     *
     */
    @Test
    @Transactional // 解决在java代码中的no session问题
    public void  testQuery3() {
        LinkMan linkMan = linkManDao.findOne(2l);
        //对象导航查询所属的客户
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);
    }
}
