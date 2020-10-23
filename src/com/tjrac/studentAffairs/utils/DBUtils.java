package com.tjrac.studentAffairs.utils;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtils {
    private static String url;
    private static String username;
    private static String password;

    //创建一个静态代码块在进行类加载的时候，将属性进行初始化
    static {
        //创建properties对象
        Properties prop = new Properties();
        //加载文件
        try {
            //读取数据
            //创建四个属性
            String driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://47.94.150.53:3306/studentAffairs?rewriteBatchedStatements=true&characterEncoding=utf8";
            username = "root";
            password = "root";
            //进行类加载加载驱动
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载驱动有问题");
        }
    }

    //创建一个工厂模式方法进行Connection获取
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    //创建一个方法用于获取preparedStatement对象
    //参数一：数据库连接对象  参数二：参数sql语句	参数三：给？赋予的实际参数值  可变参数以数组形式存在
    private static PreparedStatement getPstmt(Connection conn, String sql, Object... params) {
        //创建一个对象
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            //给？进行赋值  遍历params这个数组
            if (params.length == 1  && params[0].getClass()==ArrayList.class){
                ArrayList<Object> arrayList= (ArrayList<Object>) params[0];
                for (int i = 0; i < arrayList.size(); i++) {
                    pstmt.setObject(i + 1, arrayList.get(i));//参数1是表示给第几个问号赋值 起始值为1
                }
            } else {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);//参数1是表示给第几个问号赋值 起始值为1
                }
            }
            //返回对象
            return pstmt;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取对象失败");
            return null;
        }
    }

    //创建一个方法用于进行资源关闭
    private static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (stmt != null && !stmt.isClosed()) stmt.close();
            if (rs != null && !rs.isClosed()) rs.close();
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //创建一个方法用于进行数据的增删改操作
    public static int update(Connection conn, String sql, Object... params) {
        //获取pstmt对象
        PreparedStatement pstmt = getPstmt(conn, sql, params);
        //执行pstmt就可以了
        try {
            assert pstmt != null;
            int i = pstmt.executeUpdate();
            if (i == 0) {
                throw new SQLException("未选定行!", "", 404);
            }
            return 0;
        } catch (SQLException e) {
            System.out.print("SQL错误：");
            System.out.println(e.getMessage());
            return e.getErrorCode();
        } finally {
            close(conn, pstmt, null);
        }
    }

    //创建一个方法用于获取所有的字段名称
    public static String[] getCols(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        //创建一个字符串的数组用于存储字段名称
        String[] arr = new String[md.getColumnCount()];
        //用过md对象获取每个列的名称 存入到字符串数组中
        for (int i = 0; i < arr.length; i++) {
            arr[i] = md.getColumnName(i + 1);
        }
        return arr;
    }

    //创建一个方法用于进行数据查询  返回一个对象数据
    public static <T> T query(Class<T> c, Connection conn, String sql, Object... params) {
        //获取pstmt对象
        PreparedStatement pstmt = getPstmt(conn, sql, params);
        ResultSet rs = null;
        //执行sql语句
        try {
            assert pstmt != null;
            rs = pstmt.executeQuery();
            //获取rs结果集中所有的列名（字段名称）
            String[] cols = getCols(rs);
            //测试遍历
			/*for (String s:cols) {
				System.out.println(s);
			}*/
            //查看结果集是否有数据？
            if (rs.next()) {
                //利用反射机制进行对象的实例化创建 并且赋值
                //步骤一：创建一个实例对象
                T t = c.getDeclaredConstructor().newInstance();
                //步骤二：遍历列名数组  获取对象的方法   set
                for (String s : cols) {//id   ename  account   password job
                    //获取方法名称set+字段名称首字母大写
                    String methodName = "set" + Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    //通过字段名称获取数据库制定字段的数据
                    Object o = rs.getObject(s);
                    //利用反射method方法进行方法执行   参数1：方法名称 参数2是参数的具体数据类型
                    if (o != null) {
                        Method method = c.getMethod(methodName, o.getClass());
                        method.invoke(t, o);
                    }
                    //执行这个方法

                }
                return t;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    //创建一个方法用于进行数据查询  返回多个对象数据
    public static <T> List<T> queryList(Class<T> c, Connection conn, String sql, Object... params) {
        //获取pstmt对象
        PreparedStatement pstmt = getPstmt(conn, sql, params);
        ResultSet rs = null;
        //执行sql语句
        try {
            assert pstmt != null;
            rs = pstmt.executeQuery();
            //获取rs结果集中所有的列名（字段名称）
            String[] cols = getCols(rs);
            //创建一个List对象
            List<T> list = new ArrayList<>();
            //测试遍历
			/*for (String s:cols) {
				System.out.println(s);
			}*/
            //查看结果集是否有数据？
            while (rs.next()) {
                //利用反射机制进行对象的实例化创建 并且赋值
                //步骤一：创建一个实例对象
                T t = c.getDeclaredConstructor().newInstance();
                //步骤二：遍历列名数组  获取对象的方法   set
                for (String s : cols) {//id   ename  account   password job
                    //获取方法名称set+字段名称首字母大写
                    String methodName = "set" + Character.toUpperCase(s.charAt(0)) + s.substring(1);
                    //通过字段名称获取数据库制定字段的数据
                    Object o = rs.getObject(s);
                    if (o != null) {
                        //利用反射method方法进行方法执行   参数1：方法名称 参数2是参数的具体数据类型
                        Method method = c.getMethod(methodName, o.getClass());
                        //执行这个方法
                        method.invoke(t, o);
                        //将对象存储到list集合中
                    }
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(conn, pstmt, rs);
        }
    }

    // 开启事务
    public static void beginTransaction(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
    }
    // 提交事务
    public static void commitTransaction(Connection conn) throws SQLException {
        conn.commit();
    }
    // 回滚事务
    public static void rollbackTransaction(Connection conn) throws SQLException {
        conn.rollback();
    }
}









