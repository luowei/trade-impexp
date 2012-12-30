package com.oilchem.trade.domain.condition;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-14
 * Time: 下午7:23
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_product")
public class Product  extends IdEntity implements Serializable {

    @Column(name = "product_name")
     private String productName;
        @Column(name = "product_code")
    private String productCode;
    @Column(name = "type_code")
    private Integer typeCode;
    @Column(name = "product_type")
    private String productType;

    public Product() {
    }

    public Product(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
