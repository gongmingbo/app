package app.repository;

import app.entity.user.PassKnowledge;
import app.entity.user.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PassKnowledgeRepository extends JpaRepository<PassKnowledge, String > {
    PassKnowledge findByUserIdAndProfessionId(String user, String pro);

    List<PassKnowledge> findByUserId(String user);

    @Query("select p.professionId from PassKnowledge p where p.userId = :user")
    List<String> findProId(@Param("user") String user);

    @Query(value = "select new app.entity.user.Ranking(p.professionId, p.userId, p.energy, p.learntCourseCount, p.learntArticleCount, p.learntVideoCount) from PassKnowledge p where p.professionId = :pro order by p.energy desc ")
    List<Ranking> doRanking(@Param("pro") String pro);

    void deleteByUserIdAndProfessionId(String user, String pro);
}
