package app.repository;

import app.entity.content.BaseContent;
import app.entity.content.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findById(Long id);

    List<Comment> findByResourceId(String resourceId);

    Page<Comment> findByResourceIdAndStateInOrderByCommentTimeDesc(Pageable page, String resourceId,String state);
}
