<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script type="text/javascript">
    //导出数据
    $(function () {
        // Dialog
        $('#dialog').dialog({
            autoOpen: false,
            width: 600,
            buttons: {
                "确定": function () {

                    var pageSize = $('#expPageSize').val() == '' ? parseInt(${pageSize}) : parseInt($('#expPageSize').val())
                    var pageNumber = $('#expPageNumber').val() == '' ? 1 : parseInt($('#expPageNumber').val())

                    if (pageSize < 1 || pageNumber < 1) {
                        alert('请输入合法的页数 或 页大小')
                        return
                    }
                    if (pageNumber > parseInt(${totalElements}) / pageSize + 1) {
                        alert("输入的页大于总页数")
                        return
                    }

                    $('#expForm').attr("target", "_blank").attr("action",$('#expAction').val()).submit()
                    $(this).dialog("close");
                },
                "取消": function () {
                    $(this).dialog("close");
                }
            }
        });

        // Dialog Link
        $('#dialog_link').click(function () {

            var impType = $('input[name=impType]').val()==null?"": $('input[name=impType]').val()
            var lowYear = $('select[name=lowYear]').val()==null?"": $('select[name=lowYear]').val()
            var lowMonth = $('select[name=lowMonth]').val()==null?"":$('select[name=lowMonth]').val()
            var highYear = $('select[name=highYear]').val()==null?"": $('select[name=highYear]').val()
            var highMonth = $('select[name=highMonth]').val()==null?"":$('select[name=highMonth]').val()
            var month = $('select[name=month]').val()==null?"":$('select[name=month]').val()
            var productName = $('#productName').val()==null?"":$('#productName').val()
            var impExpType = $('select[name=impExpType]').val()==null?"0":$('select[name=impExpType]').val()

            var info = new Object();
            info.impType = impType
            info.lowYear = parseInt(lowYear)
            info.lowMonth = parseInt(lowMonth)
            info.highYear = parseInt(highYear)
            info.highMonth = parseInt(highMonth)
            info.month = parseInt(month)
            info.productName = productName
            if (impExpType == '0') {
                info.impExpType = "进口"
            }
            if (impExpType == '1') {
                info.impExpType = "出口"
            }

            var title = '导出 ' +
                    lowYear + '-' +
                    lowMonth + " ~ " +
                    highYear + '-' +
                    highMonth + " " +
                    month + " " +
                    productName + " " +
                    info.impExpType + "数据"

            var infoJson = jQuery.toJSON(info)

            $('#info').text(title)
            $('#param').val(infoJson)

            $('#expForm').attr("target", "_blank").attr("action",$('#expAction').val())

            $('#dialog').dialog('open');
            return false;
        });
    });

</script>


<!-- ui-dialog -->
<div id="dialog" title="导出数据">
    <form id="expForm" name="expForm" modelAttribute="detailCount"
               action=""  method="post" cssClass="well form-inline">
        <input id="param" type="hidden" name="param" value="">

        <p align="center"><b id="info"></b></p>

        <p align="center">总记录数:${totalElements}&nbsp;&nbsp;默认页大小:${pageSize}&nbsp;总页数:${totalPages}</p>

        <p align="center">

        <div class="input-prepend" >
            <span class="add-on">
            <label class="lable"><input type="radio" checked="checked" name="impType" value="notAll">
                <span>导出指定页</span></label>&nbsp;&nbsp; </span>
            <span class="add-on">页大小:</span>
            <input id="expPageSize" name="expPageSize" type="text" style="width: 80px;margin-top: 5px;">
            <span class="add-on">第几页:</span>
            <input id="expPageNumber" name="expPageNumber" type="text" style="width: 80px;margin-top: 5px;">
        </div>

        <br/><br/>

        <div class="input-prepend">
            <span class="add-on">
            <label class="lable"><input type="radio" name="impType" value="all">
                <span>导出全部</span></label>
            </span>
        </div>

        </p>

    </form>
</div>