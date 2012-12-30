package com.oilchem.trade.domain.condition;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午12:29
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_city")
public class City extends IdEntity implements Serializable {
    @Column(name = "city")
    private String city;

    public City() {
    }

    public City(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
