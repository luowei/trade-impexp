package com.oilchem.trade.domain.abstrac;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class IdEntity extends AbstractPersistable<Long> implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Integer id;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
}
