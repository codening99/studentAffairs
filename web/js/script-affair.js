/*导航栏对象*/
const NavigationBar = {
    /*注册*/
    register: function () {
        /*注册点击事件*/
        $(".hotkey, .common").click(function () {
            $(".hotkey, .common").attr("class", "common")
            $(this).attr("class", "hotkey")
            $(".content-body").css("display", "none")
            const name = $(this).data("name");
            switch (name) {
                case "info": $("#info").css("display", "block"); break;
                case "modify": $("#modify").css("display", "block"); break;
                case "direction": $("#direction").css("display", "block"); break;
            }
        })
    }
}

$(function () {

    NavigationBar.register()
})