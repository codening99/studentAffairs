const Boot = {

}
/*菜单*/
const Menu = {
    id : "sub-navigation",
    clickOn : function () {
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
    common : {
        /*显示隐藏窗口*/
        visible : function (object, powerSwitch) {
            if (powerSwitch === true){
                $("#" + object.id).css("display", "block")
            } else {
                $("#" + object.id).css("display", "none")
            }
        }
    },
    /*老师显示内容*/
    Teacher : {
        id : "tea_list",
        /*列表*/
        list : {
            count : 0,
            insert : null,
            showAll : function () {
                $.post("superAdmin",
                    {
                        action : "teacherList"
                    }, function (data) {
                        let json = $.parseJSON(data);
                        let teachers = json.teachers;
                        if (count % 2 === 0) {
                            $("#teacher-body").append(
                                "<tr class='dual'>\n" +
                                "<th>" + teachers[] +"</th>\n" +
                                "<th>账号</th>\n" +
                                "<th>操作</th>\n" +
                                "</tr>"
                            )
                        } else {

                        }
                    })
            }
        }
    },
    /*年级*/
    Grade : {
        id : "grade_list"
    },
    /*系*/
    Department : {
        id : "xi_list"
    },
    /*专业*/
    Specialty : {
        id : "special_list"
    }
}

$(function () {
    $(".sub-navigation .hotkey,.sub-navigation .common").click(Menu.clickOn)
})