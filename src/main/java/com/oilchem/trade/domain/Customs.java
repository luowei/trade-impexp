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
 * Time: 下午12:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_customs")
public class Customs extends IdEntity implements Serializable {
    @Column(name = "customs")
    private String customs;

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }
}
