package app.service;

import app.entity.smart.Knowledge;
import app.entity.smart.ProfessionClassification;
import app.entity.user.PassKnowledge;

import java.util.List;

public interface CareerService {
    List<ProfessionClassification> getProfessions();

    List<String> getMyProfessions(String user);

    PassKnowledge getMyProfessions(String user, String pro);

    List<Knowledge> getKnowledgeOfProfession(String proId);

    List<Knowledge> getOtherKnowledgeOfProfession(String proId, String knowId);

    Knowledge getKnowledge(String id);

    int deleteKnowledge(String id);

    void saveKnowledge(Knowledge knowledge);
}
