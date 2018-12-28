package app.entity.user;

import app.config.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 学校专业信息表
 * @author gongmingbo
 *
 */
@Entity
@Table(name="app_school_base_info")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
@Data
public class School implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	//private String schoolId;
	//private String schoolName;
	//private String schoolCollege;//院
//	private String schoolDepartment;//系
	//private String schoolMajor;//专业
	//private String schoolGrade;//年级
	//private String schoolClass;//班级
	private String orgId;
	private String orgName;
	private String pid;

	@ManyToOne
	@JoinColumn(name = "pid", referencedColumnName = "orgId", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private School parent;

	@Type(type = "JsonDataUserType")
	@Column(columnDefinition = "jsonb")
	private String CourseTemplate;//课程表模板上学期
	@Type(type = "JsonDataUserType")
	@Column(columnDefinition = "jsonb")
	private String CourseTemplateDown;//课程表模板下学期
	
}
