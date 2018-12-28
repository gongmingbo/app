package app.service;

import app.entity.user.Learnt;
import app.entity.user.PassKnowledge;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WisdomTreeService {
    List<PassKnowledge> getWisdomTree(String userId, String proId);

    void learn(String resourceId, String user, String type);

    void newTree(String user, String proId);

    List<Learnt> learntHistory(String userId, String proId);

    @Transactional
    void rank();

    @Transactional
    void checkCourse(String user);
}
