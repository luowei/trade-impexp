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
public class ImpTradeDetailSpecification {

    @PersistenceContext
    private EntityManager em;

    public static <T> Specification<T> hasCity(final Map<String,String> columnFieldMap){
//        MapAttribute<X,K,V>

        return new Specification<T>(){

            public Predicate toPredicate(Root<T> tRoot,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Map.Entry<String,String>> entryList= new ArrayList<Map.Entry<String,String>>();
                //查询城市为指定的城市
                Predicate[] predicates = new Predicate[columnFieldMap.size()];

                entryList.addAll(columnFieldMap.entrySet());
                for (int i=0;i<entryList.size();i++){
                    Map.Entry<String,String> entry = entryList.get(i);
                    predicates[i] = cb.equal(tRoot.get(entry.getKey()),entry.getValue());
                }

                query.where(predicates);
                return null;
            }
        };
    }

    public static <T> Specification<T> where(Specification specification,final Predicate predicate){
//        LocalContainerEntityManagerFactoryBean entityManager = (LocalContainerEntityManagerFactoryBean) ContextLoader
//                .getCurrentWebApplicationContext().getBean("entityManagerFactory");
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.where(predicate);
                return null;
            }
        };

//        Specifications

    }

    public static <T> Specification<T> or(final Predicate predicate){
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.where(predicate);
                return null;
            }
        };
    }

}
