package com.example.easts.demo.JavaBeans;

/**
 * Created by easts on 2017/12/22.
 */

public class CourseBean {
    //课程学年信息
    private String schoolYear;
    //课程学期信息
    private String schoolTerm;
    //课程名称
    private String courseName;
    //课程性质
    private String courseType;
    //课程学分
    private String credit;
    //课程绩点
    private String point;
    //课程成绩
    private String achievement;

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSchoolTerm() {
        return schoolTerm;
    }

    public void setSchoolTerm(String schoolTerm) {
        this.schoolTerm = schoolTerm;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }
}
