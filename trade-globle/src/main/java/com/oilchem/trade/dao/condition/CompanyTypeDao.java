package com.oilchem.trade.dao.condition;

import com.oilchem.trade.domain.condition.CompanyType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
//@RepositoryDefinition(domainClass = CompanyType.class,idClass = Integer.class)
public interface CompanyTypeDao extends PagingAndSortingRepository<CompanyType, Long> {

    CompanyType findByCompanyType(String complanyType);

}
