package com.oilchem.trade.dao.custom.impl;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.GenericsUtils;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.domain.abstrac.IdEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;


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

    public void excuteSql(String sql) {
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


    /**
     * 获得累计月数据
     *
     *
     * @param condition
     * @param productCode
     * @param yearMonth
     * @param yearMonthDto
     * @return
     */
    public List<ProductCount> getSumProductCount(String condition, String productCode, String yearMonth,
                                                 final String tableName, YearMonthDto yearMonthDto) {

        String monthSumNumStr = "select sum(amount) from "+tableName+" where product_code='" + productCode + "' and year_month='" + yearMonth + "'";
        BigDecimal monthSumNum = getJdbcTemplate().query(monthSumNumStr, new ResultSetExtractor<BigDecimal>() {
            @Override
            public BigDecimal extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                } else return null;
            }
        });

        String beginYM = yearMonthDto.getSumYear()+"-"+(yearMonthDto.getBeginSumMonth()<10?"0"+yearMonthDto.getBeginSumMonth():yearMonthDto.getBeginSumMonth());
        String endYM = yearMonthDto.getSumYear()+"-"+(yearMonthDto.getEndSumMonth()<10?"0"+yearMonthDto.getEndSumMonth():yearMonthDto.getEndSumMonth());
        final StringBuffer sumNumSql = new StringBuffer("select sum(amount) from "+tableName+" where year_month >='"+beginYM+"' and year_month <= '"+yearMonth+"' ");
        String allSumNumStr = "select sum(amount) from "+tableName +
                " where product_code='" + productCode + "' and year_month >= '"+beginYM+"' and year_month <= '"+yearMonth+"' " ;

        final BigDecimal allSumNum = getJdbcTemplate().query(allSumNumStr, new ResultSetExtractor<BigDecimal>() {
            @Override
            public BigDecimal extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getBigDecimal(1);
                } else return BigDecimal.ZERO;
            }
        });

        //tradeType -> trade_type
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (!Character.isLowerCase(c)) {
                String preStr = condition.substring(0, i) + "_";
                String suffStr = condition.substring(i).toLowerCase();
                condition = preStr + suffStr;
                break;
            }
        }
        final String finalCondition = condition;

        //若此月一条记录也没有
        if (monthSumNum==null || monthSumNum.equals(BigDecimal.ZERO)) {
            return null;
        }

        String sql = " select product_code,product_name,year_month," + condition + " as condition," +
                " sum(amount) as num,sum(amount)*100/" + monthSumNum + " as monthSumNumRatio," +
                "unit,sum(amount_money) as money " +
                " from "+tableName+" where product_code=? and year_month=? " +
                " group by year_month,product_code,product_name," + condition + ",unit";
        return getJdbcTemplate().query(sql, new RowMapper<ProductCount>() {
            @Override
            public ProductCount mapRow(ResultSet rs, int i) throws SQLException {
                //获取字段
                String productCode = rs.getString("product_code");
                String productName = rs.getString("product_name");
                String yearMonth = rs.getString("year_month");
                String conditionVal = rs.getString("condition");
                BigDecimal num = rs.getBigDecimal("num");
                BigDecimal monthSumNumRatio = rs.getBigDecimal("monthSumNumRatio");
                String unit = rs.getString("unit");
                BigDecimal money = rs.getBigDecimal("money");

                //汇总字段及比率
//                String sumNumStr = "select sum(amount) from "+tableName +
//                        " where product_code='" + productCode + "' and year_month<='" + yearMonth +
//                        "' and "+finalCondition+"='" + conditionVal + "'";
                String tempSumNumSql = sumNumSql.toString()+" and product_code='" + productCode + "' and "+finalCondition+"='" + conditionVal + "'";

                BigDecimal sumNum = getJdbcTemplate().query(tempSumNumSql, new ResultSetExtractor<BigDecimal>() {
                    @Override
                    public BigDecimal extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            return rs.getBigDecimal(1);
                        } else return null;
                    }
                });

                BigDecimal sumNumRatio = BigDecimal.ZERO;
                if (sumNum!=null && allSumNum!=null && !allSumNum.equals(BigDecimal.ZERO)) {
                    sumNumRatio = sumNum.multiply(BigDecimal.valueOf(100)).divide(allSumNum, 2, BigDecimal.ROUND_HALF_UP);
                }

//                //处理单位
//                if(unit.equals("公斤")){
//                    num=num.divide(BigDecimal.valueOf(1000),2,BigDecimal.ROUND_HALF_UP);
//                    sumNum = sumNum.divide(BigDecimal.valueOf(1000),2,BigDecimal.ROUND_HALF_UP);
//                    unit="吨";
//                }

                //构造对象
                ProductCount productCount = new ProductCount(
                        productCode,productName, yearMonth, num,
                        unit, money, conditionVal,
                        monthSumNumRatio.setScale(2,BigDecimal.ROUND_HALF_UP),
                        sumNum, sumNumRatio
                );

                return productCount;
            }
        }, productCode, yearMonth);

    }

}
