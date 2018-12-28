package app.entity.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 我的收藏
 * @author gongmingbo
 *
 */
@Entity
@Data
@Table(name="app_my_collect")
public class Collect {
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private    Long id;
	 private	String userId;
	 private	String resourcesId;
	 private	String remark ;
	 private    String state;
	 private 	String type;
	 private Timestamp time;
}
