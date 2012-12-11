package com.oilchem.trade.service;

import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.dao.map.MyRowMapper;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.QueryUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public interface CommonService {

    /**
     * 上传文件
     *
     * @param file         MultipartFile的文件
     * @param yearMonthDto
     * @return 返回上传之后文件的url
     * @author wei.luo
     * @createTime 2012-11-7
     */
    String uploadFile(MultipartFile file, String realDir, YearMonthDto yearMonthDto);

    /**
     * 解包
     *
     * @param logEntry
     * @param unPackageDir 解压目录
     * @return 解包后的文件路径
     * @author wei.luo
     * @createTime 2012-11-7
     */
    String unpackageFile(Map.Entry<Long, Log> logEntry, String unPackageDir);

    /**
     * 导入查询条件表
     *
     * @param sql
     * @param accessPath
     * @return
     * @author wei.luo
     * @createTime 2012-11-7
     */
    <E extends IdEntity> void
    importCriteriaTab(String sql, String accessPath);


    /**
     * 导入贸易明细
     *
     * @param tradeDetailMapper tradeDetailMapper
     * @param yearMonthDto
     * @param accessPath
     * @param sql               sql      @return  @author wei.luo
     * @param detailClz         @createTime 2012-11-7
     */
    <E extends TradeDetail, T extends AbstractTradeDetailRowMapper>
    Boolean importTradeDetail(
            CrudRepository repository,
            T tradeDetailMapper,
            YearMonthDto yearMonthDto,
            String accessPath, String sql,
            Class detailClz);


    /**
     * 导入Excel
     *
     * @param repository
     * @param tradeSumDao
     * @param logEntry
     * @param tradeSumClass       tradeSum Class
     * @param tradeSumRowMapClass tradeSumRowMap Class
     * @param yearMonthDto        @return 成功或失败
     * @author wei.luo
     * @createTime 2012-11-7
     */
    <E extends TradeSum, M extends MyRowMapper<E>>
    Boolean importExcel(
            CrudRepository repository,
            BaseDao<E> tradeSumDao,
            Map.Entry<Long, Log> logEntry,
            Class<E> tradeSumClass,
            Class<M> tradeSumRowMapClass, YearMonthDto yearMonthDto);

    /**
     * 获得未解压的文件列表
     *
     * @param tableType
     * @return 返回记录的Id与包的全路径组成的Map
     * @author wei.luo
     * @createTime 2012-11-7
     */
    Map<Long, Log> getUnExtractPackage(String tableType);

    /**
     * 获得未导入的文件列表
     *
     * @param tableType@return 返回记录的Id与文件的全路径组成的Map
     * @author wei.luo
     * @createTime 2012-11-7
     */
    Map<Long, Log> getUnImportFile(String tableType);

    /**
     * 获得数据模型的数据列表
     *
     * @param daoClass     daoClass
     * @param idEntityName
     * @return
     */
    <T extends IdEntity> List<T> findAllIdEntityList(Class daoClass, String idEntityName);

    /**
     * 从Access表中获得明细数据list
     *
     * @param tradeDetailMapper tradeDetailMapper
     * @param yearMonthDto
     * @param accessPath
     * @param sql               sql
     * @param detailClz         detailClz   @return
     */
    <E extends TradeDetail, T extends AbstractTradeDetailRowMapper> int
    cacheListFormDB(T tradeDetailMapper, YearMonthDto yearMonthDto,
                    String accessPath, String sql, Class detailClz);

    /**
     * 设置year month属性到到查询的Properties当中去
     *
     * @param yearMonthDto
     */
    List<QueryUtils.PropertyFilter> getYearMonthQueryProps(YearMonthDto yearMonthDto);

    /**
     * 更新实体
     * @param daoClass
     * @param entity
     * @param <T>
     */
    <T extends IdEntity> void updateEntity(Class daoClass, T entity);

    /**
     * 删除一条记录
     * @param daoClass
     * @param id
     * @param <T>
     */
    <T extends IdEntity> void delEntity(Class daoClass, Long id);

    /**
     * 查询所有的实体记录
     * @param type
     * @param <E>
     * @return
     */
      <E> List<E> findAllEntityList(String type);

    /**
     * 更新指定id的记录
     * @param type
     * @param id
     * @param name
     */
      void update(
              String type,Long id, String name);

    /**
     * 删除指定id的记录
     * @param type
     * @param id
     */
      void delete(String type, Long id);

    /**
     * 添加记录
     * @param type
     * @param name
     */
    void add(String type, String name);



}
