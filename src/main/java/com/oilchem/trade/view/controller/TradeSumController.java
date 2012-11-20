package com.oilchem.trade.view.controller;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.Message;
import com.oilchem.trade.dao.ProductTypeDao;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-8
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class TradeSumController extends CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    TradeSumService tradeSumService;

    @Autowired
    TaskService taskService;

    /**
     * 获得进口总表的一页数据
     *
     * @param model     model
     * @param tradeSum  tradeSum
     * @param commonDto commonDto
     * @return
     */
    @RequestMapping("/listsum/{pageNumber}")
    public String listTradeImpSum(Model model, TradeSum tradeSum,
                                  @PathVariable Integer pageNumber,
                                  Integer impExp, CommonDto commonDto) {
        if(impExp==null)
            impExp=0;

        if (impExp.equals(Message.ImpExpType.进口.getCode())) {
            Page<ImpTradeSum> impTradeSums = tradeSumService
                    .findImpWithCriteria(new ImpTradeSum(tradeSum), commonDto, getPageRequest(commonDto));
            findAllIdEntity(addPageInfo(model, impTradeSums, "/manage/listsum"),
                    ProductTypeDao.class, ProductType.class.getSimpleName())
                    .addAttribute("tradeSumList", impTradeSums);
        }
        if (impExp.equals(Message.ImpExpType.出口.getCode())) {
            Page<ExpTradeSum> expTradeSums = tradeSumService
                    .findExpWithCriteria(new ExpTradeSum(tradeSum), commonDto, getPageRequest(commonDto));
            findAllIdEntity(addPageInfo(model, expTradeSums, "/manage/listsum"),
                    ProductTypeDao.class, ProductType.class.getSimpleName())
                    .addAttribute("tradeSumList", expTradeSums);
        }

        return "manage/trade/listsum";
    }

    /**
     * 导入总表
     *
     * @return
     */
    @RequestMapping("/importsum")
    public String importTradeSum(@RequestParam("file") MultipartFile file, String productType,
                                 Integer impExpType, Model model,
                                 YearMonthDto yearMonthDto) {
        Boolean validate = (file.getOriginalFilename().endsWith(".rar") ||
                file.getOriginalFilename().endsWith(".zip")) && yearMonthDto != null;
        if (!validate) return "manage/trade/import";

        String uploadUrl = null;
        StringBuffer message = new StringBuffer();
        try {
            uploadUrl = tradeSumService.uploadFile(file, yearMonthDto);
            message.append("文件已上传到：" + Config.UPLOAD_DETAILZIP_DIR +
                    uploadUrl.substring(uploadUrl.lastIndexOf("/")));
            taskService.unSumPackageAndImportTask(yearMonthDto);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.append("<br/>文件上传或数据导入发生了错误");
        }

        model.addAttribute("message",message.toString());

        return "forward:/manage/import";
    }


}
