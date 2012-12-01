package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.ChartService;
import ofc4j.model.axis.Label;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.oilchem.trade.bean.DocBean.Config.yearmonth_split;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-1
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ChartServiceImpl implements ChartService {

    /**
     * 获得年月的label
     * @param yearMonthDto
     * @return
     */
    public List<Label> getYearMonthLabels(YearMonthDto yearMonthDto) {

        if (yearMonthDto.getLowYear() == null || yearMonthDto.getLowMonth() == null)
            return null;

        Integer lowYear = yearMonthDto.getLowYear();
        Integer lowMonth = yearMonthDto.getLowMonth() == null ? 1 : yearMonthDto.getLowMonth();
        Integer highYear = yearMonthDto.getHighYear();
        Integer highMonth = yearMonthDto.getHighMonth() == null ? 1 : yearMonthDto.getHighMonth();

        if (lowYear > highYear) return null;

        List<Label> labelList = new ArrayList<Label>();
        while (lowYear < highYear || (lowYear == highYear && lowMonth <= highMonth)) {
            labelList.add(new Label(lowYear + yearmonth_split.value() + (lowMonth < 10 ? "0" + lowMonth : lowMonth)));
            if (++lowMonth > 12) {
                lowMonth = 1;
                lowYear++;
            }

        }
        return labelList;
    }

}
