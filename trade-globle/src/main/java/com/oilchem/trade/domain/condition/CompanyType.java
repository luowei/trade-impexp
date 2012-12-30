package com.oilchem.trade.domain.condition;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 企业类型
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午12:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_company_type")
public class CompanyType extends IdEntity implements Serializable {
    @Column(name = "company_type")
    private String companyType;

    public CompanyType() {
    }

    public CompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }
}
