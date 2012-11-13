package com.oilchem.trade.service;

import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.dao.map.MyRowMapper;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.util.DetailCriteria;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;
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
    String unpackageFile(Map.Entry<Long, String> logEntry, String unPackageDir);

    /**
     * 导入查询条件表
     *
     * @param jdbcTemplate
     * @param sql
     * @param connection
     * @return
     * @author wei.luo
     * @createTime 2012-11-7
     */
    <E extends IdEntity> Boolean
    importCriteriaTab(JdbcTemplate jdbcTemplate, String sql, Connection connection);

    /**
     * 过滤条件
     *
     * @param detailCriteriaList
     * @param sql
     * @param conn
     * @return
     */
    public <E extends IdEntity> void
    queryCriteriaRecord(List<DetailCriteria> detailCriteriaList,
                        String sql,
                        Connection conn);

    /**
     * 导入贸易明细
     *
     * @param tradeDetailDao
     * @param jdbcTemplate      jdbcTemplate
     * @param tradeDetailMapper tradeDetailMapper
     * @param year              year
     * @param month             month
     * @param sql               sql      @return
     * @author wei.luo
     * @createTime 2012-11-7
     */
    <E extends TradeDetail, T extends AbstractTradeDetailRowMapper>
    Boolean importTradeDetail(
            CrudRepository repository,
            BaseDao<E> tradeDetailDao,
            JdbcTemplate jdbcTemplate,
            T tradeDetailMapper,
            Integer year,
            Integer month,
            String sql);

    /**
     * 导入Excel
     *
     * @param repository
     * @param tradeSumDao
     * @param excelSource         excel文件源目录
     * @param tradeSumClass       tradeSum Class
     * @param tradeSumRowMapClass tradeSumRowMap Class
     * @param year                数据所在年
     * @param month               数据所在月
     * @param productType         产品类型
     * @return 成功或失败
     * @author wei.luo
     * @createTime 2012-11-7
     */
    <E extends TradeSum, M extends MyRowMapper<E>>
    Boolean importExcel(
            CrudRepository repository,
            BaseDao<E> tradeSumDao,
            String excelSource,
            Class<E> tradeSumClass,
            Class<M> tradeSumRowMapClass,
            Integer year,
            Integer month,
            String productType);

    /**
     * 获得未解压的文件列表
     *
     * @return 返回记录的Id与包的全路径组成的Map
     * @author wei.luo
     * @createTime 2012-11-7
     */
    Map<Long, String> getUnExtractPackage(String packageType);

    /**
     * 获得未导入的文件列表
     *
     * @param fileType 文件类型
     * @return 返回记录的Id与文件的全路径组成的Map
     * @author wei.luo
     * @createTime 2012-11-7
     */
    Map<Long, String> getUnImportFile(String fileType);

    /**
     * 获得Model的list列表
     *
     * @param tClass tClass
     * @param <T>
     * @return
     * @author wei.luo
     * @createTime 2012-11-7
     */
    <T extends IdEntity> List<T> getModelList(Class<T> tClass);

    Iterable<ProductType> getProductList();
}
