package com.oilchem.common.view.controller;

//import com.oilchem.common.doc.Constants;
//import com.oilchem.common.util.CookieUtils;
//import com.oilchem.common.util.UrlBuilder;
//import com.oilchem.common.view.dto.BaseCommand;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;

/**
 * @className:BaseController
 * @classDescription:
 * @author:luowei
 * @createTime:12-10-19
 */
public abstract class BaseController {
//    protected Logger logger = (Logger) LoggerFactory.getLogger(BaseController.class);
//
//    @ModelAttribute
//    public BaseCommand createBaseCommand() {
//        return new BaseCommand();
//    }
//
//    /**
//     * 获取page页码
//     *
//     * @param pageStr
//     * @return
//     */
//    protected int getPageNoFromString(String pageStr) {
//        int page = 1;
//
//        if (StringUtils.isNumeric(pageStr)) {
//            page = Integer.parseInt(pageStr);
//        }
//
//        return page;
//    }
//
//
//    protected int getPageNo(long totalElements, int pageSize, Model model, String pageStr) {
//        int totalPages = (int) (totalElements + pageSize - 1) / pageSize;
//        int page = getPageNoFromString(pageStr);
//        if (model.containsAttribute(Constants.ADD_FLAG)) {
//            page = totalPages;
//        } else {
//            page = Math.min(totalPages, page);
//        }
//        return page;
//    }
//
//
//    /**
//     * 重定向时添加消息
//     *
//     * @param redirectAttrs
//     * @param messageText
//     */
//    protected void addRedirectMessage(RedirectAttributes redirectAttrs, String messageText) {
//        redirectAttrs.addFlashAttribute("message", messageText);
//    }
//
//
//    /**
//     * 重定向时添加错误消息
//     *
//     * @param redirectAttrs
//     * @param messageText
//     */
//    protected void addRedirectError(RedirectAttributes redirectAttrs, String messageText) {
//        redirectAttrs.addFlashAttribute("error", messageText);
//    }
//
//
//    /**
//     * 获得重定向url
//     *
//     * @param redirectUrl
//     * @return
//     */
//    protected String getRedirectUrl(String redirectUrl) {
//        String path = "/";
//        if (redirectUrl != null) {
//            path = UriComponentsBuilder.fromUriString(redirectUrl).build().encode().toUriString();
//        }
//        return "redirect:" + path;
//    }
//
//
//    /**
//     * 添加条件到cookie中
//     *
//     * @param request
//     * @param response
//     * @param page
//     * @param conditionUrl
//     */
//    protected void addRediectUrlCookie(HttpServletRequest request, HttpServletResponse response, int page, String conditionUrl) {
//        String redirectUrl = UrlBuilder.getUrl(conditionUrl, Constants.PAGE, page);
//
//        String key = Constants.REDIRECT_URL;
//        if (CookieUtils.findCookie(request, key) != null) {
//            CookieUtils.deleteCookie(response, key);
//        }
//        CookieUtils.addCookie(response, key, redirectUrl);
//    }
//
//
//    /**
//     * 添加成功消息
//     *
//     * @param redirectAttrs
//     */
//    protected void addSaveSuccessMessage(RedirectAttributes redirectAttrs) {
//        addRedirectMessage(redirectAttrs, "添加成功");
//    }
//
//    /**
//     * 更新成功消息
//     *
//     * @param redirectAttrs
//     */
//    protected void addUpdateSuccessMessage(RedirectAttributes redirectAttrs) {
//        addRedirectMessage(redirectAttrs, "更新成功");
//    }
//
//
//    /**
//     * 删除成功消息
//     *
//     * @param redirectAttrs
//     */
//    protected void addDeleteSuccessMessage(RedirectAttributes redirectAttrs) {
//        addRedirectMessage(redirectAttrs, "删除成功");
//    }
//
//    /**
//     * print String data
//     *
//     * @param request  request
//     * @param response response
//     * @author wei.luo
//     * @createTime 2012-3-26
//     */
//    public void printOutJson(HttpServletRequest request, HttpServletResponse response, String str) {
//        response.setContentType("text/html; charset=UTF-8");
//        PrintWriter out = null;
//        try {
//            out = response.getWriter();
//            out.print(str);
//            out.flush();
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e.getStackTrace());
//        } finally {
//            if (null != out) {
//                out.close();
//            }
//        }
//    }

}

