package com.oilchem.trade.domain;

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
@Table(name = "t_complany_type")
public class ComplanyType extends IdEntity implements Serializable {
    @Column(name = "complany_type")
    private String complanyType;

    public ComplanyType() {
    }

    public ComplanyType(String complanyType) {
        this.complanyType = complanyType;
    }

    public String getComplanyType() {
        return complanyType;
    }

    public void setComplanyType(String complanyType) {
        this.complanyType = complanyType;
    }
}
