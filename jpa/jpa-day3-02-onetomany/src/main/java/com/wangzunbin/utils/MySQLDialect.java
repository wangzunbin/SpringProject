package com.wangzunbin.utils;

/**
 * ClassName:MySQLDialect  <br/>
 * Function: 非常重要, 设置数据库的主表和从表没有外键 <br/>
 *
 * @author WangZunBin <br/>
 * @version 1.0 2020/7/5 17:55   <br/>
 */

public class MySQLDialect extends org.hibernate.dialect.MySQLDialect {
//
//    @Override
//    public String getAddForeignKeyConstraintString(
//            String constraintName,
//            String[] foreignKey,
//            String referencedTable,
//            String[] primaryKey,
//            boolean referencesPrimaryKey) {
//        //      设置foreignkey对应的列值可以为空
//        return " alter "+ foreignKey[0] +" set default null " ;
//    }
}

