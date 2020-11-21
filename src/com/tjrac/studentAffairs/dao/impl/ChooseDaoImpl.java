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
    public int addChoose(Choose choose) {
        return super.insert(choose);
    }

    @Override
    public int modifyChoose(Choose choose) {
        return super.modify(choose);
    }

    @Override
    public Choose queryChooseByGrade(int gid) {
        Choose choose = super.query("grade_id", gid);

        if (choose != null) {
            if (choose.getStatus() == 1) {
                /*如果是开启状态,检测时间是否到期*/

                //到期时间
                long endtime = -1;

                try {
                    endtime = Long.parseLong(choose.getEndtime());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                //当前系统时间时间戳
                long currentTimeMillis = System.currentTimeMillis();

                if (endtime - currentTimeMillis <= 0) {  //如果过期
                    choose.setStatus(0);
                    super.modify(choose);
                }

            }
            return choose;
        }

        return null;
    }

}
