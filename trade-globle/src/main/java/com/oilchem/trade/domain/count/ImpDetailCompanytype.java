package com.oilchem.trade.domain.count;

import com.oilchem.trade.domain.abstrac.DetailCount;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-13
 * Time: 下午6:18
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "t_import_detail_tradetype", schema = "dbo", catalog = "lzdb")
@Entity
public class ImpDetailCompanytype extends DetailCount {

    @Column(name="company_type")
    private String companyType;

    public ImpDetailCompanytype() {
    }

    public ImpDetailCompanytype(DetailCount detailCount){

        this.setProductCode(detailCount.getYearMonth())
                .setProductCode(detailCount.getProductCode())
                .setProductName(detailCount.getProductName())
                .setNum(detailCount.getNum())
                .setMoney(detailCount.getMoney())
                .setUnit(detailCount.getUnit())
                .setUnitPrice(detailCount.getUnitPrice());

    }

    public String getCompanyType() {
        return companyType;
    }

    public ImpDetailCompanytype setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }
}
