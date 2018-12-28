package app.entity.content;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 历史记录表
 * @author gongmingbo
 *
 */
@Entity
@Table(name="app_history_record")
public class HistoryRecord {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	private	String userId;
	private	String resourceId;
	private	String remark;
	private	Timestamp startTime;
	private	Timestamp endTime;
	@Override
	public String toString() {
		return "HistoryRecord [id=" + id + ", userId=" + userId + ", ResourceId=" + resourceId + ", remark=" + remark
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
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
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		resourceId = resourceId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
}
