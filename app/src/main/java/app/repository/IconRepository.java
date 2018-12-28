package app.repository;

import app.entity.content.IconManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gongmingbo on 2018/11/26.
 */
public interface IconRepository extends JpaRepository<IconManagement,String>{
    Page<IconManagement> findByStateAndNameLike(Pageable pageable,boolean state,String name);
    Page<IconManagement> findByState(Pageable pageable,boolean state);
    List<IconManagement> findByName(String name);
}
