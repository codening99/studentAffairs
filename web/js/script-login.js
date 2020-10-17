const Boot = {
    /*注册标签事件*/
    register: function () {
        $("#button-login").click(Boot.login)
        $("#button-login-fast").click(Boot.login_fast)
        $("#a-logout").click(Boot.manual_login)
    },
    /*显示错误内容，*/
    errorShow: function(msg){
        const $error = $("#login-error");
        const $error_msg = $("#login-error-msg");
        if(msg===undefined) {$error.css("display", "none");return ;}
        $error_msg.html(msg);
        $error.css("display", "block");
    },
    /*登录事件*/
    login: function () {
        const account = $("#input-account").val()
        const password = $("#input-password").val()
        if (account === ""){
            Boot.errorShow("账号不能为空！")
            return ;
        }
        if (password === ""){
            Boot.errorShow("密码不能为空！")
            return ;
        }
        const type = $('input:radio[name=type]:checked').val()
        Boot.errorShow()
        $(this).attr('disabled',true);
        Login.login(type, account, password)


    },
    /*检测登录状态*/
    onlineStatus: function () {
        $.post("./login", {action: "getOnlineStatus"}, function (data) {
            const json = $.parseJSON(data)
            if (json.event === 1){
                $("#login-fast").css("display", "block")
                $("#login").css("display", "none")

            }
        })
    },
    /*快速登录事件*/
    login_fast: function () {
        window.location.href = "./affair.html"
    },
    /*切回手动登录*/
    manual_login: function () {
        $("#login-fast").css("display", "none")
        $("#login").css("display", "block")
    }

}
const Login = {
    /*登录*/
    login: function (type, account, password) {
        $.post("./login", {
            action: "login",
            type: type,
            account: account,
            password: password
        }, function (data) {
            const json = $.parseJSON(data)
            if (json.event === 0){
                window.location.href = "./affair.html"
                return
            }
            Boot.errorShow(json.msg)
            $("#button-login").attr('disabled',false);
        })
    }
}
$(function () {
    Boot.register()
    Boot.onlineStatus()
})