package com.oilchem.trade.view.controller;

import com.oilchem.common.view.controller.BaseController;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class TradeSumController extends BaseController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/listimpsum")
    public String listTradeImpSum(Model model,ImpTradeSum tradeSum){


        return "listsum";
    }

    @RequestMapping("/listexpsum")
    public String listTradeExpSum(Model model,ExpTradeSum tradeSum){

        return "listsum";
    }

    @RequestMapping("/importsum")
    public String importTradeSum(){

        return "importsum";
    }

    private Model getDetailCriteriaData(Model model){
        List<ProductType> productTypeList = commonService.getModelList(ProductType.class);
        model.addAttribute(productTypeList);
        return model;
    }



}
