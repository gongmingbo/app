package app.repository;

import app.entity.smart.ProfessionClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProfessionRepository extends JpaRepository<ProfessionClassification, String> {
    @Query("select p from ProfessionClassification  p where p.active = true order by p.salary asc")
    List<ProfessionClassification> findAllOrderBySalary();

    ProfessionClassification findByProfessionName(String name);
}
