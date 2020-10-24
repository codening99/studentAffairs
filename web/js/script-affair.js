/*导航栏对象*/
const Boot = {
    /*注册点击事件*/
    register: function () {
        /***界面按钮***/
        /*导航栏点击*/
        $(".sub-navigation .hotkey,.sub-navigation .common").click(function () {
            const name = $(this).data("name");
            if (name === "exit") {
                if (confirm("是否退出当前用户？")) {
                    NavigationBar.exit()
                } else {
                    return
                }
            }
            $(".content-body").css("display", "none")
            $(".sub-navigation .hotkey, .sub-navigation .common").attr("class", "common")
            $(this).attr("class", "hotkey")
            switch (name) {
                case "info":
                    $("#info").css("display", "block");
                    break;
                case "modify":
                    $("#modify").css("display", "block");
                    break;
                case "activity":
                    $("#activity").css("display", "block");
                    break;
                case "elective":
                    $("#elective").css("display", "block");
                    break;
                case "direction":
                    $("#direction").css("display", "block");
                    break;
                case "info-list":
                    $("#list").css("display", "block");
                    break;
                case "set-direction":
                    $("#set-direction").css("display", "block");
                    break;
                case "set-elective":
                    $("#set-elective").css("display", "block");
                    break;
                case "set-activity":
                    $("#set-activity").css("display", "block");
                    break;
            }
        })
        /*方向设置导航*/
        $(".content-direction .hotkey,.content-direction .common").click(function () {
            $(".content-direction-body").css("display", "none")
            $(".content-direction .hotkey, .content-direction .common").attr("class", "common")
            $(this).attr("class", "hotkey")
            const name = $(this).data("name");
            switch (name) {
                case "direction-set":
                    $("#direction-set").css("display", "block");
                    break;
            }
        })
        /***教师端***/
        /*导出导入事件*/
        $("#button-emptyTemplate").click(StudentInformationForm.exportEmptyTemplate)
        $("#button-export").click(StudentInformationForm.exportStudentInformation)

        /***学生端***/
        $("#content-modify-submit").click(Student.modify.submit)
    },
    /*检测登录状态*/
    onlineStatus: function () {
        $.post("./login", {action: "getOnlineStatus"}, function (data) {
            const json = $.parseJSON(data)
            if (json.event !== 1) {
                window.location.href = "./index.html"
            } else {
                if (json.type === 1) {
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

        // 更新学生数据
        $.post("./student", {action: "getStudentInfo"}, Student.info.update)
    },
    teacher_init: function () {
        $("title").html("教师事务中心")
        $(".title img").attr("src", "img/logo3.png")
        $("#sub-navigation-student").css("display", "none")
        $("#sub-navigation-teacher").css("display", "block")
        $("#list").css("display", "block")

        // 更新学生列表数据 manage?action=studentList
        $.post("./manage", {action: "studentList"}, Teacher.studentList.update)

    }

}
const NavigationBar = {
    exit: function () {
        $.post("./login", {action: "logout"}, function () {
            window.location.href = "./index.html"
        })
    }
}
const StudentInformationForm = {
    /*导出空模板*/
    exportEmptyTemplate: function () {
        $.post("./manage", {action: "exportModelExcel"}, function (data) {
            const json = $.parseJSON(data)
            if (json.event === 0) {
                $.download(json.url, {}, "get");
            } else {
                alert(json.msg)
            }
        })
    },
    /*导出学生信息*/
    exportStudentInformation: function () {
        $.download("./manage", {action: "exportStudentInfo"}, "post");
    }
}
const System = {
    error: {
        show:function (id, msg) {
            const $error = $("#" + id + " div[name='input-error']");
            const $error_msg = $("#" + id + " div[name='input-error'] div[name='msg']");
            if(msg===undefined) {$error.css("display", "none");return ;}
            $error_msg.html(msg);
            $error.css("display", "block");
        }
    }
}
const Student = {

    // 个人信息栏
    info: {
        data: undefined,
        // 获取个人信息 ajax函数
        update: function (data) {
            const json = $.parseJSON(data)
            if(json.event !== 0){
                alert(json.msg)
                return
            }
            Student.info.data = json.info
            $("#content-info-box th[name='id']").html(json.info.sno)
            $("#content-info-box th[name='name']").html(json.info.name)
            $("#content-info-box th[name='sex']").html(json.info.student_sex)
            $("#content-info-box th[name='grade']").html(json.info.grade_name)
            $("#content-info-box th[name='department']").html(json.info.department_name)
            $("#content-info-box th[name='specialty']").html(json.info.specialty_name)
            $("#content-info-box th[name='direction']").html(json.info.direction_name)
            $("#content-info-box th[name='class']").html(json.info.clazz_name)

            $("#modify input[name='account']").val(json.info.name + "("+json.info.sno+")")

            if (json.info.password === "E10ADC3949BA59ABBE56E057F20F883E"){
                $("#sub-navigation-student div[data-name='modify']").click()
                alert("当前密码为初始密码，请及时修改密码！")
            }

        }
    },
    // 修改密码栏
    modify: {
        // 点击修改密码
        submit: function () {
            const $oldPassword = $("#modify input[name='oldPassword']");
            const $newPassword = $("#modify input[name='newPassword']");
            const $confirmPassword = $("#modify input[name='confirmPassword']");
            if ($oldPassword.val() === ""){
                System.error.show("modify", "旧密码不能为空！")
                return
            }
            if ($newPassword.val() === ""){
                System.error.show("modify", "请输入新密码！")
                alert("请输入新密码！")
                return
            }
            if ($confirmPassword.val() === ""){
                System.error.show("modify", "请再次输入新密码！")
                return
            }
            if ($confirmPassword.val() === $oldPassword.val()){
                System.error.show("modify", "旧密码不能和新密码一致！")
                return
            }
            if ($newPassword.val() !== $confirmPassword.val()) {
                System.error.show("modify", "两次密码不一致！")
                return
            }
            System.error.show("modify")
            $.post("./student", {
                action: "changePassword",
                old: $oldPassword.val(),
                password: $newPassword.val()
            }, function (data) {
                const json = $.parseJSON(data)
                if (json.event !== 0) {
                    System.error.show("modify", json.msg)
                    return
                }
                alert(json.msg)
                $newPassword.val("")
                $confirmPassword.val("")
                $oldPassword.val("")
            })
        },
    }
}
const Teacher = {
    studentList: {
        // 数据
        data : undefined,
        count : 0,  // 目前网页列表数量
        // 更新学生列表 AJAX请求
        update: function (data) {
            const json = $.parseJSON(data)
            Teacher.studentList.data = data
            Teacher.studentList.clear()
            if (json.event === 0){
                for (let i = 0; i < json.count; i++) {
                    Teacher.studentList.insert(json.students[i])
                }
            }
        },
        getLineStyle : function () {return Teacher.studentList.count % 2 === 0 ? "singular" : "dual"}
        ,
        // 插入列表
        insert: function (data) {
            const $manage = $("#manage-body");
            $manage.append('<tr class="'+Teacher.studentList.getLineStyle()+'">\n' +
                '<th>'+data.sno+'</th>\n' +
                '<th>'+data.name+'</th>\n' +
                '<th>'+data.student_sex+'</th>\n' +
                '<th>'+data.grade_name+'</th>\n' +
                '<th>'+data.department_name+'</th>\n' +
                '<th>'+data.specialty_name+'</th>\n' +
                '<th>'+data.direction_name+'</th>\n' +
                '<th>'+data.clazz_name+'</th>\n' +
                '<th>\n' +
                '<button class="button-modify">修改</button>\n' +
                '<button class="button-del">删除</button>\n' +
                '</th>\n' +
                '</tr>')
            Teacher.studentList.count++
        },
        clear: function () {
            const $manage = $("#manage-body");
            Teacher.studentList.count = 0
            $manage.html("")
        }
    }
}
jQuery.download = function (url, data, method) { // 获得url和data
    if (url && data) {
        // data 是 string 或者 array/object
        data = typeof data == 'string' ? data : jQuery.param(data); // 把参数组装成 form的 input
        let inputs = '';
        jQuery.each(data.split('&'), function () {
            const pair = this.split('=');
            inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
        }); // request发送请求
        jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>').appendTo('body').submit().remove();
    }
}
$(function () {
    Boot.onlineStatus()
    Boot.register()

})