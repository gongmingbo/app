package app.repository;

import app.entity.content.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;
@Repository
public interface TagRepository extends JpaRepository<Tags, String > {
    List<Tags> findByTagNameAndState(String name,boolean state);
    //List<Tags> findAll();
    List<Tags> findByStateOrderById(boolean state);
    @Query(value = "select * from  app_tags ORDER BY id limit :size offset :page where state=true",nativeQuery = true)
    List<Tags> pageAll(@Param("page") int page,@Param("size") int size);

    Page<Tags> findByIdNotInAndStateInOrderById(List<Long> ids,boolean state, Pageable pageable);

    List<Tags> findByIdNotInAndStateInOrderById(List<String> ids,boolean state);
}
