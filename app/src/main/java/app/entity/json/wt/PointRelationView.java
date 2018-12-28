package app.entity.json.wt;

import lombok.Data;

@Data
public class PointRelationView {
    private String from;
    private String to;
    private boolean traveled;
}
