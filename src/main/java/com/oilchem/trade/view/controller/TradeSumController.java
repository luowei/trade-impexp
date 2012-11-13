package com.oilchem.trade.view.controller;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.view.dto.CommonDto;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-8
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage/trade")
public class TradeSumController extends CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    TradeSumService tradeSumService;

    /**
     * 获得进口总表的一页数据
     *
     * @param model     model
     * @param tradeSum  tradeSum
     * @param commonDto commonDto
     * @return
     */
    @RequestMapping("/listimpsum")
    public String listTradeImpSum(Model model, ImpTradeSum tradeSum, CommonDto commonDto) {

        Page<ImpTradeSum> tradeSums = tradeSumService
                .findWithCriteria(tradeSum, commonDto, getPageRequest(commonDto));

        getDetailCriteriaData(addPageInfo(model, tradeSums, getServletContextPath("/listimpsum")))
                .addAttribute("tradeSumList", tradeSums);

        return "manage/trade/listsum";
    }

    /**
     * 获得出口总表的一页数据
     *
     * @param model     model
     * @param tradeSum  tradeSum
     * @param commonDto commonDto
     * @return
     */
    @RequestMapping("/listexpsum")
    public String listTradeExpSum(Model model, ExpTradeSum tradeSum, CommonDto commonDto) {

        Page<ExpTradeSum> tradeSums = tradeSumService
                .findWithCriteria(tradeSum, commonDto, getPageRequest(commonDto));

        getDetailCriteriaData(addPageInfo(model, tradeSums, getServletContextPath("/listimpsum")))
                .addAttribute("tradeSumList", tradeSums);

        return "manage/trade/listsum";
    }

    /**
     * 导入总表
     *
     * @return
     */
    @RequestMapping("/importsum")
    public String importTradeSum(@RequestParam("file") MultipartFile file, String productType,
                                 Integer impExpType,Model model,
                                 YearMonthDto yearMonthDto) {
        Boolean validate = (file.getOriginalFilename().endsWith(".rar") ||
                file.getOriginalFilename().endsWith(".zip")) && yearMonthDto!=null;
        if (!validate) return  "manage/trade/import";

        String uploadUrl = tradeSumService.uploadFile(file, yearMonthDto);


        model.addAttribute("message", Config.UPLOAD_SUMZIP_DIR+uploadUrl.substring(uploadUrl.lastIndexOf("/")));

        return "manage/trade/import";
    }

    private Model getDetailCriteriaData(Model model) {
        List<ProductType> productTypeList = commonService.getModelList(ProductType.class);
        model.addAttribute(productTypeList);
        return model;
    }


}
