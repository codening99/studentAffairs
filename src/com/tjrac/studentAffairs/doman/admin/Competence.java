package com.tjrac.studentAffairs.doman.admin;

/**
 * competence 权限表
 *
 * @author : xziying
 * @create : 2020-10-14 21:12
 */
public class Competence {
    Integer competence_id;  // 主键索引 自定义 参考md说明文件
    String name;            // 权限名称
    String explanation;     // 权限说明

    public Competence() {
    }

    public Competence(Integer competence_id, String name, String explanation) {
        this.competence_id = competence_id;
        this.name = name;
        this.explanation = explanation;
    }

    public Competence(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }

    public Integer getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(Integer competence_id) {
        this.competence_id = competence_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "Competence{" +
                "competence_id=" + competence_id +
                ", name='" + name + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
