package com.tjrac.studentAffairs.dao.impl;

import com.tjrac.studentAffairs.dao.basedao.BaseDao;
import com.tjrac.studentAffairs.dao.ChooseDao;
import com.tjrac.studentAffairs.domain.config.Choose;

/**
 * ChooseDaoImpl
 *
 * @author : xziying
 * @create : 2020-11-21 15:53
 */
public class ChooseDaoImpl extends BaseDao<Choose> implements ChooseDao {
    public ChooseDaoImpl() {
        super(Choose.class);
    }


    @Override
    public Choose queryByGrade(int gid) {
        Choose choose = super.query("grade_id", gid);
        if (choose.getStatus() == 1){
            /*如果是开启状态,检测时间是否到期*/
/*            if (到期){
                choose.setStatus(0);
                super.modify(choose);
            }*/

        }
        return choose;
    }
}
