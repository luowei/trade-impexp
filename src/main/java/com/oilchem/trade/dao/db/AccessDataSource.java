package com.oilchem.trade.dao.db;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class AccessDataSource {

    static Logger logger = LoggerFactory.getLogger(AccessDataSource.class);

    /**
     * 因为每一个Access文件都要有对应的dataSource，所以这里不能使用单例
     *
     * @param accessPath
     * @return
     */
    public DataSource getAccessDataSource(String accessPath) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver");
        dataSource.setUrl("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};charSet=GBK;DBQ=" + accessPath);
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }

    /**
     * 获得access文件的连接
     * @param accessPath      access文件路径
     * @return
     */
    public  Connection getAccessConnection(String accessPath){
        if(accessPath==null || accessPath.equals("")) return null;

        Connection conn = null;
        Properties prop = new Properties();
        prop.put("charSet", "GBK");
        prop.put("user", "");
        prop.put("password", "");
        String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="
                + accessPath;

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection(url, prop);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
}
