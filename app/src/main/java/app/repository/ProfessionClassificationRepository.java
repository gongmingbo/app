package app.repository;

import app.entity.smart.ProfessionClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gongmingbo on 2018/11/14.
 */
@Repository
public interface ProfessionClassificationRepository  extends JpaRepository<ProfessionClassification,String>{
}
