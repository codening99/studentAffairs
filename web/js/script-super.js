const Boot = {}
/*菜单*/
const Menu = {
    id: "sub-navigation",
    clickOn: function () {
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
            case "tea_list":
                Content.common.visible(Content.Teacher, true)
                Content.Teacher.list.showAll();
                break;
            case "grade_list":
                Content.common.visible(Content.Grade, true)
                break;
            case "xi_list":
                Content.common.visible(Content.Department, true)
                break;
            case "special_list":
                Content.common.visible(Content.Specialty, true)
                break;
        }
    }
}
const Content = {
    /*共有方法*/
    common: {
        /*显示隐藏窗口*/
        visible: function (object, powerSwitch) {
            if (powerSwitch === true) {
                $("#" + object.id).css("display", "block")
            } else {
                $("#" + object.id).css("display", "none")
            }
        }
    },
    /*老师显示内容*/
    Teacher: {
        id: "tea_list",
        /*列表*/
        list: {
            //插入老师信息(姓名，账号，性别)
            insert: $("button-insert").click(function () {
                var name=$("#").val();
                var account=$("#").val();
                var teacher_sex=$("#").val();
                $.post("./superAdmin",{action:"insertTea"},function (data) {
                    //返回给服务器的数据(data)
                    const json=$.parseJSON(data);
                    if(json.event==3){
                        alert(json.msg);
                    }
                    json.name=name;
                    json.account=account;
                    json.teacher_sex=teacher_sex;
                    const $teacher = $("#teacher-body").html("");
                    $teacher.append('<tr class="' + Teacher.teacherList.getLineStyle() + '" data-id="' + data.teacher_id + '">\n' +
                        '<th>' + data.name + '</th>\n' +
                        '<th>' + data.account + '</th>\n' +
                        '<th>' + data.teacher_sex + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + data.teacher_id + '">更新</button>\n' +
                        '<button class="button-del" data-id="' + data.teacher_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.teacherList.modify).data("account", data.account)
                    $(".button-del").unbind("click").click(Teacher.teacherList.del).data("account", data.account)
                    Teacher.teacherList.count++
                })
            }),
            //显示老师列表信息
            showAll: function () {
                let count = 0
                $.post("./superAdmin",
                    {
                        action: "teacherList"
                    }, function (data) {
                        $("#teacher-body").html("")
                        //转换成json对象
                        let json = $.parseJSON(data);
                        let teachers = json.teachers;
                        for (let i = 0; i < json.teachers.length; i++) {
                            if (count % 2 === 0) {
                                $("#teacher-body").append(
                                    "<tr class='dual'>\n" +
                                    "<th>" + teachers[i].name + "</th>\n" +
                                    "<th>" + teachers[i].account + "</th>\n" +
                                    "<th>" + teachers[i].teacher_sex + "</th>\n" +
                                    "<th><</th>\n" +
                                    "</tr>"
                                )
                            } else {
                                $("#teacher-body").append(
                                    "<tr class='singular'>\n" +
                                    "<th>" + teachers[i].name + "</th>\n" +
                                    "<th>" + teachers[i].account + "</th>\n" +
                                    "<th>" + teachers[i].teacher_sex + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            }
                            count++
                        }
                    })
            },
            // 删除老师信息(姓名，账号，性别)
            del : function () {
                const id = $(this).data("id")
                if (id!== undefined && confirm("是否删除老师账号为：" + $(this).data("account") + "的信息？")) {
                    $.post(".//superAdmin", {action: "delUse", teacher_id: id}, function (data) {
                        const json = $.parseJSON(data)
                        if (json.event === 0) {
                            $.post("./superAdmin", {action: "teacherList"}, Teacher.teacherList.modifyUser)
                        } else {
                            alert(json.msg)
                        }
                    })
                }
            },
            //更新老师信息(姓名，账号，性别)
            modify: function () {
                const id=$(this).data("id")
                $.post(".//superAmin",{action:"mydifyTea",teacher_id: id},function (data){
                    const json = $.parseJSON(data)
                    $("#data-id").html("").append('<tr class="' + Teacher.teacherList.getLineStyle() + '" data-id="' + data.teacher_id + '">\n' +
                        '<th>' + json.name + '</th>\n' +
                        '<th>' + json.account + '</th>\n' +
                        '<th>' + json.teacher_sex + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + json.teacher_id + '">更新</button>\n' +
                        '<button class="button-del" data-id="' + json.teacher_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.teacherList.modify).data("account", data.account)
                    $(".button-del").unbind("click").click(Teacher.teacherList.del).data("account", data.account)
                    Teacher.teacherList.count++
                   if(json.event===0){
                       $.post("./superAdmin",{action:"teacherList"},Teacher.teacherList.modifyUser)
                   }else{
                       alert(json.msg)
                   }
                })


            },

        }
    },
    /*年级*/
    Grade: {
        id: "grade_list",
        /*列表*/
        list:{
            //插入新的年级(名称)
            insert: $("button-insert").click(function () {
                var grade_name = $("#").val();
                $.post("./manage", {action: "insertGrade"}, function (data) {
                    //返回给服务器的数据(data)
                    const json = $.parseJSON(data);
                    if (json.event == 3) {
                        alert(json.msg);
                    }
                    json.grade_name = grade_name;
                    const $grade = $("#grade-body");
                    $grade.append('<tr data-id="' + data.grade_id + '">\n' +
                        '<th>' + data.grade_name + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + data.grade_id + '">修改</button>\n' +
                        '<button class="button-del" data-id="' + data.grade_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.typeInfoList.modify).data("grade_id", data.grade_id)
                    $(".button-del").unbind("click").click(Teacher.typeInfoList.del).data("grade_id", data.grade_id)
                    Teacher.typeInfoList.count++
                })
            }),
            //显示所有的年级(名称)
            showAll: function () {
                let count = 0
                $.post("./manage",
                    {
                        action: "typeInfoList"
                    }, function (data) {
                        $("#grade-body").html("")
                        let json = $.parseJSON(data);
                        let grades = json.grades;
                        for (let i = 0; i < json.grades.length; i++) {
                            if (count % 2 === 0) {
                                $("#grade-body").append(
                                    "<tr class='dual'>\n" +
                                    "<th>" + grades[i].grade_name + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            } else {
                                $("#grade-body").append(
                                    "<tr class='singular'>\n" +
                                    "<th>" + grades[i].grade_name + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            }
                            count++
                        }
                    })
            },
            // 删除年级信息信息(名称)
            del : function () {
                const id = $(this).data("id")
                if (id!== undefined && confirm("是否删除该年级账号为：" + $(this).data("grade_id") + "的信息？")) {
                    $.post(".//manage", {action: "delGrade", grade_id: id}, function (data) {
                        const json = $.parseJSON(data)
                        if (json.event === 0) {
                            $.post("./manage", {action: "typeInfoList"}, Teacher.typeInfoList.modifyGade)
                        } else {
                            alert(json.msg)
                        }
                    })
                }
            },
            //更新年级信息(名称)
            modify: function () {
                const id=$(this).data("id")
                $.post(".//manage",{action:"mydifyGrade",grade_id: id},function (data){
                    const json = $.parseJSON(data)
                    $("#data-id").html("").append('<tr" data-id="' + data.grade_id + '">\n' +
                        '<th>' + json.grade_name + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + json.grade_id + '">更新</button>\n' +
                        '<button class="button-del" data-id="' + json.grade_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.typeInfoList.modify).data("grade_id", data.grade_id)
                    $(".button-del").unbind("click").click(Teacher.typeInfoList.del).data("grade_id", data.grade_id)
                    Teacher.typeInfoList.count++
                    if(json.event===0){
                        $.post("./manage",{action:"typeInfoList"},Teacher.typeInfoList.modifyUser)
                    }else{
                        alert(json.msg)
                    }
                })
            },
        }
    },
    /*系*/
    Department: {
        id: "xi_list",
        /* 列表*/
        list:{
            insert: null,
            showAll: function () {
                let count = 0
                $.post("./manage",
                    {
                        action: "departmentList"
                    }, function (data) {
                        $("#xi-body").html("")
                        let json = $.parseJSON(data);
                        let departments = json.departments;
                        for (let i = 0; i < json.departments.length; i++) {
                            if (count % 2 === 0) {
                                $("#xi-body").append(
                                    "<tr class='dual'>\n" +
                                    "<th>" + departments[i].department_name + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            } else {
                                $("#xi-body").append(
                                    "<tr class='singular'>\n" +
                                    "<th>" + departments[i].department_name + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            }
                            count++
                        }
                    })
            }
        }
    },
    /*专业*/
    Specialty: {
        id: "special_list",
        /* 列表*/
        list:{
            insert: null,
            showAll: function () {
                let count = 0
                $.post("./manage",
                    {
                        action: "specialtyList"
                    }, function (data) {
                        $("#spec-body").html("")
                        let json = $.parseJSON(data);
                        let specialtys = json.specialtys;
                        for (let i = 0; i < json.specialtys.length; i++) {
                            if (count % 2 === 0) {
                                $("#spec-body").append(
                                    "<tr class='dual'>\n" +
                                    "<th>" + specialtys[i].specialty_name + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            } else {
                                $("#spec-body").append(
                                    "<tr class='singular'>\n" +
                                    "<th>" + specialtys[i].specialty_name + "</th>\n" +
                                    "<th>操作</th>\n" +
                                    "</tr>"
                                )
                            }
                            count++
                        }
                    })
            }
        }

    }
}

$(function () {
    $(".sub-navigation .hotkey,.sub-navigation .common").click(Menu.clickOn)
})