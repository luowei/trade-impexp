package com.oilchem.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] ars){
        List list = new ArrayList();
        list.add("aaa");
        list.add(5);

        int b = Integer.valueOf(String.valueOf(list.get(1))).intValue();
//        int a = list.get(1);

        System.out.println(list.get(1));
    }

}
