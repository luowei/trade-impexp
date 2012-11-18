package com.oilchem.trade.view.controller;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.Message;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
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
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class TradeDetailController extends CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    TradeDetailService tradeDetailService;

    @Autowired
    TaskService taskService;

//    @ModelAttribute
//    public CommonDto  createCommonDto(){
//        return new CommonDto();
//    }

    /**
     * 明细列表
     * @param model
     * @param commonDto
     * @param tradeDetail
     * @param impExp
     * @return
     */
    @RequestMapping("/listdetail")
    public String listexpTradeDetail(Model model ,CommonDto commonDto,
                                     TradeDetail tradeDetail,Integer impExp) {

        if (impExp.equals(Message.ImpExpType.进口.getCode())) {
            Page<ImpTradeDetail> impTradeDetails = tradeDetailService
                    .findImpWithCriteria((ImpTradeDetail)tradeDetail, commonDto, getPageRequest(commonDto));
            getDetailCriteriaData(addPageInfo(model, impTradeDetails, getServletContextPath("/listexpdetail")))
                    .addAttribute("tradeDetailList",impTradeDetails);
        }
        if (impExp.equals(Message.ImpExpType.出口.getCode())) {
            Page<ExpTradeDetail> expTradeDetails = tradeDetailService
                    .findExpWithCriteria((ExpTradeDetail)tradeDetail, commonDto, getPageRequest(commonDto));
            getDetailCriteriaData(addPageInfo(model, expTradeDetails, getServletContextPath("/listexpdetail")))
                    .addAttribute("tradeDetailList",expTradeDetails);
        }

        return "manage/trade/listdetail";
    }

    /**
     * 进入导入数据页面
     * @return
     */
    @RequestMapping("/import")
    public String importpage(Model model){
        model.addAttribute("productTypeList",tradeDetailService.getProductList());
        return "manage/trade/import";
    }

    /**
     * 导入明细数据
     *
     * @param file  从 DefaultMultipartHttpServletRequest获得的file
     * @param yearMonthDto  年月。。。
     * @return
     */
    @RequestMapping("/importdetail")
    public String importTradeDetail( @RequestParam("file") MultipartFile file,
                                    Model model,YearMonthDto yearMonthDto) {

        Boolean validate = (file.getOriginalFilename().endsWith(".rar") ||
                file.getOriginalFilename().endsWith(".zip"))
                && yearMonthDto!=null;
        if(!validate) return "manage/trade/import";

        StringBuffer message = new StringBuffer();
        try{
            String uploadUrl = tradeDetailService.uploadFile(file, yearMonthDto);
            message.append( "文件已上传到："+Config.UPLOAD_DETAILZIP_DIR +
                    uploadUrl.substring(uploadUrl.lastIndexOf("/")));
            taskService.unDetailPackageAndImportTask(yearMonthDto);

        }catch (Exception e){
            logger.error(e.getMessage(),e);
            message.append("<br/>文件上传或数据导入发生了错误");
        }

        model.addAttribute("message",message.toString());
        return "manage/trade/import";
    }

    /**
     * 获得查询条件数据
     *
     * @param model 模型
     * @return
     */
    private Model getDetailCriteriaData(Model model) {

        List<City> cityList = commonService.findAllIdEntityList(City.class);
        List<CompanyType> companyTypeList = commonService.findAllIdEntityList(CompanyType.class);
        List<Country> countryList = commonService.findAllIdEntityList(Country.class);
        List<Customs> customsList = commonService.findAllIdEntityList(Customs.class);
        List<TradeType> tradeTypeList = commonService.findAllIdEntityList(TradeType.class);
        List<Transportation> transportationList = commonService.findAllIdEntityList(Transportation.class);

        model.addAttribute(cityList)
                .addAttribute(companyTypeList)
                .addAttribute(countryList)
                .addAttribute(customsList)
                .addAttribute(tradeTypeList)
                .addAttribute(transportationList);

        return model;
    }


}
