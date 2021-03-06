package app.entity.content;

import app.config.JsonDataUserType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 基础类容表（如文章，视频）
 *
 * @author gongmingbo
 */
@Entity
@Table(name = "app_content_info")
@TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class)
@Data
public class BaseContent implements Serializable {
    @Id
    @GeneratedValue(generator = "content_id")
    @GenericGenerator(name = "content_id", strategy = "uuid")
    private String id;
    private String resourcesId;
    private String contentTitle;
    private String contentType;
    private String contentDescription;
    @Column(columnDefinition = "text")
    private String contenBody;
    private String contentOrigin;
    private String publisherId;
    private Timestamp publishTime;
    @Type(type = "JsonDataUserType")
    @Column(columnDefinition = "jsonb")
    private String contentTag;
    @Column(columnDefinition = "text")
    private String contentTitleImage;
    private boolean contentAttachment;
    private int readCounts;

    private int likeCount;
    private String contentTop;
    private String remark;
    @OneToMany
    @JoinColumn(name = "resourcesId", nullable = false, referencedColumnName = "resourcesId", insertable = false, updatable = false)
    //  @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    private List<Attachment> attachments;
    private String professionId;
    private String state;
    private boolean comment;
    @Transient
    private boolean collect;

    public void readIncrease(){
        readCounts++;
    }

}
