package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.AbstractTradeDetail;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_export_detail")
public class ExpTradeDetail extends AbstractTradeDetail {
}
