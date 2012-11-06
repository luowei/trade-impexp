package com.oilchem.trade.db;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class AccessDataSource {

    public static DataSource getAccessDataSource(String accessPath){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
        dataSource.setUrl("jdbc:odbc:Driver={Microsoft Access Drive (*.mdb)};"+accessPath);
//        dataSource.setUsername("username");
//        dataSource.setPassword("password");
//        dataSource.setMaxActive(10);
//        dataSource.setMaxIdle(5);
//        dataSource.setInitialSize(5);
//        dataSource.setValidationQuery("SELECT 1");

        return dataSource;

    }
}
