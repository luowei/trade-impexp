package com.oilchem.trade.config;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-16
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 */
public class Message implements IMessageCode {
    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMessage() {
        return null;
    }


    /**
     * Created with IntelliJ IDEA.
     * User: Administrator
     * Date: 12-11-15
     * Time: 下午12:44
     * To change this template use File | Settings | File Templates.
     */
    public static enum FileType implements IMessageCode{
        UPLOAD_FILE,IMPORT_FILE;

        public int getCode() {
            return this.ordinal();
        }

        public String getMessage() {
            return this.toString();
        }
    }

    /**
     * Created with IntelliJ IDEA.
     * User: Administrator
     * Date: 12-11-5
     * Time: 下午5:21
     * To change this template use File | Settings | File Templates.
     */
    public static enum ImpExpType implements IMessageCode {
        进口, 出口;

        public int getCode() {
            return this.ordinal();
        }

        public String getMessage() {
            return this.toString();
        }

        public static void main(String[] args){
            System.out.println(ImpExpType.出口.getCode());
        }
    }
}
