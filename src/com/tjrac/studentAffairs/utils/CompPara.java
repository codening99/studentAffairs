package com.tjrac.studentAffairs.utils;

import java.util.List;

/**
 * CompPara 权限解析类
 *
 * @author : xziying
 * @create : 2020-10-15 11:18
 */
public class CompPara {
    private Integer comp;
    public CompPara(Integer comp){
        this.comp = comp;
    }

    /**
     * 检验是否存在这条权限
     * @param comp 权限代码
     * @return  存在返回true 失败返回false
     */
    public boolean test(Integer comp) {
        return false;
    }
    /**
     * 添加权限
     * @param comp 权限代码
     * 比如管理员权限1(001)和老师权限2(010)加起来是3(011)，判定权限是否重复添加
     * @return 成功返回true 失败返回false
     */
    public boolean add(Integer comp) {
        return false;
    }
    /**
     * 解析权限
     * @return  权限数组
     * 比如管理员权限1(001)和老师权限2(010)加起来是3(011)，二进制解析后，把不是0的位数解析回到list里，返回1和2的数组
     */
    public List<Integer> resolveCompetence() {
        return null;
    }
}
