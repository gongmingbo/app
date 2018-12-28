package app.entity.content;

import app.entity.user.User;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 评论表
 *
 * @author gongmingbo
 */
@Entity
@Table(name = "app_comment")
@Data
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String resourceId;
    /*@ManyToOne
    @JoinColumn(name = "resourceId", nullable = false, referencedColumnName = "resourcesId",insertable = false,updatable = false)
   // @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    private BaseContent resource;*/
    private Integer parentId;
    private String remark;
    private String commentContent;
    private String commentTime;
    private String commentUserId;
	@ManyToOne
	@JoinColumn(name = "commentUserId", nullable = false, referencedColumnName = "userId",insertable = false,updatable = false)
	//@Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
	private User user;
    private String state;
	    
}
