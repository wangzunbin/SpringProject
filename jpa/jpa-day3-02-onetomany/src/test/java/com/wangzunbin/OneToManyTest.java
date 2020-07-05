package com.wangzunbin;

import com.wangzunbin.dao.ICustomerDao;
import com.wangzunbin.dao.ILinkManDao;
import com.wangzunbin.domain.Customer;
import com.wangzunbin.domain.LinkMan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName:OneToManyTest  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/5 17:11   <br/>
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private ILinkManDao linkManDao;

    /**
     * 保存一个客户，保存一个联系人
     *  效果：客户和联系人作为独立的数据保存到数据库中
     *      联系人的外键为空
     *  原因？
     *      实体类中没有配置关系
     */
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testAdd() {
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        /**
         * 配置了客户到联系人的关系
         *      从客户的角度上：发送两条insert语句，发送一条更新语句更新数据库（更新外键）
         * 由于我们配置了客户到联系人的关系：客户可以对外键进行维护
         */
        customer.getLinkMans().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 这样的形式更加贴近需求
     */
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testAdd1() {
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");

        /**
         * 配置联系人到客户的关系（多对一）
         *    只发送了两条insert语句
         * 由于配置了联系人到客户的映射关系（多对一）
         *
         *
         */
        linkMan.setCustomer(customer);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }


    /**
     * 会有一条多余的update语句
     *      * 由于一的一方可以维护外键：会发送update语句
     *      * 解决此问题：只需要在一的一方放弃维护权即可
     *
     */
    @Test
    @Transactional
    @Rollback(false) //不自动回滚
    public void testAdd2() {
        //创建一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李");


        linkMan.setCustomer(customer);//由于配置了多的一方到一的一方的关联关系（当保存的时候，就已经对外键赋值）
        customer.getLinkMans().add(linkMan); //由于配置了一的一方到多的一方的关联关系（发送一条update语句）

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /**
     * 级联添加：保存一个客户的同时，保存客户的所有联系人
     *      需要在操作主体的实体类上，配置casacde属性
     */
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testCascadeAdd() {
        Customer customer = new Customer();
        customer.setCustName("百度1");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("小李1");

        linkMan.setCustomer(customer);
        customer.getLinkMans().add(linkMan);

        customerDao.save(customer);
    }

    /**
     * 级联删除：
     *      删除1号客户的同时，删除1号客户的所有联系人
     */
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testCascadeRemove() {
        // 要注意配置     <prop key="hibernate.hbm2ddl.auto">update</prop>,  不然会报错  Caused by: javax.persistence.RollbackException: Transaction marked as rollbackOnly
        //1.查询1号客户
        Customer customer = customerDao.findOne(1L);
        //2.删除1号客户
        customerDao.delete(customer);
    }

    // 这里暂时使用加Transactional解决jpa来加载, 后面spring mvc web项目使用的是JavaConfig, spring boot 使用的是application-dev.properties配置#将jpa的session绑定到整个线程的Servlet过滤器，处理请求
    //spring.jpa.open-in-view=true
    //spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
    @Transactional(readOnly = true)//查询的只读就行
    @Test
    public void testFind() {
        //1.查询1号客户
        Customer customer = customerDao.findOne(2L);
        System.out.println(customer);
    }
}
