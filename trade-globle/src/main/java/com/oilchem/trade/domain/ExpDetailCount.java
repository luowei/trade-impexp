package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.DetailCount;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
@Entity
//@Embeddable
//@Table(name = "v_export_detail_count")
@Table(name = "t_export_detail_count")
public class ExpDetailCount extends DetailCount {

    public ExpDetailCount() {
    }

    public ExpDetailCount(DetailCount detailCount){

        this.setProductCode(detailCount.getYearMonth())
                .setProductCode(detailCount.getProductCode())
                .setProductName(detailCount.getProductName())
                .setNum(detailCount.getNum())
                .setMoney(detailCount.getMoney())
                .setUnit(detailCount.getUnit())
                .setUnitPrice(detailCount.getUnitPrice());

    }
}
