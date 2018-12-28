/*
package app.service;

import app.bankview.ConstantsKey;
import app.entity.coursetime.CourseTemplate;
import app.entity.coursetime.CoursesTemplate;
import app.entity.smart.CourseBaseInfo;
import app.entity.user.School;
import app.repository.CourseBaseInfoRepository;
import app.repository.SchoolRepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.objects.NativeNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * Created by gongmingbo on 2018/11/9.
 *//*

@Service
public class CourseTemplateService {
    @Autowired
    private CourseBaseInfoRepository courseBaseInfoRepository;
    @Autowired
    private SchoolRepository schoolRepository;
  //  private static String startFirstDay="2018-09-01";
    //生成课程模板
    @Transactional
    public void courseUpdate(String schoolId) {
        List<CourseBaseInfo> list = courseBaseInfoRepository.findBySchoolIdAndCourseTypeAndState(schoolId, ConstantsKey.require_course,"t");
        String term=schoolId.substring(0,1);
        String startFirstDay="2018-09-01";
        if (ConstantsKey.termUp.equals(term)){
            startFirstDay="2018-09-01";
        }else if (ConstantsKey.termDown.equals(term)){
            startFirstDay="2018-03-01";
        }else {
            return;
        }
        List<CourseTemplate> courseTemplateList = new ArrayList<>();
        for (CourseBaseInfo c : list) {
            CourseTemplate ct = new CourseTemplate();
            ct.setId(c.getId().toString());
            ct.setName(c.getCourseName());
            ct.setTeacher(c.getCourseTeachr());
            int[] arr = {Integer.parseInt(c.getSectionNumber())};
            ct.setClassNo(arr);
            int[] arrs = {Integer.parseInt(c.getWeek())};
            ct.setDayOfWeek(arrs);
            ct.setWeeks(getWeeks(c.getWeeks()));
            ct.setClassroom(c.getClassPlace());
            ct.setDates(getWeeks(c,startFirstDay));
            ct.setReminder(0);
            ct.setType(ConstantsKey.require_course);
            courseTemplateList.add(ct);
        }
        CoursesTemplate coursesTemplate = new CoursesTemplate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = simpleDateFormat.parse(startFirstDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        coursesTemplate.setFirstday(date);
        coursesTemplate.setCourses(courseTemplateList);
        schoolId=schoolId.substring(1);
        School school=schoolRepository.findByOrgId(schoolId);
        if (ConstantsKey.termUp.equals(term)){
            school.setCourseTemplate(JSON.toJSONString(coursesTemplate));
        }else if (ConstantsKey.termDown.equals(term)){
            school.setCourseTemplateDown(JSON.toJSONString(coursesTemplate));
        }
        schoolRepository.saveAndFlush(school);

    }

    //生成周数
    public static int[] getWeeks(String weeks) {
        String data[] = weeks.split("-");
        int start = Integer.parseInt(data[0]);
        int end = Integer.parseInt(data[1]);
        int result[] = new int[end - start + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = start++;
        }
        return result;
    }

    //生成日期
    public static List<String> getWeeks(CourseBaseInfo c,String startFirstDay) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> result = new ArrayList<>();
        try {
            Date date = simpleDateFormat.parse(startFirstDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int arr[] = getWeeks(c.getWeeks());
            int weekYear = calendar.getWeekYear();
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
            int dayOfWeek = getDayOfWeek(Integer.parseInt(c.getWeek()));
            calendar.setWeekDate(weekYear, weekOfYear, dayOfWeek);
            for (int i = 0; i < arr.length; i++) {
                String d = simpleDateFormat.format(calendar.getTime());
                calendar.add(Calendar.DATE, 7);
                result.add(d);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getDayOfWeek(int week) {
        int n = 0;
        switch (week) {
            case 1:
                n = 2;
                break;
            case 2:
                n = 3;
                break;
            case 3:
                n = 4;
                break;
            case 4:
                n = 5;
                break;
            case 5:
                n = 6;
                break;
            case 6:
                n = 7;
                break;
            case 7:
                n = 1;
                break;
            default:
                break;
        }
        return n;

    }

    public static void main(String[] args) {
        */
/*int arr[]=getWeeks("1-20");
        System.out.println(arr.length);
        for (int i = 0; i <arr.length ; i++) {
          System.out.println(arr[i]);*//*

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse("2018-09-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekYear = calendar.getWeekYear();
        int weekofyear = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setWeekDate(2018, weekofyear, 7);
        System.out.println("shijai" + simpleDateFormat.format(calendar.getTime()));
    }


}
*/
