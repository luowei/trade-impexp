package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.AbstractTradeSum;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_import_sum")
public class ImpTradeSum extends AbstractTradeSum {
}
