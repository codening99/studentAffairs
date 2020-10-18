/*导航栏对象*/
const Boot = {
    /*注册点击事件*/
    register: function () {
        /*导航栏点击*/
        $(".sub-navigation .hotkey,.sub-navigation .common").click(function () {
            const name = $(this).data("name");
            if (name === "exit"){
                if (confirm("是否退出当前用户？")){
                    NavigationBar.exit()
                } else {
                    return
                }
            }
            $(".content-body").css("display", "none")
            $(".sub-navigation .hotkey, .sub-navigation .common").attr("class", "common")
            $(this).attr("class", "hotkey")
            switch (name) {
                case "info": $("#info").css("display", "block"); break;
                case "modify": $("#modify").css("display", "block"); break;
                case "activity": $("#activity").css("display", "block"); break;
                case "elective": $("#elective").css("display", "block"); break;
                case "direction": $("#direction").css("display", "block"); break;
                case "info-list": $("#list").css("display", "block"); break;
                case "set-direction": $("#set-direction").css("display", "block"); break;
                case "set-elective": $("#set-elective").css("display", "block"); break;
                case "set-activity": $("#set-activity").css("display", "block"); break;
            }
        })
        /*方向设置导航*/
        $(".content-direction .hotkey,.content-direction .common").click(function () {
            $(".content-direction-body").css("display", "none")
            $(".content-direction .hotkey, .content-direction .common").attr("class", "common")
            $(this).attr("class", "hotkey")
            const name = $(this).data("name");
            switch (name) {
                case "direction-set": $("#direction-set").css("display", "block"); break;
            }
        })
    },
    /*检测登录状态*/
    onlineStatus: function () {
        $.post("./login", {action: "getOnlineStatus"}, function (data) {
            const json = $.parseJSON(data)
            if (json.event !== 1){
                window.location.href = "./index.html"
            } else {
                if (json.type === 1){
                    Boot.teacher_init()
                } else {
                    Boot.student_init()
                }
            }
        })
    },
    /*学生事务中心初始化*/
    student_init: function () {
        $("title").html("学生事务中心")
        $(".title img").attr("src", "img/logo2.png")
        $("#sub-navigation-student").css("display", "block")
        $("#sub-navigation-teacher").css("display", "none")
        $("#info").css("display", "block")
    },
    teacher_init:function () {
        $("title").html("教师事务中心")
        $(".title img").attr("src", "img/logo3.png")
        $("#sub-navigation-student").css("display", "none")
        $("#sub-navigation-teacher").css("display", "block")
        $("#list").css("display", "block")

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