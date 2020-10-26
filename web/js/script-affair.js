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
        $("#button-upload-submit").click(Teacher.pop_up_upload.uploadFiles)

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
    /*教师事务中心初始化*/
    teacher_init: function () {
        $("title").html("教师事务中心")
        $(".title img").attr("src", "img/logo3.png")
        $("#sub-navigation-student").css("display", "none")
        $("#sub-navigation-teacher").css("display", "block")
        $("#list").css("display", "block")

        // 更新学生列表数据 manage?action=studentList
        $.post("./manage", {action: "studentList"}, Teacher.studentList.update)

        // 初始化弹窗信息
        $("#button-insert").click(Teacher.studentList.add)
        $("#button-import").click(Teacher.pop_up_upload.show)
        Teacher.pop_up.init()
        $("#pop-up-box .close").click(Teacher.pop_up.close)
        $("#pop-up-student-modify select[name='departments']").change(Teacher.pop_up.updateSpecialties)
        $("#pop-up-student-modify select[name='specialties']").change(Teacher.pop_up.updateClazzAndDirection)
        $("#pop-up-student-modify select[name='grade']").change(Teacher.pop_up.updateClazzAndDirection)



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
            $manage.append('<tr class="'+Teacher.studentList.getLineStyle()+'" data-id="'+data.student_id+'">\n' +
                '<th>'+data.sno+'</th>\n' +
                '<th>'+data.name+'</th>\n' +
                '<th>'+data.student_sex+'</th>\n' +
                '<th>'+data.grade_name+'</th>\n' +
                '<th>'+data.department_name+'</th>\n' +
                '<th>'+data.specialty_name+'</th>\n' +
                '<th>'+data.direction_name+'</th>\n' +
                '<th>'+data.clazz_name+'</th>\n' +
                '<th>\n' +
                '<button class="button-modify" data-id="'+data.student_id+'">修改</button>\n' +
                '<button class="button-del" data-id="'+data.student_id+'">删除</button>\n' +
                '</th>\n' +
                '</tr>')
            $(".button-modify").unbind("click").click(Teacher.studentList.modify)
            $(".button-del").unbind("click").click(Teacher.studentList.del).data("sno", data.sno)
            Teacher.studentList.count++
        },
        clear: function () {
            const $manage = $("#manage-body");
            Teacher.studentList.count = 0
            $manage.html("")
        },
        // 修改事件
        modify: function () {
            const student_id = $(this).data("id")
            const $tr = $("#manage-body tr[data-id='"+student_id+"']")
            const children = $tr.children("th");
            if (children.length === 9) {
                $("#pop-up-student-modify input[name='sno']").attr("disabled", "disabled").val(children[0].innerHTML)
                $("#pop-up-student-modify input[name='name']").val(children[1].innerHTML)
                $("#pop-up-student-modify select[name='sex']").val(children[2].innerHTML)

                $("#pop-up-student-modify select[name='grade']").val(
                    Teacher.pop_up.reverseAnalysisData.grade2Id(children[3].innerHTML)
                )
                $("#pop-up-student-modify select[name='departments']").val(
                    Teacher.pop_up.reverseAnalysisData.department2Id(children[4].innerHTML)
                ).change()
                $("#pop-up-student-modify select[name='specialties']").val(
                    Teacher.pop_up.reverseAnalysisData.specialty2Id(children[5].innerHTML)
                ).change()
                $("#pop-up-student-modify select[name='directions']").val(
                    Teacher.pop_up.reverseAnalysisData.direction2Id(children[6].innerHTML)
                )
                $("#pop-up-student-modify select[name='clazz']").val(
                    Teacher.pop_up.reverseAnalysisData.clazz2Id(children[7].innerHTML)
                )
                Teacher.pop_up.close()
                $("#pop-up-box").css("display", "block")
                $("#pop-up-modify").css("display", "block")
                $("#pop-up-box div[name='title']").html("修改学生信息")
                $("#pop-up-submit").val("确认修改").unbind("click").click(Teacher.pop_up.submit).data("id", student_id)
            }
        },
        // 添加学生事件
        add : function () {
            Teacher.pop_up.close()
            $("#pop-up-student-modify input[name='sno']").removeAttr("disabled")
            $("#pop-up-box").css("display", "block")
            $("#pop-up-modify").css("display", "block")
            $("#pop-up-box div[name='title']").html("添加学生信息")
            const $pop = $("#pop-up-submit");
            $pop.html("确认添加")
            $pop.unbind("click")
            $pop.click(Teacher.pop_up.submit)
            $pop.removeData("id")
        },
        // 删除学生事件
        del : function () {
            const id = $(this).data("id")
            if (id!== undefined && confirm("是否删除学号为：" + $(this).data("sno") + "的信息？")) {
                $.post("./manage", {action: "deleteStudentInfo", student_id: id}, function (data) {
                    const json = $.parseJSON(data)
                    if (json.event === 0) {
                        $.post("./manage", {action: "studentList"}, Teacher.studentList.update)
                    } else {
                        alert(json.msg)
                    }
                })
            }
        }
    },
    /*弹出窗口事件*/
    pop_up: {
        data : undefined,
        /*反向解析数据*/
        reverseAnalysisData: {
            grade2Id : function (grade) {
                const json = Teacher.pop_up.data
                for (let i = 0; i < json.grades_count; i++) {
                    if (json.grades[i].grade_name == grade) return json.grades[i].grade_id;
                }
                return ""
            },
            department2Id : function (department) {
                const json = Teacher.pop_up.data
                for (let i = 0; i < json.departments_count; i++) {
                    if (json.departments[i].department_name == department) return json.departments[i].department_id;
                }
                return ""
            },
            specialty2Id : function (specialty) {
                const json = Teacher.pop_up.data
                for (let i = 0; i < json.specialties_count; i++) {
                    if (json.specialties[i].specialty_name == specialty) return json.specialties[i].specialty_id;
                }
                return ""
            },
            direction2Id : function (direction) {
                const json = Teacher.pop_up.data
                for (let i = 0; i < json.directions_count; i++) {
                    if (json.directions[i].direction_name == direction) return json.directions[i].direction_id;
                }
                return ""
            },
            clazz2Id : function (clazz) {
                const json = Teacher.pop_up.data
                for (let i = 0; i < json.clazzes_count; i++) {
                    if (json.clazzes[i].clazz_name == clazz) return json.clazzes[i].clazz_id;
                }
                return ""
            }
        },
        init : function () {
            $.post("./manage", { action:"typeInfoList"}, function (data) {
                const json = $.parseJSON(data)
                Teacher.pop_up.data = json
                if (json.event === 0){
                    Teacher.pop_up.updateSelect(
                        $("#pop-up-student-modify select[name='grade']"),
                        json.grades_count,
                        json.grades,
                        "--请选择年级--",
                        "grade_id",
                        "grade_name"
                    )
                    Teacher.pop_up.updateSelect(
                        $("#pop-up-student-modify select[name='departments']"),
                        json.departments_count,
                        json.departments,
                        "--请选择系--",
                        "department_id",
                        "department_name"
                    )
                }

            })
        },
        /*关闭弹窗*/
        close : function (){
            $("#pop-up-box").css("display", "none")
            $("#pop-up-modify").css("display", "none")
            $("#pop-up-upload").css("display", "none")
            $("#pop-up-submit").removeData("id")
        },
        /*更新班级和方向*/
        updateClazzAndDirection: function (){
            const grade_id = $("#pop-up-student-modify select[name='grade']").val()
            const specialties_id = $("#pop-up-student-modify select[name='specialties']").val()
            const $select = $("#pop-up-student-modify select[name='clazz']")
            $select.html('<option value="">--请选择班级--</option>')
            const json = Teacher.pop_up.data
            for (let i = 0; i < json.clazzes_count; i++) {
                if (json.clazzes[i].grade_id == grade_id && json.clazzes[i].specialty_id == specialties_id){
                    $select.append(
                        '<option value="'+json.clazzes[i].clazz_id+'">'+json.clazzes[i].clazz_name+'</option>'
                    )
                }
            }
            const $directions = $("#pop-up-student-modify select[name='directions']")
            $directions.html('<option value="">--请选择方向--</option>')
            for (let i = 0; i < json.directions_count; i++) {
                if (json.directions[i].grade_id == grade_id && json.directions[i].specialty_id == specialties_id){
                    $directions.append(
                        '<option value="'+json.directions[i].direction_id+'">'+json.directions[i].direction_name+'</option>'
                    )
                }
            }
        },
        /*更新专业下拉框*/
        updateSpecialties: function (){
            const department_id = $("#pop-up-student-modify select[name='departments']").val()
            const $select = $("#pop-up-student-modify select[name='specialties']")
            $select.html('<option value="">--请选择专业--</option>')
            const json = Teacher.pop_up.data
            for (let i = 0; i < json.specialties_count; i++) {
                if (json.specialties[i].department_id == department_id){
                    $select.append(
                        '<option value="'+json.specialties[i].specialty_id+'">'+json.specialties[i].specialty_name+'</option>'
                    )
                }
            }
        },
        /*更新下拉框*/
        updateSelect: function ($select, count, json, defaultVal, id_name, name_name) {
            $select.html('<option value="">'+defaultVal+'</option>')
            for (let i = 0; i < count; i++) {
                $select.append(
                    '<option value="'+json[i][id_name]+'">'+json[i][name_name]+'</option>'
                )
            }
        },
        /*确认提交按钮*/
        submit : function () {
            const error_id = "pop-up-student-modify"
            let submitData = {}
            submitData.sno = $("#pop-up-student-modify input[name='sno']").val()
            if (submitData.sno === "") {
                System.error.show(error_id, "学号不能为空")
                return
            }
            submitData.name = $("#pop-up-student-modify input[name='name']").val()
            if (submitData.name === "") {
                System.error.show(error_id, "姓名不能为空")
                return
            }
            submitData.password = $("#pop-up-student-modify input[name='password']").val()
            if (submitData.password !== "" && (submitData.password.length < 8 || submitData.password.length > 20)){
                System.error.show(error_id, "密码长度应该在8-20之间！")
                return ;
            }
            submitData.student_sex = $("#pop-up-student-modify select[name='sex']").find("option:selected").text();
            if (submitData.student_sex === "") {
                System.error.show(error_id, "姓名不能为空！")
                return
            }
            submitData.grade_name = $("#pop-up-student-modify select[name='grade']").find("option:selected").text();
            if (submitData.grade_name === "") {
                System.error.show(error_id, "请选择年级！")
                return
            }
            submitData.department_name = $("#pop-up-student-modify select[name='departments']").find("option:selected").text();
            if (submitData.department_name === "") {
                System.error.show(error_id, "请选择系！")
                return
            }
            submitData.specialty_name = $("#pop-up-student-modify select[name='specialties']").find("option:selected").text();
            if (submitData.specialty_name === "") {
                System.error.show(error_id, "请选择专业！")
                return
            }
            submitData.direction_name = $("#pop-up-student-modify select[name='directions']").find("option:selected").text();

            submitData.clazz_name = $("#pop-up-student-modify select[name='clazz']").find("option:selected").text();
            if (submitData.clazz_name === "") {
                System.error.show(error_id, "请选择专业！")
                return
            }
            submitData.student_id = $(this).data("id")
            System.error.show(error_id)
            submitData.action = "updateStudent"
            $.post("./manage", submitData, function (data) {
                const json = $.parseJSON(data)
                if (json.event === 0) {
                    $.post("./manage", {action: "studentList"}, Teacher.studentList.update)
                    Teacher.pop_up.close()
                } else {
                    System.error.show(error_id, json.msg)
                }

            })
        }

    },
    /*弹出窗口上传事件*/
    pop_up_upload : {
        show : function () {
            Teacher.pop_up.close()
            $("#pop-up-box").css("display", "block")
            $("#pop-up-upload").css("display", "block")
        },
        uploadFiles: function () {
            const formData = new FormData();
            const files = $("#upload-file").get(0).files;
            if (files.length === 1){
                formData.append("file",files[0]);
                formData.append("action",'importStudentInfo');
                $.ajax({
                    url:'./UploadExcel', /*接口域名地址*/
                    type:'post',
                    data: formData,
                    contentType: false,
                    processData: false,
                    success : function(data){
                        console.log(data);
                    }
                })
            }
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