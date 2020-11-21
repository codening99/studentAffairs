package com.tjrac.studentAffairs.dao;

import com.tjrac.studentAffairs.domain.config.Choose;

/**
 * ChooseDao
 *
 * @author : xziying
 * @create : 2020-11-21 15:52
 */
public interface ChooseDao {
    Choose queryChooseByGrade(int gid);

    int addChoose(Choose choose);

    int modifyChoose(Choose choose);

}
