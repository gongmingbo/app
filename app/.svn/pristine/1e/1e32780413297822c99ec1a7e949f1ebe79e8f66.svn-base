package app.entity.smart;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 职业分类表
 * @author gongmingbo
 *
 */
@Entity
@Table(name="app_profession_classification")
@Data
public class ProfessionClassification {
	@Id
	@GeneratedValue(generator = "pro_id")
	@GenericGenerator(name = "pro_id", strategy = "uuid")
	private String  professionId;
	private String 	professionName;
	private String 	professionDescription;
	private String 	futureProspects;
	private String 	industry;
	private String 	salary;
	private String 	parentId;
	private String 	remark;
	private int knowledgeNumber;
	private int energy;

	@Column(name="is_active")
	private boolean active;
}
