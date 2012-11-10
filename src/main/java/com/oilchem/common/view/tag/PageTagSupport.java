package com.oilchem.common.view.tag;


//import com.oilchem.common.bean.DataTable;
//import com.oilchem.common.doc.Constants;
//import com.oilchem.common.util.UrlBuilder;
//
//import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @className:PageTagSupport
 * @classDescription:
 * @author:luowei
 * @createTime:12-10-13
 */
public class PageTagSupport extends SimpleTagSupport {
//    private DataTable dataTable;    //分页数据载体
//    private String formId;          //表单的id
//    private String name;            //表单中page的名称,默认是page
//
//
//    public DataTable getDataTable() {
//        return dataTable;
//    }
//
//    public void setDataTable(DataTable dataTable) {
//        this.dataTable = dataTable;
//    }
//
//
//    public String getFormId() {
//        return formId;
//    }
//
//    public void setFormId(String formId) {
//        this.formId = formId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public void doTag() throws JspException, IOException {
//
////        PageContext context = (PageContext)getJspContext();
////        String contextPath = context.getServletContext().getContextPath();
//
//        StringBuilder sb = new StringBuilder();
//
//
//        if (dataTable.getTotalPages() > 1) {
//            sb.append("<div class=\"pagination\">\n\t");
//
//            if (dataTable.isHasPreviousPage()) {
//                sb.append(getLiAndA("previous", dataTable.getPrevious(), null, "上一页", false));
//            } else {
//                sb.append(getLiAndA("disabled", dataTable.getPrevious(), null, "上一页", true));
//            }
//
//            sb.append("\n");
//
//            sb.append(getPageNoListString(dataTable.getStartIndex(),
//                    dataTable.getEndIndex()));
//
//            if (dataTable.isHasNextPage()) {
//                sb.append(getLiAndA("next", dataTable.getNext(), null, "下一页", false));
//            } else {
//                sb.append(getLiAndA("disabled", dataTable.getNext(), null, "下一页", true));
//            }
//
//            sb.append("\n</div>");
//        }
//
//
//        sb.append("<p class=\"pageInfo\">")
//                .append("当前第 ").append(dataTable.getCurrentPage()).append(" 页，")
//                .append("共 ").append(dataTable.getTotalElements()).append(" 条记录，")
//                .append("每页显示 ").append(dataTable.getPageSize()).append(" 条，")
//                .append("共 ").append(dataTable.getTotalPages()).append(" 页\n")
//                .append("</p>");
//
//
//        JspWriter out = getJspContext().getOut();
//
//        out.println(sb.toString());
//
//        out.flush();
//
//    }
//
//
//    /**
//     * <a href="/commons/student/list?searchKey1=xx&page=1" class="button">上一页</a>
//     *
//     * @param page
//     * @return
//     */
////    private String getPreviousLinkString(int page) {
////
////        return getLinkString(page, "上一页", "button");
////    }
//
//    /**
//     * <a href="/commons/student/list?searchKey1=xx&page=2" class="button">下一页</a>
//     *
//     * @param page
//     * @return
//     */
////    private String getNextLinkString(int page) {
////
////        return getLinkString(page, "下一页", "button");
////    }
//    private String getLinkString(long page, String text, String cssClass, boolean isCurrentPage) {
//
//        StringBuilder sb = new StringBuilder();
//
//        String conditionUrl = dataTable.getConditionUrl();
//
//        String paramName = (null == name ? Constants.PAGE : name);
//
//        String linkString = UrlBuilder.getUrl(conditionUrl, paramName, page);
//
//        sb.append("<a href=\"");
//
//        if (isCurrentPage) {
//            sb.append("#");
//        } else {
//            sb.append(linkString);
//        }
//
//        sb.append("\"");
//
//
//        if (cssClass != null && !"".equals(cssClass)) {
//            sb.append(" class=\"").append(cssClass).append("\"");
//        }
//
//        sb.append(">");
//
//        sb.append(text).append("</a>");
//
//        return sb.toString();
//    }
//
//
//    /**
//     * @param startIndex
//     * @param endIndex
//     * @return
//     */
//    private String getPageNoListString(long startIndex, long endIndex) {
//
//        if (startIndex == endIndex) {
//            return "";
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        for (long i = startIndex; i <= endIndex; i++) {
//            if (i == dataTable.getCurrentPage()) {
//                sb.append(getLiAndA("active", i, null, i + "", true));
//            } else {
//                sb.append(getStartPartOfLiString(null))
//                        .append(getLinkString(i, i + "", null, false))
//                        .append("</li>\n");
//            }
//        }
//
//        return sb.toString();
//
//    }
//
//    private String getStartPartOfLiString(String cssClass) {
//        if (cssClass != null && !"".equals(cssClass)) {
//            return "<li class=\"" + cssClass + "\">";
//        }
//
//        return "<li>";
//    }
//
//
//    private String getEndPartOfLiString() {
//
//        return "</li>";
//    }
//
//    /**
//     * <pre><span class="">2</span></pre>
//     *
//     * @param page
//     * @param cssClass
//     * @return
//     */
//    private String getLiString(long page, String cssClass) {
//
//        return getStartPartOfLiString(cssClass) + page + "</li>\n";
//    }
//
//
//    /**
//     * <li class=""><a href="" class="">linkText</a></li>
//     *
//     * @param liClass
//     * @param page
//     * @param linkClass
//     * @param linkText
//     * @return
//     */
//    private String getLiAndA(String liClass, long page, String linkClass, String linkText, boolean isCurrentPage) {
//
//        StringBuilder sb = new StringBuilder();
//
//        sb.append(getStartPartOfLiString(liClass));
//        sb.append(getLinkString(page, linkText, linkClass, isCurrentPage));
//        sb.append(getEndPartOfLiString());
//
//        return sb.toString();
//
//    }


}

