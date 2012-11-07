package com.oilchem.trade.service;

import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.domain.abstrac.IdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public interface CommonService {

    /**
     * 解压文件
     * @param zipFileFullName   含绝对路径的zip文件全名
     * @param unZipFullName    解压后含绝对路径的文件全名
     * @return
     */
    String unZipFile(String zipFileFullName,String unZipFullName);

    /**
     * 导入access表数据
     * @param accessFileFullName
     * @param impExpTypeEnum
     * @return
     */
    public Boolean importAccess(String accessFileFullName, Enum<ImpExpType> impExpTypeEnum);

    /**
     * 导入数据的方法
     * @param jdbcTemplate   jdbcTemplate
     * @param repository    mondel dao
     * @param e     model bean
     * @param sql  jdbcTemplate's query sql
     * @param filedName  access table's filed name
     * @return
     */
    public <E extends IdEntity> List<E> queryDiffRecord(JdbcTemplate jdbcTemplate,
           final Repository repository, final E e,
           String sql, final String filedName);

    /**
     * 导入查询条件表
     * @param jdbcTemplate
     * @param sql
     * @return
     */
    public Boolean importCriteriaTab(JdbcTemplate jdbcTemplate,String sql);

    /**
     * 导入贸易明细
     * @param crudRepository  crudRepository与baseDaoDao传相同对象
     * @param baseDaoDao        crudRepository与baseDaoDao传相同对象
     * @param jdbcTemplate
     * @param tradeDetailMapper
     * @param yearMonth
     * @param sql
     * @param <T>
     * @return
     */
    public <T extends AbstractTradeDetailRowMapper> Boolean importTradeDetail(
            CrudRepository crudRepository,BaseDao baseDaoDao,JdbcTemplate jdbcTemplate,
            T tradeDetailMapper,Date yearMonth, String sql) ;

}
