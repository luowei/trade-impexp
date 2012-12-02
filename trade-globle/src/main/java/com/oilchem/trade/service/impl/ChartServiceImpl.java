package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.ChartService;
import ofc4j.model.axis.Label;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.oilchem.trade.bean.DocBean.Config.yearmonth_split;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

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
     *
     * @param yearMonthDto
     * @return
     */
    public List<Label> getYearMonthLabels(YearMonthDto yearMonthDto) {

        Integer highYear = null;
        Integer highMonth = null;
        Integer lowYear = null;
        Integer lowMonth = null;

        //默认为近一年的统计
        if (yearMonthDto.getLowYear() == null || yearMonthDto.getHighYear() == null) {
            Calendar calendar = Calendar.getInstance();
            highYear = calendar.get((YEAR));
            highMonth = calendar.get(MONTH) + 1 < 10 ? calendar.get(MONTH) + 1 : calendar.get(MONTH) + 1;
            lowYear = highYear - 1;
            lowMonth = highMonth;
        } else {
            lowYear = yearMonthDto.getLowYear();
            lowMonth = yearMonthDto.getLowMonth() == null ? 1 : yearMonthDto.getLowMonth();
            highYear = yearMonthDto.getHighYear();
            highMonth = yearMonthDto.getHighMonth() == null ? 1 : yearMonthDto.getHighMonth();

            if (lowYear > highYear) return null;
        }

        List<Label> labelList = new ArrayList<Label>();
        labelList.add(new Label(lowYear + yearmonth_split.value() + (lowMonth < 10 ? "0" + lowMonth : lowMonth)));
        while (lowYear < highYear || (lowYear.equals(highYear) && lowMonth < highMonth-1)) {
            if (lowMonth >= 12) {
                lowYear++;
                lowMonth = 1;
            } else {
                ++lowMonth;
            }
            labelList.add(new Label(lowYear + yearmonth_split.value() + (lowMonth < 10 ? "0" + lowMonth : lowMonth)));

        }
        return labelList;
    }

}
