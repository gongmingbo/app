package app.entity.user;

import app.config.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户基础信息表
 *
 * @author gongmingbo
 */
@Entity
@Table(name = "app_users_base_info")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
@Data
public class User {
    @Id
    @GeneratedValue(generator = "user_id")
    @GenericGenerator(name = "user_id", strategy = "uuid")
    private String userId;
    private String userName;
    private String userPassword;
    private String userSex;
    @Column(nullable = false, unique = true)
    private String userTelephone;
    private String shoolId;

    @ManyToOne
    @JoinColumn(name = "shoolId", referencedColumnName = "orgId", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private School school;
    private String userType;
    private String userImage;
    @Type(type = "JsonDataUserType")
    @Column(columnDefinition = "jsonb")
    private String userInterest;
    private String userEmail;
    private String weiChaId;
    private String remark;
}
