
package app.repository;

import app.entity.content.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gongmingbo on 2018/11/15.
 */
public interface AttachmentRepository extends JpaRepository<Attachment,Long>{
}

