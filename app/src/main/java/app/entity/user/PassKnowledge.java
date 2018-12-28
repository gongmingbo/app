package app.entity.user;

import app.config.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

/**
 * 已学知识点表
 *
 * @author gongmingbo
 */
@Entity
@Table(name = "app_my_knowledge")
@Data
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
public class PassKnowledge {

    @Id
    @GeneratedValue(generator = "pass_id")
    @GenericGenerator(name = "pass_id", strategy = "uuid")
    private String id;

    private String userId;

    private String knowledgeId;

    private String professionId;

    private int energy;

    private String remark;

    int learntCourseCount;

    int learntArticleCount;

    int learntVideoCount;

    @Column(columnDefinition="jsonb")
    @Type(type = "JsonDataUserType")
    private String  plan;       // mapping type is app.entity.json.wt.WisdomTreeView

    @Column(columnDefinition="jsonb")
    @Type(type = "JsonDataUserType")
    private String learnt;       // mapping type is List<app.entity.user.Learnt>
}
