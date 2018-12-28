package app.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table(name = "app_ranking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ranking {

    @Id
    @GeneratedValue(generator = "rank_id")
    @GenericGenerator(name = "rank_id", strategy = "uuid")
    private String id;

    private String professionId;

    private String userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    private int energy;

    private int learntCourseCount;

    private int learntArticleCount;

    private int learntVideoCount;

    private int rank;

    public Ranking(String professionId, String userId, int energy, int learntCourseCount, int learntArticleCount, int learntVideoCount){
        this.professionId = professionId;
        this.userId = userId;
        this.energy = energy;
        this.learntArticleCount = learntArticleCount;
        this.learntCourseCount = learntCourseCount;
        this.learntVideoCount = learntVideoCount;
    }
}
