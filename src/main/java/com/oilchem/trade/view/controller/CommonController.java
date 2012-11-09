package com.oilchem.trade.view.controller;

import com.oilchem.trade.domain.abstrac.IdEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-9
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CommonController {


    public <T extends IdEntity> Model addPageInfo(Model model,Page<T> page){

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        Integer totalPages = page.getTotalPages();
        Long totalElements = page.getTotalElements();

        return model==null? model:model.addAttribute("currentIndex",current)
                .addAttribute("beginIndex",begin)
                .addAttribute("endIndex",end)
                .addAttribute("totalPages",totalPages)
                .addAttribute("totalElements",totalElements);
    }

}
