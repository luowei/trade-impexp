package com.test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-24
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class TestOdbc {

    public static void main(String[] args){

//        Properties prop = new Properties();
//        prop.put("charSet", "GBK");
//        prop.put("user", "");
//        prop.put("password", "");
//        String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=d:/aaaa.mdb";
//        try {
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
////            Connection conn = DriverManager.getConnection(url, prop);
//            Connection conn = DriverManager.getConnection(url, prop);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        BigDecimal big = BigDecimal.valueOf(Double.valueOf("0"));
//        System.out.println(big.intValue());


        System.out.println("new Random(1000).nextInt():"+new Random().nextInt(1000));


    }



}
