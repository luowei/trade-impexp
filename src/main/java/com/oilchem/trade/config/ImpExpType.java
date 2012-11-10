package com.oilchem.trade.config;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public enum ImpExpType implements IMessageCode {
    进口, 出口;

    public int getCode() {
        return this.ordinal();
    }

    public String getMessage() {
        return this.toString();
    }
}
