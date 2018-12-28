package app.repository;

import app.entity.smart.Knowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KnowledgeRepository extends JpaRepository<Knowledge, String > {
    @Query(value = "select k.contents from app.entity.smart.Knowledge k where k.professionId in (" +
            "select p.professionId from app.entity.user.PassKnowledge p where p.userId=:userId" +
            ")")
    List<String> recommendContent(@Param("userId") String userId);
    /*@Query(value = "select k.contents from app_knowledge k where k.profession_id in (" +
            "select p.profession_id from app_my_knowledge p where p.user_id=:userId" +
            ")",nativeQuery = true)*/
    List<Knowledge> findByProfessionId(String id);

    List<Knowledge> findByProfessionIdAndKnowledgeIdIsNot(String id, String knowledgeId);

    @Query(value = "select COUNT (*) from app_knowledge_prerequisite where prerequisite_id = :pre", nativeQuery = true)
    int countByPre(@Param("pre") String pre);

    Knowledge findByKnowledgeNameAndProfessionId(String name, String pro);

    Knowledge findByKnowledgeNameAndProfessionIdAndKnowledgeIdNot(String name, String pro, String id);
}
