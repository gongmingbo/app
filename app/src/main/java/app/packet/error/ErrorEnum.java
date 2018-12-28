package app.packet.error;

public enum ErrorEnum {
    SERVER_INTERNAL_ERROR(500, "服务器内部错误"),
    TOKEN_INVALID(100, "无效的token或者已经过期"),
    USER_NOT_EXISTS(101, "用户不存在"),
    USER_PASSWORD_INVALID(102, "用户密码错误"),
    COURSE_INVALID(103, "未完善学校信息(无法使用计划功能),请前往个人中心完善"),
    COURSE_SYLLABUS_INVALID(105, "课程表未填写完整"),
    USER_ALREADY_EXISTS(108, "该手机已被注册"),
    SMS_GENERATE_FAILED(107, "短信验证码发送失败"),

    COURSE_MAJOR_INVALID(106,"暂无专业课程"),
    COURSE_REMINDER_INVALID(120,"暂未设置提醒"),
    COURSE_ELECTIVE_INVALID(111,"暂无选修课程"),
    COURSE_MAJOR_TYPE_INVALID(112,"未传入对应的必修课程"),
    COURSE_ELECTIVE_TYPE_INVALID(109,"未传入对应的选修课程"),
    COURSE_ID_INVALID(110,"课程编号为空"),
    INVALID_FILE(104,"无效的文件"),
    SMS_CODE_ERROR(111, "短信验证码错误"),
    PERSON_INVALID(112,"未输入内容"),
    SCHOOL_INVALID(113,"暂无学校"),
    SCHOOL_GRADE_INVALID(114,"暂无学院或专业或年级信息"),
    PERSON_PASSWORD_INVALID(115,"原密码输入错误"),
    TAGS_INVALID(116,"暂无标签"),
    CONTENT_COLLECT_INVALID(117,"暂无收藏"),
    CONTENT_RESOURCE_INVALID(203,"资源id不正确"),
    ADMIN_PERMISSION(201,"暂无权限"),
    COMMENT_MY_INVALID(202,"该评论不存在"),
    COMMENT_INVALID(204,"暂无评论"),
    CONTENT_KNOWLEDGE_INVALID(119,"暂无职业资源推荐或未选择职业"),
    OBJECT_ALREADY_IN_USE(222, "该对象正在被其他对象使用，无法删除"),
    PERSON_IMAGE_INVALID(118,"头像上传失败"), INVALID_PARAM(120, "无效的参数");

    private final int code;
    private final String message;
    ErrorEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}