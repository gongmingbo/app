package app.service.impl;

import app.entity.content.BaseContent;
import app.entity.coursetime.CourseTemplate;
import app.entity.coursetime.CoursesTemplate;
import app.entity.json.ContentBaseView;
import app.entity.smart.CourseBaseInfo;
import app.entity.user.CourseTimeTable;
import app.repository.ContentRepository;
import app.repository.CourseBaseInfoRepository;
import app.repository.CourseSyllabusRepository;
import app.service.CourseService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @Autowired
    private  CourseSyllabusRepository courseSyllabusRepository;

    @Autowired
    private CourseBaseInfoRepository courseBaseInfoRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public CourseTimeTable getCourseTemplate(String username) {
        return courseSyllabusRepository.findByUserId(username);
    }

    /*****
     * 必修课
     * @return 所有必修课
     */
    @Override
    public List<CourseBaseInfo> getAllCompulsory() {
        return courseBaseInfoRepository.findByCourseTypeAndState("必修课", "t");
    }

    /****
     * 选修课
     * @return 所有选修课
     */
    @Override
    public List<CourseBaseInfo> getAllElective() {
        return courseBaseInfoRepository.findByCourseTypeAndState("选修课", "t");
    }

    @Override
    public List<BaseContent> getAllContent(){
        return contentRepository.findAll();
    }

    /***
     * 获取领域文章的基础信息
     * @return
     */
    @Override
    public List<ContentBaseView> getAllContentBaseView(){
        return contentRepository.findContentBaseView("t");
    }

    //查询课程表
    public CoursesTemplate queryCourse(CourseTimeTable course){
        //更新添课程表的学期（学期、周数、开学第一周星期一日期）
        CoursesTemplate coursesTemplate=setTerm();

        //查询课程
        Gson gson=new Gson();
        String data = course.getCourseTemplate();
        CoursesTemplate coursesT=gson.fromJson(data,CoursesTemplate.class);
        List<CourseTemplate> list=coursesT.getCourses();
        List<CourseTemplate> courseNow=new ArrayList<>();
        for(CourseTemplate t:list){
            if(t.getState()==0){
                courseNow.add(t);
            }
        }

        if(coursesTemplate.getFirstday()!=coursesT.getFirstday()){
            coursesTemplate.setCourses(list);
            course.setCourseTemplate(JSON.toJSONString(coursesTemplate));
            courseSyllabusRepository.saveAndFlush(course);
        }else{
            coursesTemplate=coursesT;
        }
        coursesTemplate.setCourses(courseNow);
        return  coursesTemplate;
    }
    /*
     * 添加或重置课程表的学期等
     * */
    public CoursesTemplate setTerm(){
        CoursesTemplate courses=new CoursesTemplate();
        //获取当前时间判断学期
        Date date=new Date();
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("E");
        Date first=null;//开学上课日期
        String title="";//学期
        String dayweek="";//开学上课星期几
        Date begin=null;
        Date end=null;
        String mouth1 = year.format(date)+"-08-01 00:00:00";
        String mouth2 = year.format(date)+"-02-15 00:00:00";
        try {
            begin=sdf.parse(mouth2);
            end=sdf.parse(mouth1);
            if(date.getTime()<begin.getTime()){
                first=simpleDateFormat.parse((Integer.parseInt(year.format(date))-1)+"-09-01");
                title=(Integer.parseInt(year.format(date))-1)+"-"+year.format(date)+"年度 上学期";
            }else if(date.getTime()>end.getTime()){
                first=simpleDateFormat.parse(year.format(date)+"-09-01");
                title=year.format(date)+"-"+(Integer.parseInt(year.format(date))+1)+"年度 上学期";
            }else{
                first=simpleDateFormat.parse(year.format(date)+"-03-01");
                title=(Integer.parseInt(year.format(date))-1)+"-"+year.format(date)+"年度 下学期";
            }
        }catch (Exception e){
            e.getMessage();
        }
        dayweek=formatter.format(first);
        courses.setTerm(title);
        courses.setFirstday(first.getTime());
        courses.setDayOfWeek(dayweek);
        courses.setWeeks(25);
        List<CourseTemplate> ct=new ArrayList<>();
        courses.setCourses(ct);
        return courses;
    }

    //查询专业课程
    public List<CourseBaseInfo> getMajorCourses(String username,String name){
        List<CourseBaseInfo> courses=courseBaseInfoRepository.majorCourses(username,"%"+name+"%");
        return courses;
    }

    //查询选修课程
    public List<CourseBaseInfo> getElective(String username,String name){
        List<CourseBaseInfo> courses=courseBaseInfoRepository.elective(username,"%"+name+"%");
        return courses;
    }

    //根据课程id查询课程详细信息
    public CourseBaseInfo getCourseBase(String id){
        List<CourseBaseInfo> base=courseBaseInfoRepository.findByCourseCode(id);
        return  base.get(0);
    }

    //覆盖课程
    public CoursesTemplate queryWeek(String type,String id,int dayOfWeek,int[] classNo,int[] weeks,CoursesTemplate coursesTemplate){
        List<CourseTemplate> course=coursesTemplate.getCourses();
        CourseTemplate ct=null;
        for(CourseTemplate c:course){
            if(id.equals(c.getId())||c.getState()==1||!c.getType().equals(type)){
                ct=null;
                continue;
            }else {
                if(classNo[0]==c.getClassNo()[0]&&dayOfWeek==c.getDayOfWeek()){
                    //ct=c;
                    List<Integer> w=new ArrayList<>();
                    int save=0;
                    for(int week:c.getWeeks()){
                        String a="不存在";
                        for(int i:weeks){
                            if(i==week){
                               a="存在";
                               break;
                            }
                        }
                        if(a.equals("不存在")){
                            w.add(week);
                        }else{
                            save+=1;
                        }
                    }
                    if(save==0){
                        ct=null;//没有覆盖
                    }else{
                        if(w.size()==0){
                            c.setWeeks(new int[0]);
                            c.setState(1);
                        }//有覆盖
                        else{
                            int[] d = new int[w.size()];
                            for(int i = 0;i<w.size();i++) {
                                d[i] = w.get(i);
                            }
                            c.setWeeks(d);
                        }
                        ct=c;
                        break;
                    }
                }else{
                    continue;
                }
            }
        }
        if(ct==null||ct.equals("")){
            return null;
        }else{
            return coursesTemplate;
        }
    }
}
