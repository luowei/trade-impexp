package com.oilchem.test;

import com.oilchem.trade.util.GenericsUtils;
import com.oilchem.trade.domain.City;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-15
 * Time: 下午1:04
 * To change this template use File | Settings | File Templates.
 */
public class GenericTest<T> {

    private  Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());

    public static void main(String[] args){
        GenericTest<City> cityGenericTest = new GenericTest<City>();
        System.out.println(cityGenericTest.getClass().getTypeParameters().getClass().getSimpleName());
        System.out.println(GenericTest.class.getTypeParameters().getClass().getSimpleName());
    }
}
