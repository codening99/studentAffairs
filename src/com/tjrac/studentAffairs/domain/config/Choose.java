package com.tjrac.studentAffairs.domain.config;

/**
 * choose 配置选方向
 *
 * @author : xziying
 * @create : 2020-11-21 15:51
 */
public class Choose {
    Integer choose_id;
    String endtime;
    Integer status;
    Integer grade_id;

    public Choose() {
    }

    public Choose(String endtime, Integer status, Integer grade_id) {
        this.endtime = endtime;
        this.status = status;
        this.grade_id = grade_id;
    }

    public Integer getChoose_id() {
        return choose_id;
    }

    public void setChoose_id(Integer choose_id) {
        this.choose_id = choose_id;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    @Override
    public String toString() {
        return "choose{" +
                "choose_id=" + choose_id +
                ", endtime='" + endtime + '\'' +
                ", status=" + status +
                ", grade_id=" + grade_id +
                '}';
    }
}
