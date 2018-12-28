package app.entity.user;

import app.config.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "app_users_learnt")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
@Data
public class Learnt {

    @Id
    @GeneratedValue(generator = "learnt_id")
    @GenericGenerator(name = "learnt_id", strategy = "uuid")
    private String id;

    private String userId;

    private String learntId;

    private String type = "article";

    private int energy;

    private String name;

    private Timestamp endTime;

    private Timestamp learntTime;

    private Boolean liked;

    private Boolean collect;

    @Transient
    private String time;

}
