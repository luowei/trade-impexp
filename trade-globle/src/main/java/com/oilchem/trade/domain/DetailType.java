package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-5
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_detail_type")
public class DetailType extends IdEntity implements Serializable {

    @Column(name = "code")
    private Integer type_code;

    @Column(name = "detail_type")
    private String detailType;

    public DetailType() {
    }

    public DetailType(Integer type_code, String detailType) {
        this.type_code = type_code;
        this.detailType = detailType;
    }

    public Integer getType_code() {
        return type_code;
    }

    public void setType_code(Integer type_code) {
        this.type_code = type_code;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }
}
