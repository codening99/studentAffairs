/*导航栏对象*/
const NavigationBar = {
    /*注册*/
    register: function () {
        /*注册点击事件*/
        $(".hotkey, .common").click(function () {
            $(".hotkey, .common").attr("class", "common")
            $(this).attr("class", "hotkey")
        })
    }
}

$(function () {

    NavigationBar.register()
})