package com.oilchem.trade.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class AccessDataSource{

    public DataSource getAccessDataSource(String accessPath){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
        dataSource.setUrl("jdbc:odbc:Driver={Microsoft Access Drive (*.mdb)};"+accessPath);
        return dataSource;

    }
}
