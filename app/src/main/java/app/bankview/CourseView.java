package app.bankview;

import app.entity.smart.CourseBaseInfo;
import app.entity.user.School;
import app.packet.Pagination;
import app.repository.CourseBaseInfoRepository;
import app.repository.SchoolRepository;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/web/course")
public class CourseView {
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private CourseBaseInfoRepository courseBaseInfoRepository;
   // @Autowired
   // private CourseTemplateService courseTemplateService;

    /**
     * 返回必修HTML
     *
     * @param request
     * @return
     */
    @RequestMapping("/required")
    public String index(HttpServletRequest request) {
        List<School> list = schoolRepository.findByPidIsNull();
        request.setAttribute("list", list);
        request.setAttribute("type",ConstantsKey.require_course);
        return "required2";
    }

    /**
     * 返回选修HTML
     *
     * @param request
     * @return
     */
    @RequestMapping("/elective")
    public String elective(HttpServletRequest request) {
        List<School> list = schoolRepository.findByPidIsNull();
        request.setAttribute("list", list);
        request.setAttribute("type",ConstantsKey.selcct_course);
        return "elective";
    }

    /**
     * 获取学院 获取专业 获取年级
     *
     * @param request
     * @return
     */
    @RequestMapping("/get/college")
    @ResponseBody
    public String getCollege(HttpServletRequest request) {
        String pid = request.getParameter("pid");
        List<School> list = schoolRepository.findByPid(pid);
        return JSON.toJSONString(list);
    }
    /**
     * 获取课程表
     * @param request
     * @return
     */
    @RequestMapping("/get/coursetable")
    @ResponseBody
    public String getCourseTable(HttpServletRequest request) {
        String orgId = request.getParameter("schoolId");
        String type = request.getParameter("type");
        String pagNum = request.getParameter("pageNum");
        String serchName=request.getParameter("serchName");
        Page<CourseBaseInfo> list ;
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable=new PageRequest(Integer.parseInt(pagNum)-1,10,sort);
       if (!StringUtils.isEmpty(serchName)){
           list=courseBaseInfoRepository.findByOrgIdAndCourseTypeAndStateAndCourseNameLike(pageable,orgId, type, "t","%"+serchName+"%");
       }else {
           list = courseBaseInfoRepository.findByOrgIdAndCourseTypeAndState(pageable, orgId, type, "t");
       }
        Pagination pagination = new Pagination();
        pagination.setPage(Integer.parseInt(pagNum));
        pagination.setPageSize(10);
        pagination.setTotalPage(list.getTotalPages());
        Map<String, Object> map = new HashMap<>();
        map.put("data", list.getContent());
        map.put("page", pagination);
        return JSON.toJSONString(map);
    }
    /**
     * 获取课程表
     *
     * @param request
     * @return
     *//*
    @RequestMapping("/get/coursetable")
    @ResponseBody
    public String getCourseTable(HttpServletRequest request) {
        String schoolId = request.getParameter("schoolId");
        String type = request.getParameter("type");
        List<CourseBaseInfo> list = new ArrayList<>();
       // if (ConstantsKey.selcct_course.equals(type)) {
            list = courseBaseInfoRepository.findByOrgIdAndCourseTypeAndState(schoolId, type, "t");
            return JSON.toJSONString(list);
      //  } else {
            //list = courseBaseInfoRepository.findBySchoolIdAndCourseTypeAndState(schoolId, ConstantsKey.require_course, "t");
       // }
     /*   Map<String, CourseTable> map = new HashMap<>();
        CourseTable ct;
        // 构建课程小节数
        for (int i = 1; i <= ConstantsKey.COURSE_NUMBER.size(); i++) {
            ct = new CourseTable(i + "");
            ct.setNumberName(ConstantsKey.COURSE_NUMBER.get(i + ""));
            map.put(i + "", ct);
        }
        if (list != null && list.size() > 0) {
            for (CourseBaseInfo c : list) {
                String number = c.getSectionNumber();
                ct = map.get(number);
                // 设置周一到周日的课程
                if (c.getWeek().equals("1")) {
                   ct.getMonday().add(c);
                } else if (c.getWeek().equals("2")) {
                  ct.getTuesday().add(c);
                } else if (c.getWeek().equals("3")) {
                   ct.getWednesday().add(c);
                } else if (c.getWeek().equals("4")) {
                   ct.getThursday().add(c);
                } else if (c.getWeek().equals("5")) {
                   ct.getFriday().add(c);
                } else if (c.getWeek().equals("6")) {
                   ct.getSaturday().add(c);
                } else if (c.getWeek().equals("7")) {
                   ct.getSunday().add(c);
                }
               map.put(number, ct);
            }
        }
        System.out.println(JSON.toJSONString(map));
        return JSON.toJSONString(map);
    }*/


    @RequestMapping("/add/course")
    @ResponseBody
    @Transactional
    public String addCourse(HttpServletRequest request, CourseBaseInfo courseBaseInfo) {
          courseBaseInfo.setCourseCreationTime(new Timestamp(new Date().getTime()));
          courseBaseInfo.setState("t");
          String tag=courseBaseInfo.getCourseTags();
        /*  String []tags=tag.split(",");
          tag=JSON.toJSONString(tags);
          courseBaseInfo.setCourseTags(tag);*/
        courseBaseInfo= courseBaseInfoRepository.saveAndFlush(courseBaseInfo);
          return JSON.toJSONString(courseBaseInfo);
    }
    /**
     * 获取单个课程
     * @param request
     * @return
     */
    @RequestMapping("/get/course")
    @ResponseBody
    public String getCourse(HttpServletRequest request) {
        String id = request.getParameter("id");
        List<CourseBaseInfo> list = courseBaseInfoRepository.findById(Long.parseLong(id));
        return JSON.toJSONString(list);
    }

    /**
     * 删除课程
     *
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Transactional
    public String deleteCourse(HttpServletRequest request) {
        String id = request.getParameter("id");
        String schoolId = request.getParameter("schoolId");
        String ids[] = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            // courseBaseInfoRepository.delete(Long.parseLong(ids[i]));
            CourseBaseInfo baseInfo = courseBaseInfoRepository.findOne(Long.parseLong(ids[i]));
            baseInfo.setState("f");
            courseBaseInfoRepository.saveAndFlush(baseInfo);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
     //   courseTemplateService.courseUpdate(schoolId);
        return JSON.toJSONString(map);
    }
    @RequestMapping("/coursecode")
    @ResponseBody
    public String distinctByCourseCode(HttpServletRequest request){
        String courseCode=request.getParameter("courseCode");
        List<CourseBaseInfo> list = courseBaseInfoRepository.findByCourseCode(courseCode);
        Map<String, Object> map = new HashMap<>();
        if (list!=null&&list.size()>0){
            map.put("state", true);
        }else {
            map.put("state", false);
        }
        return JSON.toJSONString(map);
    }

    public static void main(String[] args) {
    }
}
