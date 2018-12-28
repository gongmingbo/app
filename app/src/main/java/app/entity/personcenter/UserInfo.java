package app.entity.personcenter;

import lombok.Data;

import java.util.List;

//用户个人信息实体
@Data
public class UserInfo {
    private String userId;
    private String userName;
    private String sex;
    private String userImage;
    private List<UserTags>  tag;
    private String school;//学校
    private String schoolId;
    private String college;//学院
    private String collegeId;
    private String major;//专业
    private String majorId;
    private String grade;//班级
    private String gradeYear;//班级
    private String gradeId;
    private String telephone;
    private List<String> pros;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", userImage='" + userImage +'\'' +
                ", tag=" + tag +
                ", school='" + school + '\'' +
                ", telephone='" + telephone + '\'' +
                ", major='" + major + '\'' +
                ", college='" + college + '\'' +
                ", grade='" + grade + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", collegeId='" + collegeId + '\'' +
                ", majorId='" + majorId + '\'' +
                ", gradeYear='"+gradeYear+ '\'' +
                ", gradeId='" + gradeId + '\'' +
                '}';
    }
}


