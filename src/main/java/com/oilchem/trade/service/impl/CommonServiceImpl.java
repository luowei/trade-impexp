package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    public String unZipFile(String zipFileFullName, String unZipFullName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean importAccess(String accessFileFullName, Enum<ImpExpType> impExpTypeEnum) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 导入数据的方法
     * @param jdbcTemplate   jdbcTemplate
     * @param repository    mondel dao
     * @param e     model bean
     * @param sql  jdbcTemplate's query sql
     * @param filedName  access table's filed name
     * @return
     */
    public List<IdEntity> importFiled(JdbcTemplate jdbcTemplate,
        final Repository repository,final IdEntity e,
        String sql,final String filedName){

        List<IdEntity> idEntityList = jdbcTemplate.query(sql,new RowMapper<IdEntity>() {
            public IdEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString(filedName);
                String suffix = repository.getClass().getSimpleName();
                String methodName = "getBy"+suffix;
                try {
                    Method method = repository.getClass().getDeclaredMethod(methodName,String.class);
                    Object findByMethodRet = method.invoke(repository,name);  //根据名字查找

                    //如果没有找到相同记录
                    if(!(findByMethodRet instanceof IdEntity)){
                        Method setMethod = e.getClass().getDeclaredMethod("set"+suffix,String.class);
                        setMethod.invoke(e,name);
                        return e;
                    }
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }

                return null;
            }
        });
        return idEntityList;
    }
}
