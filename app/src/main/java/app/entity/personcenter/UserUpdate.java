package app.entity.personcenter;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//个人中心头像等
@Data
public class UserUpdate {
    private String userImage;
    private String userName;
    private List<UserTags>  tag;
}
