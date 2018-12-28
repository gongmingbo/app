package app.entity.json.wt;

import lombok.Data;

import java.util.List;

@Data
public class WisdomTreeView {
    private List<PointRelationView> relations;

    private List<KnowledgePointView> points;

    private int count;

    private int energy;

    private int floatingEnergy;

    private String proId;

    private String proName;

    public void addFloatingEnergy(int energy){
        floatingEnergy += energy;
    }

    public void countIncrease(){
        count++;
    }

    public void countIncrease(int c){
        count += c;
    }
}
