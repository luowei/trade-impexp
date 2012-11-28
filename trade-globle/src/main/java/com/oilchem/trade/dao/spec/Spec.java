package com.oilchem.trade.dao.spec;

import com.oilchem.trade.domain.ImpTradeDetail;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 下午10:06
 * To change this template use File | Settings | File Templates.
 */
public class Spec<T> {

    /**
     * 是否有字段
     * @param fieldName       表字段
     * @param fieldValue       java对象字段
     * @return
     */
    public  Specification<T>
    hasField(final String fieldName,final Comparable fieldValue){

        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(fieldName==null){
                    return toPredicate(root,query,cb);
                }
                return cb.equal(root.get(fieldName),fieldValue);
            }

        };
    }

    public static Specification<ImpTradeDetail> hasCity(
            final String fieldName,final String fieldValue) {
        return new Specification<ImpTradeDetail>() {

            @Override
            public javax.persistence.criteria.Predicate
            toPredicate(Root<ImpTradeDetail> impTradeDetailRoot,
                        CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(fieldValue==null) return null;
                return cb.equal(impTradeDetailRoot.get(fieldName),fieldValue);
            }
        };
    }

    public Specification<ImpTradeDetail> hasCity2() {
        return new Specification<ImpTradeDetail>() {

            @Override
            public javax.persistence.criteria.Predicate
            toPredicate(Root<ImpTradeDetail> impTradeDetailRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(impTradeDetailRoot.get("city"),"广东广州市");
            }
        };
    }

}
