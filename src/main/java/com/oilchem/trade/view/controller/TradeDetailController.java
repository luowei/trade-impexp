package com.oilchem.trade.view.controller;

import com.oilchem.trade.domain.*;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.view.dto.CommonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-8
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage/trade")
public class TradeDetailController extends CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    TradeDetailService tradeDetailService;

    @ModelAttribute
    public CommonDto  createCommonDto(){
        return new CommonDto();
    }

    @RequestMapping("/listimpdetail")
    public String listImpTradeDetail(Model model,Integer pageNumber,
                                     ImpTradeDetail impTradeDetail){

        Page<ImpTradeDetail> tradeDetails = tradeDetailService
                .findWithCriteria(impTradeDetail,pageNumber);

        getDetailCriteriaData(model).addAttribute("tradeDetailList",tradeDetails);
        return "listdetail";
    }



    @RequestMapping("/listexpdetail")
    public String listexpTradeDetail(Model model,Integer pageNumber,
                                     ExpTradeDetail expTradeDetail){

        Page<ImpTradeDetail> tradeDetails = tradeDetailService
                .findWithCriteria(expTradeDetail,pageNumber);
        getDetailCriteriaData(model).addAttribute("tradeDetailList",tradeDetails);
        return "listdetail";
    }

    @RequestMapping("/importdetail")
    public String importTradeDetail(DefaultMultipartHttpServletRequest request,
                                    Integer impExpType,Integer year,Integer month){

        MultipartFile file=request.getFile("file");

        return "importdetail";
    }

    private Model getDetailCriteriaData(Model model){

        List<City> cityList = commonService.getModelList(City.class);
        List<ComplanyType> complanyTypeList = commonService.getModelList(ComplanyType.class);
        List<Country> countryList = commonService.getModelList(Country.class);
        List<Customs> customsList = commonService.getModelList(Customs.class);
        List<TradeType> tradeTypeList = commonService.getModelList(TradeType.class);
        List<Transportation> transportationList = commonService.getModelList(Transportation.class);

        model.addAttribute(cityList)
                .addAttribute(complanyTypeList)
                .addAttribute(countryList)
                .addAttribute(customsList)
                .addAttribute(tradeTypeList)
                .addAttribute(transportationList);

        return model;
    }



}
