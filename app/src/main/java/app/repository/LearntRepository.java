package app.repository;

import app.entity.user.Learnt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearntRepository extends JpaRepository<Learnt, String > {
    List<Learnt> findByUserId(String userId);
    //查看学习记录
    List<Learnt> findByUserIdAndTypeIn(String userId,String type);
}
