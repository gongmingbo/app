package app.entity.json.wt;

import lombok.Data;

import java.util.Optional;

@Data
public class KnowledgeResourceView {
    private String id;
    private String name;
    private String url;
    private String type;
    private int energy;
    private boolean learnt;
    private long endTime;

    public static final String COURSE = "course";
    public static final String ARTICLE = "article";
    public static final String VIDEO = "video";

    public boolean isCourse(){
        return Optional.ofNullable(type).orElse("").equals(COURSE);
    }

    public boolean isVideo(){
        return Optional.ofNullable(type).orElse("").equals(VIDEO);
    }

    public boolean isArticle(){
        return Optional.ofNullable(type).orElse("").equals(ARTICLE);
    }
}
