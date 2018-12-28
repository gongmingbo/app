package app.entity.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseBaseView {
    private String id;
    private String name;
    private int energy;
//    private Timestamp endTime;
    public CourseBaseView(String id, String name){
        this.name = name;
        this.id = id;
    }
    private Long endTime;//存时间戳
}
