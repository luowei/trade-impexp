package com.oilchem.trade.dao;

import com.oilchem.trade.domain.Log;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: 下午12:44
 * To change this template use File | Settings | File Templates.
 */
public interface LogDao extends CrudRepository<Log,Long>,BaseDao<Log> {

    List<Log> findByExtractFlagAndTableType(String unextract_flag,String tableType);

    List<Log> findByImportFlagAndTableType(String unimport_flag, String detail);
}
