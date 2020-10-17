const Boot = {
    /*注册标签事件*/
    register: function () {
        $("#button-login").click(Boot.login)
        $("#button-login-fast").click(Boot.login_fast)
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
    /*快速登录事件*/
    login_fast: function () {

    }
}
const enrolErrorShow = function (msg) {

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
})