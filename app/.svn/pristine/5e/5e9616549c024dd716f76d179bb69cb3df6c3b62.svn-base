package app.entity.smart;

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
 * 职业规划表
 * @author gongmingbo
 *
 */
//@Entity
//@Table(name = "app_profession_plan")
//@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
	//似乎不需要这个表了
public class ProfessionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String  planId;
	private String  professionId;
	 @Column(columnDefinition="jsonb")
	 @Type(type = "JsonDataUserType")
	private String  planIath;
	private String  remark;
	@Override
	public String toString() {
		return "ProfessionPlan [id=" + id + ", planId=" + planId + ", professionId=" + professionId + ", planIath="
				+ planIath + ", remark=" + remark + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getProfessionId() {
		return professionId;
	}
	public void setProfessionId(String professionId) {
		this.professionId = professionId;
	}
	public String getPlanIath() {
		return planIath;
	}
	public void setPlanIath(String planIath) {
		this.planIath = planIath;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
