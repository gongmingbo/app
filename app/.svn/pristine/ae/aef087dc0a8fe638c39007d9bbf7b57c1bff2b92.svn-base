package app.entity.smart;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import app.config.JsonDataUserType;

/**
 * 课程基础信息表
 *
 * @author gongmingbo
 */
@Entity
@Table(name = "app_course_base_info")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
@Data
public class CourseBaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //课程编号
    private String courseCode;
    private String courseName;
    private String courseType;
    //学分
    private String courseCredit;
    //学时
    private String courseHour;
    //考核方式
    private String assessmentMethod;
    //课程简绍
    private String  CourseIntroduction;
    private Timestamp courseCreationTime;
    private String courseTeachr;
   // private String sectionNumber;//表示上课节数
  //  private String classPlace;
    private String powerPoint;
    @Column(columnDefinition = "jsonb")
    @Type(type = "JsonDataUserType")
    private String courseTags;
    private String orgId;
   // private String weeks;//表示周数
  //  private String week;//表示星期几
    private String state;//t表示正常，f表示删除状态
    @Transient
    private boolean collect;
    @Transient
    private boolean comment;
    @Transient
    private String remark;

}
