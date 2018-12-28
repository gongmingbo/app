package app.entity.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentBaseView {
    private String title;
    private String id;
    private int energy;
    private String type;
    private Long publishTime;
    public ContentBaseView(String id, String title, String type, Date publishTime){
        this.title = title;
        this.id = id;
        this.type = type;
        this.publishTime = publishTime.getTime();
    }
}
