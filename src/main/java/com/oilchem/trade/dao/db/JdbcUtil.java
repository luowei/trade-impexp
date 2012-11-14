package com.oilchem.trade.dao.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-14
 * Time: 下午8:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class  JdbcUtil<E,O> {

    Logger logger = LoggerFactory.getLogger(getClass());

    private static String driverClass;
    private static String url;
    private static Properties properties;
    private static List<String> args = new ArrayList<String>();
    private static String sql;

    public static void init(String driverClass, String url, String sql,
                  Properties properties, String... args) {

        JdbcUtil.driverClass = driverClass;
        JdbcUtil.url = url;
        JdbcUtil.sql = url;
        JdbcUtil.properties = properties;
        if (args != null) {
            for (int i=0; i < args.length;i++) {
                JdbcUtil.args.add(args[i]);
            }
        }
    }

    public final List<E> getList() throws Exception {
        
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(url, properties);
        Statement statement = null;
        PreparedStatement preStatement = null;
        ResultSet rs = null;
        List<E> list = new ArrayList<E>();
        try {
            if(args.size() > 0){
                statement = conn.createStatement();
                rs = statement.executeQuery(sql);
            }else {
                preStatement = conn.prepareStatement(sql);
                for(int i = 0;i < args.size();i++){
                    preStatement.setObject(i+1,args.get(i));
                }
                rs = preStatement.executeQuery();
            }
            while (rs.next()) {
                list.add(constructBean());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.args.clear();
            closeDBResource(conn, statement, rs);
        }
        return list;
    }

    public int update(String sql, Object... values) throws ClassNotFoundException, SQLException {
        PreparedStatement preStatement = null;
        Connection conn = null;
        int row = 0;
        try {
            conn = DriverManager.getConnection(url, properties);
            preStatement = conn.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                preStatement.setObject(i + 1, values[i]);
            }
            row = preStatement.executeUpdate();
        }finally {
            if (preStatement != null) {
                preStatement.close();
                preStatement = null;
            } if(conn != null){
                conn.close();
                conn = null;
            }
        }
        return row;
    }

    public abstract E constructBean(O... t);

    private void closeDBResource(
            Connection conn, Statement statement, ResultSet rs) {
        try {
            if (rs == null) {
                rs.close();
            }
            if (statement == null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }


}
