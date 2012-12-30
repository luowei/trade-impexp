package com.oilchem.trade.view.controller;

import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.service.LogService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    TaskService taskService;

    @RequestMapping("/listlog/{tableType}/{pageNumber}")
    public String listLog(Model model,
                          Log log,
                          CommonDto commonDto) {
        if (commonDto.getPageNumber() == null) {
            commonDto.setPageNumber(1);
        }

        Page<Log> logs = null;
        commonDto.setSort("logTime:desc");
        logs = logService.findAll(log, getPageRequest(commonDto));


        addPageInfo(model, logs, "/manage/listlog/" + log.getTableType())
                .addAttribute("flag", DocBean.Flag.values())
                .addAttribute("logList", logs)
                .addAttribute("tableType",log.getTableType());

        return "manage/trade/listlog";
    }

    /**
     * 重新解包
     *
     * @param log
     * @param commonDto
     * @return
     */
    @RequestMapping("/extract/{tableType}/{pageNumber}/{id}")
    public String extractPackage(Log log, CommonDto commonDto, @PathVariable Long id) {
        if (id != null) {
            commonDto.setId(id);
            log.setId(id);
            taskService.extractPackage(log.getId());
        }

        return "redirect:/manage/listlog/" + log.getTableType()
                + "/" + commonDto.getPageNumber();
    }

    /**
     * 重新导入
     *
     * @param log
     * @param commonDto
     * @return
     */
    @RequestMapping("/import/{tableType}/{pageNumber}/{id}")
    public String importData(Log log, CommonDto commonDto, @PathVariable Long id) {

        if (id != null) {
            commonDto.setId(id);
            log.setId(id);
            taskService.importData(log.getId());
        }

        return "redirect:/manage/listlog/" + log.getTableType()
                + "/" + commonDto.getPageNumber();
    }

    @RequestMapping("/toexcute")
    public String toexcute(){
        return "manage/config/excuteconf";
    }

    @RequestMapping("/excute")
    public String excute(Model model,String excute){
        try {
            taskService.excuteService(excute);
            model.addAttribute("message","任务正在后台执行");
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
        }
        return "manage/config/excuteconf";
    }

    @Autowired
    TradeDetailService tradeDetailService;

    @RequestMapping("/updateDetailType")
    public String updateDetailType(Model model,YearMonthDto yearMonthDto){
        if(yearMonthDto.getImpExpType()==null || yearMonthDto.getMonth()==null
                || yearMonthDto.getYear()==null){
            model.addAttribute("error","请选择年月，或进出口类型");
            return "manage/trade/count/gencount";
        }
        //更新明细产品类型
        tradeDetailService.updateDetailType(yearMonthDto);
        model.addAttribute("message","更新成功");
        return "manage/trade/count/gencount";
    }


}
