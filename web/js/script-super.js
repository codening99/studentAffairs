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
            insert: $("button-insert_grade").click(function () {
                var grade_name = $("#").val();
                $.post("./superAdmin", {action: "insertGrade"}, function (data) {
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
                    $.post(".//superAdmin", {action: "delGrade", grade_id: id}, function (data) {
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
                $.post(".//superAdmin",{action:"mydifyGrade",grade_id: id},function (data){
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
                        $.post("./manage",{action:"typeInfoList"},Teacher.typeInfoList.modifyGrade)
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
            //插入新的系(名称)
            insert: $("button-insert_xi").click(function () {
                var department_name = $("#").val();
                $.post("./superAdmin", {action: "insertDep"}, function (data) {
                    //返回给服务器的数据(data)
                    const json = $.parseJSON(data);
                    if (json.event == 3) {
                        alert(json.msg);
                    }
                    json.department_name = department_name;
                    const $department = $("#xi-body");
                    $department.append('<tr data-id="' + data.department_id + '">\n' +
                        '<th>' + data.grade_name + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + data.department_id + '">修改</button>\n' +
                        '<button class="button-del" data-id="' + data.department_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.departmentList.modify).data("department_id", data.department_id)
                    $(".button-del").unbind("click").click(Teacher.departmentList.del).data("department_id", data.department_id)
                    Teacher.departmentList.count++
                })
            }),
            //展示系信息
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
            },
            // 删除系信息(名称)
            del : function () {
                const id = $(this).data("id")
                if (id!== undefined && confirm("是否删除该系账号为：" + $(this).data("department_id") + "的信息？")) {
                    $.post(".//superAdmin", {action: "delDep", department_id: id}, function (data) {
                        const json = $.parseJSON(data)
                        if (json.event === 0) {
                            $.post("./manage", {action: "departmentList"}, Teacher.departmentList.modifyDep)
                        } else {
                            alert(json.msg)
                        }
                    })
                }
            },
            //更新系信息(名称)
            modify: function () {
                const id=$(this).data("id")
                $.post(".//superAdmin",{action:"mydifyDep",department_id: id},function (data){
                    const json = $.parseJSON(data)
                    $("#data-id").html("").append('<tr" data-id="' + data.department_id + '">\n' +
                        '<th>' + json.department_name + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + json.department_id + '">更新</button>\n' +
                        '<button class="button-del" data-id="' + json.department_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.departmentList.modify).data("grade_id", data.grade_id)
                    $(".button-del").unbind("click").click(Teacher.departmentList.del).data("grade_id", data.grade_id)
                    Teacher.departmentList.count++
                    if(json.event===0){
                        $.post("./manage",{action:"departmentList"},Teacher.departmentList.modifyDep)
                    }else{
                        alert(json.msg)
                    }
                })
            },
        }
    },
    /*专业*/
    Specialty: {
        id: "special_list",
        /* 列表*/
        list:{
            //插入新的专业(专业名称，系名称)
            insert: $("button-insert_spec").click(function () {
                var department_name=$("#").val();
                var specialty_name = $("#").val();
                $.post("./superAdmin", {action: "insertSpec"}, function (data) {
                    //返回给服务器的数据(data)
                    const json = $.parseJSON(data);
                    if (json.event == 3) {
                        alert(json.msg);
                    }
                    const $specialty = $("#spec-body");
                    $specialty.append('<tr data-id="' + data.specialty_id + '">\n' +
                        '<th>' + data.department_id.department_name + '</th>\n' +
                        '<th>' + data.specialty_name + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + data.specialty_id + '">修改</button>\n' +
                        '<button class="button-del" data-id="' + data.specialty_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.specialtyList.modify).data("specialty_id", data.specialty_id)
                    $(".button-del").unbind("click").click(Teacher.specialtyList.del).data("specialty_id", data.specialty_id)
                    Teacher.specialtyList.count++
                })
            }),
            //展示所有专业信息（专业名称，系名称）
            showAll: function () {
                let count = 0
                $.post("./manage",
                    {
                        action: "specialtyList"
                    }, function (data) {
                        $("#spec-body").html("")
                        let json = $.parseJSON(data);
                        let specialtys = json.specialtys;
                        let departments=json.departments;
                        for (let i = 0; i < json.specialtys.length; i++) {
                            if (count % 2 === 0) {
                                $("#spec-body").append(
                                    "<tr class='dual'>\n" +
                                    "<th>" + departments[specialtys[i].department_id].department_name + "</th>\n" +
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
            },
            // 删除专业信息(名称)
            del : function () {
                const id = $(this).data("id")
                if (id!== undefined && confirm("是否删除该专业账号为：" + $(this).data("specialty_id") + "的信息？")) {
                    $.post(".//superAdmin", {action: "delSpec", specialty_id: id}, function (data) {
                        const json = $.parseJSON(data)
                        if (json.event === 0) {
                            $.post("./manage", {action: "specialtyList"}, Teacher.specialtyList.modifySpec)
                        } else {
                            alert(json.msg)
                        }
                    })
                }
            },
            //更新专业信息(专业名称，系名称)
            modify: function () {
                const id=$(this).data("id")
                $.post(".//superAdmin",{action:"mydifySpec",specialty_id: id},function (data){
                    const json = $.parseJSON(data)
                    $("#data-id").html("").append('<tr" data-id="' + data.specialty_id + '">\n' +
                        '<th>' + json.department_id.department_name + '</th>\n' +
                        '<th>' + json.specialty_name + '</th>\n' +
                        '<th>\n' +
                        '<button class="button-modify" data-id="' + json.specialty_id + '">更新</button>\n' +
                        '<button class="button-del" data-id="' + json.specialty_id + '">删除</button>\n' +
                        '</th>\n' +
                        '</tr>')
                    $(".button-modify").unbind("click").click(Teacher.specialtyList.modify).data("specialty_id", data.specialty_id)
                    $(".button-del").unbind("click").click(Teacher.specialtyList.del).data("specialty_id", data.specialty_id)
                    Teacher.specialtyList.count++
                    if(json.event===0){
                        $.post("./manage",{action:"specialtyList"},Teacher.specialtyList.modifySpec)
                    }else{
                        alert(json.msg)
                    }
                })
            },

        }

    }
}

$(function () {
    $(".sub-navigation .hotkey,.sub-navigation .common").click(Menu.clickOn)
})