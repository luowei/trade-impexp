package com.oilchem.trade.config;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-15
 * Time: 下午12:44
 * To change this template use File | Settings | File Templates.
 */
public enum FileType implements IMessageCode{
    UPLOAD_FILE,IMPORT_FILE;

    public int getCode() {
        return this.ordinal();
    }

    public String getMessage() {
        return this.toString();
    }
}
