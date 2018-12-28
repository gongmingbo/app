package app.repository;

import app.entity.user.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, String > {

    @Query("select rank from Ranking rank where rank.professionId = :pro and rank.user.school.parent.parent.parent.pid = :school")
    List<Ranking> findSchoolRank(@Param("pro") String proId, @Param("school") String school);

//    @Query("select rank from Ranking rank where rank.professionId = :pro order by rank.rank")
    Page<Ranking> findByProfessionIdOrderByRankAsc(String proId, Pageable pageable);

    Ranking findRankingByUserIdAndProfessionId(String user, String pro);
}
