/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-13
 * Time: 上午9:01
 * To change this template use File | Settings | File Templates.
 */

//消息div一定时间后隐藏
$(function(){
    $("div.messagePlace").fadeOut(5000);
});

/**
 * 全选功能
 * @param controlCheckboxId 控制全选checkbox的id
 * @param name checkbox名称
 */
function checkAll(controlCheckboxId, name){
    var $controlCheckbox = $("#" + controlCheckboxId);
    var $element = $("input[name="+name+"]");

    var checkedName = "input[name=" + name + "]:checked";

    $controlCheckbox.click(function () {
        if ($(this).attr("checked") == "checked") {
            $element.attr("checked", "checked");
        } else {
            $element.removeAttr("checked");
        }
    });

    var size = $element.length;

    $element.click(function () {
        var $checkedElement = $(checkedName);

        if (size == $checkedElement.length) {
            $controlCheckbox.attr("checked", "checked");
        }

        if (size > $checkedElement.length) {
            $controlCheckbox.removeAttr("checked");
        }
    });
}

//function changeProductTypeInput() {
//    var selectVal = $("select[name=productType]").val().trim();
//    $("input[name=productType]").val(selectVal);
//}
//
//function checkValue() {
//    var inputVal = $("input[name=productType]").val().trim();
//    var selectVal = $("select[name=productType]").val().trim();
//    if (inputVal != selectVal) {
//        if (selectVal != "") {
//            $("input[name=productType]").val(selectVal);
//        }
//    }
//}
