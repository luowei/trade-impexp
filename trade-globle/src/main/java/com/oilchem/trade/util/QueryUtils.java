package com.oilchem.trade.util;


import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className:QueryUtils
 * @classDescription:
 * @author:luowei
 * @createTime:12-11-12
 */
public abstract class QueryUtils {

    public static String DEFAULT_ORDER = "id: asc";
    public static Integer DEFAULT_PAGESIZE = 100;

    /**
     * 获得排序条件
     *
     * @param orderMap 排序字段map
     * @return
     */
    public static Sort sortByOrderFiled(Map<String, Sort.Direction> orderMap) {
        if (orderMap == null) return null;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        for (Map.Entry<String, Sort.Direction> entry : orderMap.entrySet()) {
            orderList.add(new Sort.Order(entry.getValue(), entry.getKey()));
        }
        return new Sort(orderList);
    }

    /**
     * 根据条件查询，多条件只支持and，返回分页对象
     *
     * @param pfList
     * @return
     */
    public static String getWheres(List<PropertyFilter> pfList) {
        String where = null;
        Map<String, Object> params = new HashMap<String, Object>();

        // count用来计数条件个数
        int count = 0;

        StringBuilder sb = new StringBuilder();

        if (pfList != null && !pfList.isEmpty()) {

//            sb.append(" where ");

            for (PropertyFilter pf : pfList) {
                if (pf.isNotBlankOfThisPropertyValue()) {
                    count++;

                    // count大于1表示至少有两个条件
                    if (count > 1) {
                        sb.append(" and ");
                    }

//                    sb.append(pf.getName());
                    sb.append("o.").append(pf.getName());

                    switch (pf.getType()) {
                        case EQ:
                            sb.append(" = ");
                            break;
                        case GT:
                            sb.append(" > ");
                            break;
                        case GE:
                            sb.append(" >= ");
                            break;
                        case LT:
                            sb.append(" < ");
                            break;
                        case LE:
                            sb.append(" <= ");
                            break;
                        case LIKE:
                            sb.append(" like ");
                            break;
                    }

                    if (pf.getType().equals(Type.LIKE)) {
                        sb.append(" %" + pf.getValue() + "% ");
                    } else {
                        sb.append(pf.getValue());
                    }
                }
            }

            where = sb.toString();
        }

        return where;
    }


    /**
     * @className:PropertyFilter
     * @classDescription:
     * @author:luowei
     * @createTime:12-10-12
     */
    public static class PropertyFilter {
        private String name;    // 条件名称
        private Object value;   // 条件值
        private Type type;      // 查询类型

        public PropertyFilter() {
        }

        public PropertyFilter(String name, Object value) {
            this.name = name;
            this.value = value;
            this.type = Type.EQ;
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
         *
         * @return
         */
        public boolean isNotBlankOfThisPropertyValue() {
            if (value instanceof String) {
                String str = (String) value;

                return (null != str && !str.trim().isEmpty());
            } else {
                return (null != value);
            }

        }

        /**
         * 添加属性
         *
         * @param name
         * @param value
         * @return
         */
        public PropertyFilter add(String name, String value, Type type) {

            if (name != null && name != "") {
                if (type == null) {
                    type = Type.EQ;
                }
                return new PropertyFilter(name, value, type);
            } else return this;
        }
    }


    /**
     * @className:Type
     * @classDescription:
     * @author:luowei
     * @createTime:12-1--12
     */
    public static enum Type {
        EQ, GT, GE, LT, LE, LIKE
    }
}

