/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-13
 * Time: 上午9:01
 * To change this template use File | Settings | File Templates.
 */

var m_strTextselectDiv = "Textselectshow_Div"
var m_intTextSelectIn = false
var ie = (document.getElementById && document.all);

for (var IDx = 0, IDy = ''; document.getElementById(m_strTextselectDiv) != null; IDx++, IDy = IDx) {
    m_strTextselectDiv = (document.getElementById(m_strTextselectDiv + IDy) == null) ? m_strTextselectDiv + IDy : m_strTextselectDiv
}
document.write('<div id="' + m_strTextselectDiv + '" style="position: absolute;cursor: default;border: 1px solid #B2B2B2;background-color: #fff;display: none;"></div>')
// 获取对象的坐标
function getPosition(Obj) {
    try {
        for (var sumTop = 0, sumLeft = 0; Obj != window.document.body; sumTop += Obj.offsetTop, sumLeft += Obj.offsetLeft, Obj = Obj.offsetParent);
        return {left:sumLeft, top:sumTop}
    } catch (e) {
    }
}
//处理Div中的选项/* 某个选项，输入框的ID号 */
function divOnmoveover(obj, objText) {
    var MM_objText = document.getElementById(objText)

    var objChilddiv = obj.parentNode.getElementsByTagName("div")
    for (var x = 0; x < objChilddiv.length; x++) {
        objChilddiv[x].style.cssText = ''
    }
    obj.style.cssText = 'background-color: #330066;color: #ffffff;'

    obj.onclick = function () {
        m_intTextSelectIn = false
        if (ie) {
            MM_objText.value = obj.outerText
        }
        else {
            MM_objText.value = obj.textContent
        }
        MM_objText.focus()
        MM_objText.blur()
    }
}

function showSelect(obj, A_seleObj) {
    var ie = (document.getElementById && document.all);
    var objDiv = document.getElementById(m_strTextselectDiv)
    var seleObj = document.getElementById(A_seleObj)

    //循环取名，避免取了个重复的ID号
    for (var IDx = 0, IDy = ''; obj.id == ''; IDx++, IDy = IDx) {
        obj.id = (document.getElementById("textSelect" + IDy) == null) ? "textSelect" + IDy : ''
    }
    objDiv.style.left = getPosition(obj).left
    objDiv.style.top = getPosition(obj).top + obj.offsetHeight
    objDiv.style.width = obj.offsetWidth
    objDiv.style.height = '';
    objDiv.style.overflowY = '';
    objDiv.innerHTML = ''
    //读取select的项目放到Div里。
    for (var x = 0; x < seleObj.options.length; x++) {
        objDiv.innerHTML += "<div onmouseover=\"divOnmoveover(this,'" + obj.id + "')\" style='width:100%;" +
            "white-space: nowrap;cursor: default;'>" + seleObj.options[x].text + "</div>"
    }
    //调整Div高度，过度显示滚动条
    if (x > 8) {
        objDiv.style.height = 100;
        objDiv.style.overflowY = 'auto';
    }
    objDiv.style.display = ''
    if (ie) {
        HideOverSels(objDiv.id)
    }
    objDiv.onmouseover = function () {
        m_intTextSelectIn = true
    }
    objDiv.onmouseout = function () {
        m_intTextSelectIn = false;
        obj.focus();
    }

    obj.onclick = function () {
        showSelect(obj, A_seleObj);
        obj.onkeyup();
    }
    //自动匹配选项中符合条件的记录
    obj.onkeyup = function () {
        if (obj.value == '') {
            return false
        }
        var objChilddiv = objDiv.getElementsByTagName("div")
        for (var x = 0; x < objChilddiv.length; x++) {
            objChilddiv[x].style.cssText = ''
        }
        for (var x = 0; x < objChilddiv.length; x++) {
            var strChilddiv = (ie) ? objChilddiv[x].outerText : obj.textContent
            if (strChilddiv.substr(0, obj.value.length) == obj.value) {
                objDiv.scrollTop = objChilddiv[x].offsetHeight * x
                objChilddiv[x].style.cssText = 'background-color: #330066;color: #ffffff;'
                return true
            }
        }
    }
    obj.onblur = function () {
        if (!m_intTextSelectIn) {
            objDiv.style.display = 'none'
        }
        ;
        if (ie) {
            HideOverSels(objDiv.id)
        }
    }
}

// 隐藏被ID为objID的对象（层）遮挡的所有select
function HideOverSels(objID) {
    var sels = document.getElementsByTagName('select');
    for (var i = 0; i < sels.length; i++)
        if (Obj1OverObj2(document.getElementById(objID), sels[i]))
            sels[i].style.visibility = 'hidden';
        else
            sels[i].style.visibility = 'visible';
}

//判断obj1是否遮挡了obj2
function Obj1OverObj2(obj1, obj2) {
    var pos1 = getPosition(obj1)
    var pos2 = getPosition(obj2)
    var result = true;
    var obj1Left = pos1.left - window.document.body.scrollLeft;
    var obj1Top = pos1.top - window.document.body.scrollTop;
    var obj1Right = obj1Left + obj1.offsetWidth;
    var obj1Bottom = obj1Top + obj1.offsetHeight;
    var obj2Left = pos2.left - window.document.body.scrollLeft;
    var obj2Top = pos2.top - window.document.body.scrollTop;
    var obj2Right = obj2Left + obj2.offsetWidth;
    var obj2Bottom = obj2Top + obj2.offsetHeight;

    if (obj1Right <= obj2Left || obj1Bottom <= obj2Top ||
        obj1Left >= obj2Right || obj1Top >= obj2Bottom)
        result = false;
    return result;
}
