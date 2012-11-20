package com.oilchem.trade.service;

import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.dao.map.MyRowMapper;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.bean.YearMonthDto;
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
     * @param sqlList
     * @param accessPath
     * @return
     */
    public void
    importCriteriaTab(List<String> sqlList, final String accessPath);



    /**
     * 导入明细数据
     *
     * @param repository
     * @param tradeDetailMapper tradeDetailMapper
     * @param yearMonthDto
     * @param accessPath
     * @param sql               sql      @return  @author wei.luo
     * @param detailClz         明细抽象类
     */
    public <E extends TradeDetail, T extends AbstractTradeDetailRowMapper>
    void importTradeDetail(
            CrudRepository repository,
            T tradeDetailMapper, YearMonthDto yearMonthDto,
            String accessPath, String sql, Class detailClz);


    /**
     * 导入Excel
     * @param repository
     * @param tradeSumDao
     * @param logEntry
     * @param tradeSumClass       tradeSum Class
     * @param tradeSumRowMapClass tradeSumRowMap Class
     * @param yearMonthDto    @return 成功或失败
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
     * @return 返回记录的Id与包的全路径组成的Map
     * @author wei.luo
     * @createTime 2012-11-7
     * @param tableType
     */
    Map<Long, Log> getUnExtractPackage(String tableType);

    /**
     * 获得未导入的文件列表
     *
     * @param tableType@return 返回记录的Id与文件的全路径组成的Map
     * @author wei.luo
     * @createTime 2012-11-7
     */
    Map<Long,Log> getUnImportFile(String tableType);

    /**
     * 获得数据模型的数据列表
     *
     * @param daoClass daoClass
     * @param idEntityName
     * @return
     */
    public <T extends IdEntity> List<T> findAllIdEntityList(Class daoClass, String idEntityName);

    /**
     * 从Access表中获得明细数据list
     *
     * @param tradeDetailMapper tradeDetailMapper
     * @param yearMonthDto
     * @param accessPath
     * @param sql               sql
     * @param detailClz         detailClz   @return
     */
    public  <E extends TradeDetail, T extends AbstractTradeDetailRowMapper> List<E>
    getListFormDB(T tradeDetailMapper, YearMonthDto yearMonthDto,
                  String accessPath, String sql, Class detailClz);

}
