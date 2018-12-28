package app.entity.content;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by gongmingbo on 2018/11/26.
 */
@Entity
@Data
@Table(name = "app_icon")
public class IconManagement {
    @Id
    @GeneratedValue(generator = "content_id")
    @GenericGenerator(name = "content_id", strategy = "uuid")
    private String id;
    private String url;
    private String name;
    private Timestamp createTime;
    private boolean state; //true 正常使用 ，false 表示删除
}
