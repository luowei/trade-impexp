package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 运输出方式
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午12:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_transportation")
public class Transportation extends IdEntity implements Serializable {
    @Column(name = "transportation")
    private String transportation;

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }
}
