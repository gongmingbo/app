package app.repository;

import app.entity.user.CourseTimeTable;
import app.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
/*
* app学生课程表查询
* */
public interface CourseSyllabusRepository extends JpaRepository<CourseTimeTable, Long> {
    CourseTimeTable findByUserId(String id);
}
