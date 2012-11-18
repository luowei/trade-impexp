package com.oilchem.trade.view.controller;

import com.oilchem.trade.domain.Log;
import com.oilchem.trade.service.LogService;
import com.oilchem.trade.bean.CommonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class LogController extends CommonController {

    @Autowired
    LogService logService;

    @RequestMapping("/listlog")
    public String listLog(Model model,CommonDto commonDto){

        Page<Log> logs = logService.findAll(getPageRequest(commonDto));
        addPageInfo(model,logs,getServletContextPath("/listlog"))
            .addAttribute("logList",logs);

        return "manage/trade/listlog";
    }

}
