package com.tjrac.studentAffairs.utils;

import java.util.ArrayList;
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
        int purviewValue=(int)Math.pow(2,this.comp);
        int value=purviewValue&(int)Math.pow(2,comp);
        if(value==(int)Math.pow(2,comp)){
            return true;
        }
        return false;
    }
    /**
     * 添加权限
     * @param comp 权限代码
     * 比如管理员权限1(001)和老师权限2(010)加起来是3(011)，判定权限是否重复添加
     * @return 成功返回true 失败返回false
     */
    public boolean add(Integer comp) {
        //判断是否有comp权限
        int purview =(int)Math.pow(2,this.comp);//没添加时的总权限
        int userPurview=(int)Math.pow(2,comp)+purview;
        int value=userPurview &((int)Math.pow(2,comp));//将新添加的权限值与之前的权限进行位运算用于判断是否重复
        if(userPurview==value){
            return false;
        }
        //String number=Integer.toBinaryString(userPurview);//权限值为二进制
         this.comp=userPurview;  //现在所具有的的权限值
         return true;
    }
    /**
     * 解析权限
     * @return  权限数组
     * 比如管理员权限1(001)和老师权限2(010)加起来是3(011)，二进制解析后，把不是0的位数解析回到list里，返回1和2的数组
     */
    public List<Integer> resolveCompetence() {
        List<Integer> competence = new ArrayList<Integer>();
        int i = 0;
        int c=0;
        int b =this.comp;
        while (b != 0) {
            c = b % 2;
            if (c == 1) {
                competence.add(i*2);
            }
            i++;
            b = b / 2;
        }

       return competence;
    }
}
