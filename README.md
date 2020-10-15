e# studentAffairs

## 建表约定

| 数据表名  | 唯一主键 |
| --------- | -------- |
| 包名_类名 | 类名_id  |

## 权限表

| competence_id(二进制计数，权限可以累加) | 对应十进制数 | 权限名     | 权限说明           |
| --------------------------------------- | ------------ | ---------- | ------------------ |
| 1                                       | 1            | 管理员权限 | 所有操作都可以进行 |
| 10                                      | 2            | 老师权限   |                    |
| 100                                     | 4            | 学生权限   |                    |



## 数据库表

## ajax请求接口

### 登录

| 提交地址            | 参数     | 参数说明                         |
| ------------------- | -------- | -------------------------------- |
| /login?action=login | type     | 类型（1：教师登录  2：学生登录） |
|                     | account  | 账号或学号                       |
|                     | password | 密码                             |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容         | 数据说明     |
| -------- | -------- | ---------------- | ------------ |
| event    | int      | 返回状态信息     |              |
|          |          | 0                | 登录成功     |
|          |          | 1                | 用户名不存在 |
|          |          | 2                | 密码错误     |
| msg      | string   | 返回提示消息内容 |              |

### 获取在线状态

| 提交地址                      | 参数 | 参数说明 |
| ----------------------------- | ---- | -------- |
| /login?action=getOnlineStatus |      |          |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容         | 数据说明 |
| -------- | -------- | ---------------- | -------- |
| event    | int      | 返回状态信息     |          |
|          |          | 0                | 未登录   |
|          |          | 1                | 在线中   |
| msg      | string   | 返回提示消息内容 |          |

### 添加教师信息

| 提交地址                 | 参数        | 参数说明 |
| ------------------------ | ----------- | -------- |
| /login?action=addTeacher | account     | 账号     |
|                          | password    | 密码     |
|                          | name        | 姓名     |
|                          | teacher_sex | 老师性别 |
|                          | competence  | 权限     |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容         | 数据说明   |
| -------- | -------- | ---------------- | ---------- |
| event    | int      | 返回状态信息     |            |
|          |          | 0                | 添加成功   |
|          |          | 1                | 权限约束   |
|          |          | 2                | 账户已存在 |
| msg      | string   | 返回提示消息内容 |            |



### 查看教师信息

| 提交地址                   | 参数        | 参数说明 |
| -------------------------- | ----------- | -------- |
| /login?action=checkTeacher | account     | 账号     |
|                            | password    | 密码     |
|                            | name        | 姓名     |
|                            | teacher_sex | 老师性别 |
|                            | competence  | 权限     |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容     | 数据说明         |
| -------- | -------- | ------------ | ---------------- |
| event    | int      | 返回状态信息 |                  |
|          |          | 0            | 跳转教师信息页面 |
|          |          | 1            | 权限约束         |

### 删除教师信息

| 提交地址                    | 参数        | 参数说明 |
| --------------------------- | ----------- | -------- |
| /login?action=deleteTeacher | account     | 账号     |
|                             | password    | 密码     |
|                             | name        | 姓名     |
|                             | teacher_sex | 老师性别 |
|                             | competence  | 权限     |

| 数据段名 | 数据类型 | 数据内容         | 数据说明                 |
| -------- | -------- | ---------------- | ------------------------ |
| event    | int      | 返回状态信息     |                          |
|          |          | 0                | 删除成功                 |
|          |          | 1                | 权限约束                 |
|          |          | 2                | 删除失败跳回教师信息页面 |
| msg      | string   | 返回提示消息内容 |                          |

### 更新教师信息

| 提交地址                    | 参数        | 参数说明 |
| --------------------------- | ----------- | -------- |
| /login?action=updateTeacher | account     | 账号     |
|                             | password    | 密码     |
|                             | name        | 姓名     |
|                             | teacher_sex | 老师性别 |
|                             | competence  | 权限     |

| 数据段名 | 数据类型 | 数据内容         | 数据说明                 |
| -------- | -------- | ---------------- | ------------------------ |
| event    | int      | 返回状态信息     |                          |
|          |          | 0                | 更新成功                 |
|          |          | 1                | 权限约束                 |
|          |          | 2                | 更新失败跳回教师信息页面 |
| msg      | string   | 返回提示消息内容 |                          |

**返回json数据格式**

### 添加学生信息

| 提交地址                 | 参数            | 参数说明 |
| ------------------------ | --------------- | -------- |
| /login?action=addStudent | sno             | 学号     |
|                          | name            | 姓名     |
|                          | sex             | 性别     |
|                          | password        | 密码     |
|                          | competence      | 权限     |
|                          | grade_name      | 年级     |
|                          | clazz_name      | 班级     |
|                          | department_name | 系       |
|                          | specialty_name  | 专业     |
|                          | direction_name  | 方向     |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容         | 数据说明   |
| -------- | -------- | ---------------- | ---------- |
| event    | int      | 返回状态信息     |            |
|          |          | 0                | 添加成功   |
|          |          | 1                | 权限约束   |
|          |          | 2                | 账户已存在 |
| msg      | string   | 返回提示消息内容 |            |

### 查看学生信息

| 提交地址                   | 参数            | 参数说明 |
| -------------------------- | --------------- | -------- |
| /login?action=checkStudent | sno             | 学号     |
|                            | name            | 姓名     |
|                            | sex             | 性别     |
|                            | password        | 密码     |
|                            | competence      | 权限     |
|                            | grade_name      | 年级     |
|                            | clazz_name      | 班级     |
|                            | department_name | 系       |
|                            | specialty_name  | 专业     |
|                            | direction_name  | 方向     |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容     | 数据说明         |
| -------- | -------- | ------------ | ---------------- |
| event    | int      | 返回状态信息 |                  |
|          |          | 0            | 跳转学生信息页面 |
|          |          | 1            | 权限约束         |

### 删除学生信息

| 提交地址                    | 参数            | 参数说明 |
| --------------------------- | --------------- | -------- |
| /login?action=deleteStudent | sno             | 学号     |
|                             | name            | 姓名     |
|                             | sex             | 性别     |
|                             | password        | 密码     |
|                             | competence      | 权限     |
|                             | grade_name      | 年级     |
|                             | clazz_name      | 班级     |
|                             | department_name | 系       |
|                             | specialty_name  | 专业     |
|                             | direction_name  | 方向     |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容         | 数据说明                 |
| -------- | -------- | ---------------- | ------------------------ |
| event    | int      | 返回状态信息     |                          |
|          |          | 0                | 删除成功                 |
|          |          | 1                | 权限约束                 |
|          |          | 2                | 删除失败跳转写生信息页面 |
| msg      | string   | 返回提示消息内容 |                          |

### 更新学生信息

| 提交地址                    | 参数            | 参数说明 |
| --------------------------- | --------------- | -------- |
| /login?action=updateStudent | sno             | 学号     |
|                             | name            | 姓名     |
|                             | sex             | 性别     |
|                             | password        | 密码     |
|                             | competence      | 权限     |
|                             | grade_name      | 年级     |
|                             | clazz_name      | 班级     |
|                             | department_name | 系       |
|                             | specialty_name  | 专业     |
|                             | direction_name  | 方向     |

**返回json数据格式**

| 数据段名 | 数据类型 | 数据内容         | 数据说明                 |
| -------- | -------- | ---------------- | ------------------------ |
| event    | int      | 返回状态信息     |                          |
|          |          | 0                | 更新成功                 |
|          |          | 1                | 权限约束                 |
|          |          | 2                | 更新失败跳转学生信息页面 |
| msg      | string   | 返回提示消息内容 |                          |

## 学生各种属性表类业务接口

### Clazz班级类

| 提交地址            | 参数         | 参数说明 |
| ------------------- | ------------ | -------- |
| /login?action=Clazz | clazz_name   | 班级名   |
|                     | specialty_id | 专业id   |
|                     | grade_id     | 年级id   |