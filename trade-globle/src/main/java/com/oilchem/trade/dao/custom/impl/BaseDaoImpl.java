package com.oilchem.trade.dao.custom.impl;

import com.oilchem.trade.util.GenericsUtils;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.domain.abstrac.IdEntity;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-7
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
@Resource
//@Transactional
public class BaseDaoImpl<T extends IdEntity> extends JdbcDaoSupport implements BaseDao<T> {

    private Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());

    @PersistenceContext(unitName = "jpa.trade")
    EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 根据年月删除记录
     *
     * @param year        year
     * @param month       month
     * @param idEntityClz
     * @return
     */
    @Transactional
    public Integer delWithYearMonthRecord(Integer year, Integer month, Class<T> idEntityClz) {
        Query q = em.createQuery("delete from " + idEntityClz.getSimpleName()
                + "  t where t.year = " + year + " and t.month = " + month + " ");
            Integer count = q.executeUpdate();
            return count;
    }


    /**
     * 查出指定年月的记录数
     *
     * @param year        year
     * @param month       month
     * @param idEntityClz
     * @return
     */
    public Long countWithYearMonth(Integer year, Integer month,
                                   Class<T> idEntityClz) {
        return Long.valueOf(
                String.valueOf(
                        em.createQuery("select count(*) from "
                                + idEntityClz.getSimpleName() +
                                "  t where t.year = " + year + " and t.month = " + month + "")
                                .getSingleResult()
                )
        );

    }

    public void excuteSql(String sql){
        getJdbcTemplate().execute(sql);
    }

    /**
     * 查出指定年月的记录数
     *
     * @param year        year
     * @param month       month
     * @param idEntityClz
     * @return
     */
    private Long countWithYearMonth_bak(Integer year,
                                        Integer month, Class<T> idEntityClz) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(idEntityClz);
        Root<T> root = criteria.from(idEntityClz);
        criteria.select(root);
        Predicate predicate1 = builder.and(
                builder.equal(root.get("year"), year),
                builder.equal(root.get("month"), month)
        );
        criteria.where(predicate1);
        return findCountByCriteria(criteria);
    }

    /**
     * 是否存在指定id的记录
     *
     * @param id
     * @param idEntityClz
     * @return
     */
    public T exists(Long id, Class<T> idEntityClz) {
        return em.getReference(idEntityClz, id);
    }

    /**
     * 删除
     *
     * @param entity
     */
    @Transactional
    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    /**
     * 删除一串
     *
     * @param entities
     */
    @Transactional
    public void delete(Iterable<? extends T> entities) {
        Assert.notNull(entities,
                "The given Iterable of entities not be null!");
        for (T entity : entities) {
            delete(entity);
        }
    }

    /**
     * 刷新
     */
    @Transactional
    public void flush() {
        em.flush();
    }

    /**
     * 根据 criteria 查询数量
     *
     * @param criteria
     * @param <T>
     * @return
     */
    private <T> Long findCountByCriteria(CriteriaQuery<?> criteria) {
        //得到查询构造器
        CriteriaBuilder builder = em.getCriteriaBuilder();
        //构造查询
        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        //指定主角
        Root<?> entityRoot = countCriteria.from(criteria.getResultType());
        //设置查询字段
        countCriteria.select(builder.count(entityRoot));
        //设置查询条件
        countCriteria.where(criteria.getRestriction());
        //执行查询并拿出结果
        return em.createQuery(countCriteria).getSingleResult();
    }



}
