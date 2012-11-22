package com.oilchem.trade.util;

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
public abstract class JdbcUtil<E> {

    static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    private static String driverClass;
    private static String url;
    private static Properties properties;
    private static List<String> args = new ArrayList<String>();
    private static String sql;

    /**
     * 初始化jdbc参数
     *
     * @param driverClass
     * @param url
     * @param sql
     * @param properties
     * @param args
     */
    public static void init(String driverClass, String url, String sql,
                            Properties properties, String... args) {

        JdbcUtil.driverClass = driverClass;
        JdbcUtil.url = url;
        JdbcUtil.sql = url;
        JdbcUtil.properties = properties;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                JdbcUtil.args.add(args[i]);
            }
        }
    }

    /**
     * 获得查询list
     *
     * @param obj
     * @return
     */
    public final List<E> getList(Object... obj) {
        Connection conn = null;
        Statement statement = null;
        PreparedStatement preStatement = null;
        ResultSet rs = null;
        List<E> list = new ArrayList<E>();
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, properties);

            if (args.size() > 0) {
                statement = conn.createStatement();
                rs = statement.executeQuery(sql);
            } else {
                preStatement = conn.prepareStatement(sql);
                for (int i = 0; i < args.size(); i++) {
                    preStatement.setObject(i + 1, args.get(i));
                }
                rs = preStatement.executeQuery();
            }

            while (rs.next()) {
                list.add(constructBean(rs, obj));
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.args.clear();
            closeDBResource(conn, statement, preStatement, rs);
        }
        return list;
    }

    public abstract E constructBean(ResultSet rs, Object... obj) throws SQLException;


    /**
     * 更新
     *
     * @param sql
     * @param values
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int update(String sql, Object... values) {
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

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        } finally {
            closeDBResource(conn, null, preStatement, null);
        }
        return row;
    }

    /**
     * 关闭数据库资源
     *
     * @param conn
     * @param statement
     * @param preStatement
     * @param rs
     */
    private static void closeDBResource(
            Connection conn, Statement statement,
            PreparedStatement preStatement, ResultSet rs) {
        try {
            if (rs == null) {
                rs.close();
                rs = null;
            }
            if (statement == null) {
                statement.close();
                statement = null;
            }
            if (preStatement == null) {
                preStatement.close();
                preStatement = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }


}
