package app.repository;
import app.entity.user.School;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
	  //通过学校查询学院
        //查询所有学校
       List<School> findByPidIsNull();
       //通过pid查询所有的子类
       List<School> findByPid(String pid);
       //通过orgid查找父类pid
       School findByOrgId(String orgId);
       //根据当年年份查询所有年级
       List<School> findByPidAndOrgNameInOrderByOrgName(String pid,List<String> year);
      //通过学院,学校查询专业
      // List<School> findBySchoolCollegeAndSchoolName(String schoolCollege,String schoolName);
       //通过专业，学院,学校查询年级
      // List<School> findBySchoolMajorAndSchoolCollegeAndSchoolName(String schoolMajor,String schoolCollege,String schoolName);
       //@Query(value = "update app_school_base_info e set course_template= :ct where e.school_id = :schoolId ",nativeQuery = true)
      // Integer update(@Param("ct")JSONObject ct, @Param("schoolId")String schoolId);
       // School findBySchoolId(String schoolId);
}
