package com.oilchem.common.bean;

/**
 * @className:PropertyFilter
 * @classDescription:
 * @author:luowei
 * @createTime:12-10-12
 */
public class PropertyFilter {
    private String name;    // 条件名称
    private Object value;   // 条件值
    private Type type;      // 查询类型

    public PropertyFilter(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public PropertyFilter(String name, Object value, Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     * 返回当前条件参数是否有效,字符串不为空,非字符串的不为null
     * @return
     */
    public boolean isNotBlankOfThisPropertyValue() {
        if(value instanceof String){
            String str = (String) value;

            return (null != str && !str.trim().isEmpty());
        }else {
            return (null != value);
        }

    }
}

