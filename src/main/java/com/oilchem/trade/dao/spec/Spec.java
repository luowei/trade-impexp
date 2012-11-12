package com.oilchem.trade.dao.spec;

import com.oilchem.trade.domain.ImpTradeDetail;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.expression.spel.ast.Selection;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.context.ContextLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.MapAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.springframework.data.jpa.domain.Specifications.*;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 下午10:06
 * To change this template use File | Settings | File Templates.
 */
public class Spec {

    /**
     * 是否有字段
     * @param colum       表字段
     * @param field       java对象字段
     * @param <T>          参数
     * @return
     */
    public static <T> org.springframework.data.jpa.domain.Specification<T> hasField(final String colum,final Serializable field){

        return new org.springframework.data.jpa.domain.Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get(colum),field);
            }
        };
    }

}
