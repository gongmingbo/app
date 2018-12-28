package app.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import app.config.JsonDataUserType;

/**
 * 课程表
 * @author gongmingbo
 *
 */
@Table(name="app_course_timetable")
@Entity
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
public class CourseTimeTable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String userId;
	 @Column(columnDefinition="jsonb")
	 @Type(type = "JsonDataUserType")
	private String courseTemplate;//上学期
	@Column(columnDefinition="jsonb")
	@Type(type = "JsonDataUserType")
	private String courseTemplateDown;//下学期

	public String getCourseTemplateDown() {
		return courseTemplateDown;
	}

	public void setCourseTemplateDown(String courseTemplateDown) {
		this.courseTemplateDown = courseTemplateDown;
	}


	@Override
	public String toString() {
		return "CourseTimeTable [id=" + id + ", userId=" + userId + ", courseTemplate=" + courseTemplate + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCourseTemplate() {
		return courseTemplate;
	}
	public void setCourseTemplate(String courseTemplate) {
		this.courseTemplate = courseTemplate;
	}
	 
	
}
