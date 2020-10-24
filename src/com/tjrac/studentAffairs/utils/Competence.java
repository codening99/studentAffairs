package com.tjrac.studentAffairs.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * CompPara 权限解析类
 *
 * @author : xziying
 * @create : 2020-10-15 11:18
 */
public class Competence {
    // 权限表
    /**
     * 管理员权限
     */
    public final int COMP_MANAGER = 1;
    /**
     * 教师权限
     */
    public final int COMP_TEACHER = 2;
    /**
     * 学生权限
     */
    public final int COMP_STUDENT = 4;


    // 权限
    private Integer comp;
    public Competence(Integer comp){
        this.comp = comp;
    }

    /**
     * 检验是否存在这条权限
     * @param comp 权限代码
     * @return  存在返回true 失败返回false
     */
    public boolean test(Integer comp) {
        return (this.comp & comp) == comp;
    }
    /**
     * 添加权限
     * @param comp 权限代码
     * 比如管理员权限1(001)和老师权限2(010)加起来是3(011)，判定权限是否重复添加
     * @return 成功返回true 失败返回false
     */
    public boolean add(Integer comp) {
        //判断是否有comp权限
        if(test(comp)){
            return false;
        }
        this.comp += comp;
        return true;
    }
    /**
     * 解析权限
     * @return  权限数组
     * 比如管理员权限1(001)和老师权限2(010)加起来是3(011)，二进制解析后，把不是0的位数解析回到list里，返回1和2的数组
     */
    public List<Integer> resolveCompetence() {
        List<Integer> competence = new ArrayList<>();
        int i = 1;
        int b = this.comp;
        while (b != 0) {
            if (b % 2 == 1) {
                competence.add(i);
            }
            i*=2;
            b = b / 2;
        }
       return competence;
    }

    /**
     * 返回权值值
     * @return 权限值
     */
    public Integer getComp(){
        return this.comp;
    }
}
