package app.entity.personcenter;

import app.entity.content.BaseContent;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

@Data
public class MyCollectContent {
    private String publisherName;
    private BaseContent base;
    private List<Map<String,Object>> titleImage;
    private String publishTime;
}
