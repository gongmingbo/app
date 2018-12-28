package app.entity.json.wt;

import lombok.Data;

import java.util.List;

@Data
public class KnowledgePointView {
    public static final String ACHIEVED = "achieved";
    public static final String AVAILABLE = "available";
    public static final String LOCKED = "locked";

    private String id;
    private String name;
    private String desc;
    private List<KnowledgeResourceView> resources;
    private int energy;

    // status can be one of 'achieved', 'available', 'locked'
    private String status;

    private String source;

    private List<List<String>> pres;

    private List<String> allPres;

}
