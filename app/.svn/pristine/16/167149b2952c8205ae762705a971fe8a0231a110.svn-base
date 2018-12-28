package app.apiservice;

import app.entity.content.BaseContent;
import app.entity.content.Comment;
import app.entity.json.ContentBaseView;
import app.entity.json.CourseBaseView;
import app.entity.json.KnowledgeContent;
import app.entity.json.SearchResultView;
import app.entity.smart.CourseBaseInfo;
import app.entity.smart.Knowledge;
import app.entity.user.Collect;
import app.entity.user.PassKnowledge;
import app.packet.error.ErrorEnum;
import app.packet.request.CommonPacket;
import app.packet.response.CommonMessage;
import app.repository.*;
import app.service.WisdomTreeService;
import com.google.gson.Gson;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@ControllerAdvice
@RequestMapping(path = "/api/content")
public class Content {
    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LearntRepository learntRepository;

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private WisdomTreeService wisdomTreeService;

    @Autowired
    private PassKnowledgeRepository passKnowledgeRepository;

    @Autowired
    private CourseBaseInfoRepository courseRepository;

    @Autowired
    private KnowledgeRepository knowledgeRepository;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private Gson gson = new Gson();

    @RequestMapping(path = "/fetch", method = RequestMethod.POST)
    @Transactional
    public CommonMessage fetch(@RequestBody CommonPacket<String> packet) {
        String contentId = packet.getData();
        BaseContent content = contentRepository.findOne(contentId);
        if (content == null) {
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }
        content.readIncrease();
        contentRepository.save(content);
        List<Comment> comments = commentRepository.findByResourceId(content.getResourcesId());
        Map<String, Object> param = new HashMap<>();
        param.put("content", content);
        param.put("comments", comments);
        wisdomTreeService.learn(content.getId(), packet.getUsername(), content.getContentType());
        Collect collect = collectRepository.findByUserIdAndResourcesId(packet.getUsername(), content.getResourcesId());
        if (collect != null && collect.getState().equals("t")) {
            content.setCollect(true);
        } else {
            content.setCollect(false);
        }
        return CommonMessage.success(param);
    }

    @RequestMapping(path = "/fetchCourse", method = RequestMethod.POST)
    @Transactional
    public CommonMessage fetchCourse(@RequestBody CommonPacket<Long> packet) {
        Long courseId = packet.getData();
        CourseBaseInfo course = courseRepository.findOne(courseId);
        if (course == null) {
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }
        List<Comment> comments = commentRepository.findByResourceId(course.getId()+"");
        Map<String, Object> param = new HashMap<>();
        param.put("course", course);
        param.put("comments", comments);
//        wisdomTreeService.learn(course.getId()+"", packet.getUsername(), "course");
        Collect collect = collectRepository.findByUserIdAndResourcesId(packet.getUsername(), course.getId()+"");
        if (collect != null && collect.getState().equals("t")) {
            course.setCollect(true);
        } else {
            course.setCollect(false);
        }
        course.setComment(true);
        return CommonMessage.success(param);
    }

    @RequestMapping(path = "/search", method = RequestMethod.POST)
    @Transactional
    public CommonMessage search(@RequestBody CommonPacket<Map<String, String>> packet) {
        Map<String, String> d = packet.getData();
        String keyWord = d.getOrDefault("keyWord", null);
        String type = d.getOrDefault("type", null);
        if (keyWord == null) {
            return CommonMessage.success();
        } else {
            keyWord = "%" + keyWord + "%";
            Set<String> arts = new HashSet<>();
            Set<Long> courses = new HashSet<>();
            List<PassKnowledge> pros = passKnowledgeRepository.findByUserId(packet.getUsername());
            //通过职业查找具体的知识点
            for (PassKnowledge p : pros) {
                List<Knowledge> knowledge = knowledgeRepository.findByProfessionId(p.getProfessionId());
                for (Knowledge k : knowledge) {
                    String contents = k.getContents();
                    KnowledgeContent content = gson.fromJson(contents, KnowledgeContent.class);
                    if (type == null || type.equals("article") || type.equals("video")){
                        for (ContentBaseView c : content.getContents()) {
                            if (!StringUtils.isEmpty(c.getId())) {
                                arts.add(c.getId());
                            }
                        }
                    }

                    if (type == null || type.equals("course")) {
                        for (CourseBaseView courseBaseView : content.getCourses()) {
                            if (!StringUtils.isEmpty(courseBaseView.getId())) {
                                try {
                                    courses.add(Long.valueOf(courseBaseView.getId()));
                                }catch (NumberFormatException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            Set<SearchResultView> searchResultViews = new HashSet<>();
            if (!arts.isEmpty()) {
                searchResultViews.addAll(contentRepository.search(keyWord, arts));
            }
            if (!courses.isEmpty()){
                searchResultViews.addAll(courseRepository.search(keyWord, courses));
            }
            return CommonMessage.success(searchResultViews);
        }
    }
}
