package com.wangzunbin.dao;

import com.wangzunbin.domain.LinkMan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * ClassName:ILinkManDao  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/5 17:13   <br/>
 */

@Repository
public interface ILinkManDao extends JpaRepository<LinkMan, Long>, JpaSpecificationExecutor<LinkMan> {


}
