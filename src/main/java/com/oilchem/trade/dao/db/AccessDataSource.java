package com.oilchem.trade.dao.db;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class AccessDataSource{

    /**
     * 因为每一个Access文件都要有对应的dataSource，所以这里不能使用单例
     * @param accessPath
     * @return
     */
    public DataSource getAccessDataSource(String accessPath){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
        dataSource.setUrl("jdbc:odbc:Driver={Microsoft Access Drive (*.mdb)};"+accessPath);
        return dataSource;

    }
}
