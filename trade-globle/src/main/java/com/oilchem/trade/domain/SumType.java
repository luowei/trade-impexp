package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午12:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_sum_type")
public class SumType extends IdEntity implements Serializable {
    @Column(name = "product_type")
    private String sumType;

    public SumType() {
    }

    public SumType(String sumType) {
        this.sumType = sumType;
    }

    public String getSumType() {
        return sumType;
    }

    public void setSumType(String sumType) {
        this.sumType = sumType;
    }
}
