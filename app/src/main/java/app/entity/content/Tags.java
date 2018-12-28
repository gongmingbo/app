package app.entity.content;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 标签库
 * @author gongmingbo
 *
 */
@Entity
@Table(name="app_tags")
@Data
public class Tags {
	@Id
	@GeneratedValue(generator = "content_id")
	@GenericGenerator(name = "content_id", strategy = "uuid")
	private String id;
	private String tagName;
	private String remark;
	private String parentId;
	private boolean state; //true 正常使用 ，false 表示删除

}
