var TT = TAOTAO = {
    checkLogin: function () {
        var _ticket = $.cookie("TT_TOKEN");
        if (!_ticket) {
            alert("没有token，应该跳转到登录界面去登录");
            return;
        }
        $.ajax({
            url: "http://localhost:8088/user/token/" + _ticket,
            dataType: "jsonp",
            type: "GET",
            success: function (data) {
                if (data.status == 200) {
                    var username = data.data.username;
                    var html = username + "，欢迎来到淘淘！<a href=\"javascript:void()\" class=\"link-logout\"  onclick=\"logout()\" >[退出]</a>";
                    $("#loginbar").html(html);
                } else {
                    alert("登录过期或没登录，应该跳转到登录界面去登录");
                }
            }
        });


    }
}

$(function () {
    // 查看是否已经登录，如果已经登录查询登录信息
    TT.checkLogin();
});

function logout() {
    var _ticket = $.cookie("TT_TOKEN");
    if (!_ticket) {
        return;
    }
    $.ajax({
        url: "http://localhost:8088/user/logout/" + _ticket,
        dataType: "jsonp",
        type: "GET",
        success: function (data) {
            if (data.status == 200) {
                location = "http://localhost:8082";
            }
        }
    });

}

