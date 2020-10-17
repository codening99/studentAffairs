/*导航栏对象*/
const Boot = {
    /*注册*/
    register: function () {
        /*注册点击事件*/
        $(".hotkey, .common").click(function () {
            const name = $(this).data("name");
            if (name === "exit"){
                if (confirm("是否退出当前用户？")){
                    NavigationBar.exit()
                } else {
                    return
                }
            }
            $(".content-body").css("display", "none")
            $(".hotkey, .common").attr("class", "common")
            $(this).attr("class", "hotkey")
            switch (name) {
                case "info": $("#info").css("display", "block"); break;
                case "modify": $("#modify").css("display", "block"); break;
                case "direction": $("#direction").css("display", "block"); break;
                case "list": $("#list").css("display", "block"); break;
            }
        })
    },
    /*检测登录状态*/
    onlineStatus: function () {
        $.post("./login", {action: "getOnlineStatus"}, function (data) {
            const json = $.parseJSON(data)
            if (json.event !== 1){
                window.location.href = "./index.html"
            }
        })
    }
}
const NavigationBar = {
    exit: function (){
        $.post("./login", {action: "logout"}, function () {
            window.location.href = "./index.html"
        })
    }
}

$(function () {
    Boot.onlineStatus()
    Boot.register()
})