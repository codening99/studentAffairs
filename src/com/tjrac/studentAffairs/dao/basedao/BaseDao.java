package com.tjrac.studentAffairs.dao.basedao;
import com.tjrac.studentAffairs.utils.DBUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseDao  通用数据库增删改查，约定所有数据表类的id为表名_id
 *
 * @author : xziying
 * @create : 2020-10-02 23:22
 */
public class BaseDao<T>{
    private final Class<T> aClass;
    public BaseDao(Class<T> aClass){
        this.aClass = aClass;
    }
    private int update(int type, T obj) {
        StringBuilder sb = null;
        switch (type) {
            case 1:
                sb = new StringBuilder("insert into ");
                break;
            case 2:
                sb = new StringBuilder("update ");
                break;
            case 3:
                sb = new StringBuilder("delete from ");
                break;
            default:
                return -1;
        }

        // 获取表名
        Class<?> c = obj.getClass();
        // 添加到sql语句中
        sb.append(getTableNameFromClass(c));
        // 获取所有的Field和Method
        Method[] methods = c.getDeclaredMethods();
        // 存放表名和值的容器
        List<String> tableNames = new ArrayList<>();
        final List<Object> tableValues = new ArrayList<>();
        String condition = null;
        // 填充容器
        for (Method m : methods) {
            String mName = m.getName();
            if (mName.startsWith("get") && !mName.equals("getClass")) {
                Object value;
                try {
                    value = m.invoke(obj);
                } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                    return -1;
                }
                if (value == null) {continue;}
                if (type!=1 && getSqlIdFromClass(c).toLowerCase().equals(getFieldFromMethod(mName))) {
                    condition = getSqlIdFromClass(c).toLowerCase() + "=" + value;
                    continue;
                }

                tableValues.add(value);
                tableNames.add(getFieldFromMethod(mName));
            }
        }

        // 填充sql语句的字段部分
        if (type == 1) {
            for (int i = 0; i < tableNames.size(); i++) {
                if (i == 0) {
                    sb.append("(`");
                    sb.append(tableNames.get(i));
                } else {
                    sb.append("`,`");
                    sb.append(tableNames.get(i));
                }
                if (i == tableNames.size() - 1) {
                    sb.append("`)");
                }
            }
            sb.append(" values(");
            // 填充sql语句的值部分
            for (int i = 0; i < tableNames.size(); i++) {
                sb.append('?');
                if (i != tableNames.size() - 1)
                {sb.append(',');}
            }
            sb.append(")");
        } else if (type == 2) {
            for (int i = 0; i < tableNames.size(); i++) {
                if (i == 0) {
                    sb.append(" set ").append("`").append(tableNames.get(i)).append("`=?");
                } else {
                    sb.append(",`");
                    sb.append(tableNames.get(i)).append("`=?");
                }
                if (condition == null) {
                    return -1;
                }
            }
            sb.append(" where ").append(condition);
        } else {
            if (condition == null) {return -1;}
            sb.append(" where ").append(condition);
            tableValues.clear();
        }
        // 已经完成sql语句的形成，下边创建处理对象
        try {
            Connection conn = DBUtils.getConnection();

            return DBUtils.update(conn, sb.toString(), tableValues);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return -1;
        }
        //return 0;
    }

    public int insert(T obj) {
        return this.update(1, obj);
    }

    public int modify(T obj) {
        return this.update(2, obj);
    }

    public int delete(T obj) {
        return this.update(3, obj);
    }
    /**
     * 查询表并返回数组
     *
     * @param paras 必须为偶数,格式为 列名,值,列名,值,可以为空
     * @return 查询数组
     */
    public List<T> queryList(Object... paras) {
        if (paras.length % 2 != 0) {return null;}
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(getTableNameFromClass(aClass));
        final List<Object> tableValues = new ArrayList<>();
        if (paras.length != 0) {
            sb.append(" WHERE ");
            selectToSql(sb, tableValues, paras);
        }
        try {
            Connection conn = DBUtils.getConnection();
            return DBUtils.queryList(aClass, conn, sb.toString(), tableValues);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
    private void selectToSql(StringBuilder sb, List<Object> tableValues, Object[] paras) {
        for (int i = 0; i < paras.length / 2; i++) {
            if (i != 0) {sb.append(" AND ");}
            sb.append("`").append(paras[i * 2]).append("`=?");
            tableValues.add(paras[i * 2 + 1]);

        }
    }

    /**
     * 查询表并返回对象
     *
     * @param paras 必须为偶数,格式为 列名,值,列名,值
     * @return 实例对象
     */
    public T query(Object... paras) {
        if (paras.length % 2 != 0 || paras.length == 0) {return null;}
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(getTableNameFromClass(aClass));

        sb.append(" WHERE ");
        final List<Object> tableValues = new ArrayList<>();
        selectToSql(sb, tableValues, paras);
        try {
            Connection conn = DBUtils.getConnection();
            return DBUtils.query(aClass,conn, sb.toString(), tableValues);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return null;
        }
        //return null;
    }

    /**
     * 从提供方法的名字获取到与之匹配的数据库字段名 ---即从set，get方法名称获取到数据库中字段名字
     */
    private static String getFieldFromMethod(String MethodName) {
        return MethodName.substring(3, 4).toLowerCase() + MethodName.substring(4);
    }

    /**
     * 从Bean类名名获取到表名
     */
    private static String getTableNameFromClass(Class<?> c) {
        String cName = c.getName();
        String[] split = cName.split("\\.");
        return split[split.length - 2] + "_" + split[split.length - 1];
    }

    private static String getSqlIdFromClass(Class<?> c) {
        String cName = c.getName();
        String[] split = cName.split("\\.");
        return split[split.length - 1] + "_id";
    }
}
