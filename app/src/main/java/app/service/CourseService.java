package app.service;

import app.entity.content.BaseContent;
import app.entity.coursetime.CourseTemplate;
import app.entity.coursetime.CoursesTemplate;
import app.entity.json.ContentBaseView;
import app.entity.smart.CourseBaseInfo;
import app.entity.user.CourseTimeTable;

import java.util.List;

/*
* 学生课程表查询接口
* */
public interface CourseService {
    CourseTimeTable getCourseTemplate(String username);

    List<CourseBaseInfo> getAllCompulsory();

    List<CourseBaseInfo> getAllElective();

    List<BaseContent> getAllContent();

    List<ContentBaseView> getAllContentBaseView();

    //添加或重置课程表
    CoursesTemplate setTerm();

    //查询专业课程
    List<CourseBaseInfo> getMajorCourses(String username,String name);

    //查询选修课程
    List<CourseBaseInfo> getElective(String username,String name);

    //根据课程id查询课程详细信息
    CourseBaseInfo getCourseBase(String id);

    //查询课程表
    CoursesTemplate queryCourse(CourseTimeTable course);

    //覆盖课程
    CoursesTemplate queryWeek(String type,String id,int dayOfWeek,int[] classNo, int[] weeks, CoursesTemplate coursesTemplate);
}
