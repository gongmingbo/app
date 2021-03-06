package app.service;

import app.entity.content.BaseContent;
import app.entity.content.Tags;
import app.entity.json.ContentBaseView;
import app.entity.personcenter.UserInfo;
import app.entity.user.School;
import app.entity.user.User;
import app.packet.request.CommonPacket;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface UserService {
    User getUser(String username);

    User getUserByTelephone(String mobile);

    boolean validatePassword(String username, String password);

    String getToken(String username, String deviceId);

    String validateAndRefreshToken(String username, String deviceId, String token);

    String validateAndRefreshToken(CommonPacket packet);

    boolean valid(String user, String messageId, String code) throws UnsupportedEncodingException;

    String sendSmsCode(String mobile) throws UnsupportedEncodingException;

    void save(User user);

    void newUser(String mobile, String password);

    //查询用户学校专业等基本信息
    UserInfo sendSchool(User user);

    //查询所有学校
    List<Map<String,String>> allSchool();

    //查询某一学校下的学院或专业或年级信息
    List<School> allCollege(String pid);

    //查询所有标签
    List<Tags> allTags1(int size);

    //查询所有标签排除选中标签后的
    List<Tags> allTags2(int size,List<String> ids);

    /*//查询所有标签总页数
    int total(int size);

    //查询所有标签总页数排除选中标签后的
    int total(int size,List<Long> ids);*/

    //用户头像图片上传
    String userImageUpload(@RequestParam("file") MultipartFile file);

    //查询用户所有收藏(分页)
    List<BaseContent> userMyCollect(String username,int page,int size);

    //查询用户所有收藏条数返回总页数
    int userMyCollectTotalPage(String username,int size);

    //查询所有年纪
    List<School> getGrate(List<School> list,String majorId);

    //获取年级大几上期下期
    String getTerm(String grate);
}
