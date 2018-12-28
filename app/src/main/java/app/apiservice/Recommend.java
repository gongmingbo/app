package app.apiservice;

import app.entity.PageInfo;
import app.entity.content.BaseContent;
import app.entity.content.Comment;
import app.entity.json.ContentBaseView;
import app.entity.json.CourseBaseView;
import app.entity.json.KnowledgeContent;
import app.entity.smart.CourseBaseInfo;
import app.entity.smart.Knowledge;
import app.entity.user.Collect;
import app.entity.user.PassKnowledge;
import app.packet.error.ErrorEnum;
import app.packet.request.CommonPacket;
import app.packet.request.CommonPagePacket;
import app.packet.response.CommonMessage;
import app.repository.*;
import app.service.UserService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gongmingbo on 2018/11/12.
 * 课程推荐
 */
@RestController
@ControllerAdvice
@RequestMapping(path = "/api/recommend")
public class Recommend {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Gson gson = new Gson();
    @Autowired
    private UserService userService;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private PassKnowledgeRepository passKnowledgeRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private LearntRepository learntRepository;
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CourseBaseInfoRepository courseBaseInfoRepository;

    /**
     * 查看所有推荐课程
     */
    @RequestMapping(path = "/courseProfession", method = RequestMethod.POST)
    //@Transactional(readOnly = true)
    public CommonMessage recommend(@RequestBody CommonPagePacket<String> packet) {
        logger.info("userId is: " + packet.getUsername());
        logger.info("deviceId is: " + packet.getDeviceId());
        logger.info("token is: " + packet.getToken());
        logger.info("page is: " + packet.getPage());
        logger.info("size is: " + packet.getSize());
        logger.info("data is: " + packet.getData());
        String name = "";
        if (packet.getData() == null || packet.getData().equals("")) {
            name = "";
        } else {
            name = packet.getData();
        }
        CommonMessage message;
        //保存baseContent需要的resourceId
        List<Map<String, Object>> contentIds = new ArrayList<>();
        //保存选修课需要的id
        List<Map<String, Object>> courseIds = new ArrayList<>();
        //查询profession_id职业编号contents
        List<String> knowledge = knowledgeRepository.recommendContent(packet.getUsername());
        if (knowledge == null || knowledge.size() == 0) {
            message = CommonMessage.success("未选择职业!暂无推荐!");
        } else {
            //通过职业查找具体的知识点
            for (String p : knowledge) {
                KnowledgeContent knowledgeContent = gson.fromJson(p, KnowledgeContent.class);
                for (ContentBaseView c : knowledgeContent.getContents()) {
                    if (c.getId() == null || c.getId().equals("")) {
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", c.getId());
                        map.put("endTime", c.getPublishTime());
                        map.put("type", "0");
                        contentIds.add(map);
                    }
                }
                for (CourseBaseView c : knowledgeContent.getCourses()) {
                    if (c.getId() == null || c.getId().equals("")) {
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", c.getId());
                        map.put("endTime", c.getEndTime());
                        map.put("type", "1");//代表课程
                        courseIds.add(map);
                    }
                }
            }
            //获取size和page
            int size = packet.getSize();
            int page = packet.getPage();
            if ((contentIds == null || contentIds.size() == 0) && (courseIds == null || courseIds.size() == 0)) {
                message = CommonMessage.failure(ErrorEnum.CONTENT_KNOWLEDGE_INVALID);
                return message;
            }
            List<String> content = new ArrayList<>();
            List<Long> course = new ArrayList<>();
            for (Map<String, Object> m : contentIds) {
                content.add(m.get("id").toString());
            }
            for (Map<String, Object> m : courseIds) {
                course.add(Long.parseLong(m.get("id").toString()));
            }
            content = new ArrayList<>(new TreeSet<>(content));
            course = new ArrayList<>(new TreeSet<>(course));
            if(course==null||course.size()==0){
                course.add(Long.parseLong("0"));
            }
            String messages = "";
            //获取推荐文章和课程的未被删除的id集合
            List<BaseContent> contentList= contentRepository.countContent(content, "%" + name + "%");
            //获取课程未被删除的id集合
            List<CourseBaseInfo> courseList = courseBaseInfoRepository.countCourse(course, "%" + name + "%");
            if (contentList.size() == 0 && courseList.size() == 0 && !name.equals("")) {
                messages = "未找到相关内容!";
                name = "";
                contentList = contentRepository.countContent(content, "%" + name + "%");
                courseList = courseBaseInfoRepository.countCourse(course, "%" + name + "%");
            } else {
                messages = "";
            }
            //提取集合分别查询所有未被删除的的id集合
            content=new ArrayList<>();
            course=new ArrayList<>();
            for(BaseContent bc:contentList){
                content.add(bc.getId());
            }
            for(CourseBaseInfo cb:courseList){
                course.add(cb.getId());
            }
            //根据时间戳排序集合
            List<Map<String, Object>> pageList = new ArrayList<>();
            contentIds = compareCollection(contentIds, (List<Object>) (List) content);
            courseIds = compareCollection(courseIds, (List<Object>) (List) course);
            pageList.addAll(contentIds);
            pageList.addAll(courseIds);
            pageList = compareCollection(pageList, null);
            //获取总页数
            int totalPage = 0;
            long number = pageList.size();
            totalPage = (int) number / size;
            if (number % size > 0) {
                totalPage += 1;
            }

            //返回数据实体
            PageInfo<Map<String, Object>> basePage = new PageInfo<>();
            Map<String, Object> recommend = new HashMap<>();
            List<Object> list = new ArrayList<>();
            recommend.put("message", messages);
            if (page > totalPage || page == 0) {
                message = CommonMessage.success("无此页数");
            } else {
                int a = size;
                if (page == totalPage) {
                    a = pageList.size()-((page - 1) * size);
                }
                //排序集合通过size和page查询
                for (int i = (page - 1) * size; i < ((page - 1) * size)+a; i++) {
                    Map<String, Object> map = pageList.get(i);
                    String type = map.get("type").toString();
                    if (type.equals("0")) {
                        //获取content
                        BaseContent contentBase = new BaseContent();
                        for(BaseContent bc:contentList){
                            if(bc.getId().equals(map.get("id"))){
                                contentBase=bc;
                                break;
                            }
                        }
                        contentBase.setContenBody("");
                        contentBase.setRemark("资源");
                        list.add(contentBase);
                    } else if (type.equals("1")) {
                        //获取选修课程
                        CourseBaseInfo courseBase = new CourseBaseInfo();
                        for(CourseBaseInfo cb:courseList){
                            if(cb.getId().toString().equals(map.get("id").toString())){
                                courseBase=cb;
                                break;
                            }
                        }
                        courseBase.setRemark("课程");
                        list.add(courseBase);
                    }
                }
                recommend.put("recommend", list);
                //组装集合
                basePage.setData(recommend);//每一页数据
                basePage.setPage(packet.getPage());//当前页
                basePage.setSize(list.size());//每页显示条数
                basePage.setTotalPage(totalPage);//总页数
                basePage.setTotalNumber(pageList.size());//总条数
                message = CommonMessage.success(basePage, null);
                if (list == null || list.size() == 0) {
                    message = CommonMessage.failure(ErrorEnum.CONTENT_KNOWLEDGE_INVALID);
                }
            }
        }
        return message;
    }

    //对比集合,排序
    public static List<Map<String, Object>> compareCollection(List<Map<String, Object>> l1, List<Object> l2) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (l2 == null) {
            if (l1 == null || l1.size() == 0) {
                return list;
            } else {
                for (int i = 0; i < l1.size()-1; i++) {
                    for(int k =0; k < l1.size() - i-1; k++){
                        Map<String, Object> map1 = l1.get(k);
                        Map<String, Object> map2 = l1.get(k+1);
                        if ((map1.get("endTime") == null || map1.get("endTime").equals(""))||(map2.get("endTime") == null || map2.get("endTime").equals(""))) {
                            continue;
                        }else{
                            Long time1 = Long.parseLong(map1.get("endTime").toString());
                            Long time2 = Long.parseLong(map2.get("endTime").toString());
                            if (time1 < time2) {
                                Map<String, Object> map = map1;
                                l1.set(k, map2);
                                l1.set(k+1, map);
                            }
                        }
                    }
                }
                return l1;
            }
        } else {
            for (Object o : l2) {
                for (Map<String, Object> m : l1) {
                    String a = m.get("id").toString();
                    if (o.toString().equals(a)) {
                        list.add(m);
                    } else {
                        continue;
                    }
                }
            }
        }
        return list;
    }

    /**
     * 添加收藏或取消收藏
     */
    @RequestMapping(path = "/editCollect", method = RequestMethod.POST)
    public CommonMessage addCollect(@RequestBody CommonPacket<Map<String, Object>> packet) {
        logger.info("userId is: " + packet.getUsername());
        logger.info("deviceId is: " + packet.getDeviceId());
        logger.info("token is: " + packet.getToken());
        logger.info("data is: " + packet.getData());
        CommonMessage message;
        //接收收藏的资源id，收藏人delete：false
        String userId = packet.getUsername();
        Map<String, Object> data = packet.getData();
        String resourceId = data.get("resourceId").toString();
        //是否删除
        boolean delete = (boolean) data.get("delete");
        Collect collect = collectRepository.findByUserIdAndResourcesId(userId, resourceId);
        //如果是删除在资源id后添加对象delete：true
        if (delete) {
            //修改state状态未false
            if (collect == null || collect.equals("")) {
                //传值错误，没有该条收藏
                message = CommonMessage.failure(ErrorEnum.CONTENT_COLLECT_INVALID);
            } else {
                collect.setState("f");
                message = CommonMessage.success("取消收藏成功!", null);
            }
        } else {
            //收藏类型course或content
            String type = data.get("type").toString();
            if (collect == null || collect.equals("")) {
                collect = new Collect();
                //在我的收藏表中添加收藏
                collect.setState("t");
                collect.setResourcesId(resourceId);
                collect.setUserId(userId);
                collect.setType(type);
            } else {
                collect.setState("t");
            }
            message = CommonMessage.success("收藏成功!", null);
        }
        if(collect.getResourcesId()==null||collect.getResourcesId().equals("")){
            return CommonMessage.failure("resourceId为空");
        }
        collect.setTime(new Timestamp(new Date().getTime()));
        collectRepository.saveAndFlush(collect);
        return message;
    }

    /**
     * 添加评论和删除评论
     */
    @RequestMapping(path = "/editContent", method = RequestMethod.POST)
    public CommonMessage editContent(@RequestBody CommonPacket<Map<String, Object>> packet) {
        logger.info("userId is: " + packet.getUsername());
        logger.info("deviceId is: " + packet.getDeviceId());
        logger.info("token is: " + packet.getToken());
        logger.info("data is: " + packet.getData());
        CommonMessage message;
        //接收评论的资源id，评论人，评论内容，评论时间delete：false
        String userId = packet.getUsername();
        Map<String, Object> data = packet.getData();
        Long id;

        Comment comment = new Comment();
        //如果是删除在资源id后添加对象delete：true
        if (data.get("id") == null || data.get("id").equals("")) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
            String commentTime = sdf.format(date);
            String resourceId = data.get("resourceId").toString();
            String commentContent = data.get("commentContent").toString();
            //在我的收藏表中添加收藏
            comment.setState("t");
            comment.setCommentContent(commentContent);
            comment.setCommentUserId(userId);
            comment.setResourceId(resourceId);
            //BaseContent base=contentRepository.findByResourcesId(resourceId);
            comment.setCommentTime(commentTime);
            message = CommonMessage.success("评论成功!", null);
        } else {
            id = Long.parseLong(data.get("id").toString());
            //修改state状态未false
            comment = commentRepository.findById(id);
            if (comment == null || comment.equals("")) {
                //传值错误，没有该条收藏
                message = CommonMessage.failure(ErrorEnum.COMMENT_MY_INVALID);
            } else {
                comment.setState("f");
                message = CommonMessage.success("删除成功!", null);
            }
        }
        commentRepository.saveAndFlush(comment);
        return message;
    }

    /**
     * 查看评论
     */
    @RequestMapping(path = "/Content", method = RequestMethod.POST)
    public CommonMessage Content(@RequestBody CommonPagePacket<Map<String, Object>> packet) {
        logger.info("userId is: " + packet.getUsername());
        logger.info("deviceId is: " + packet.getDeviceId());
        logger.info("token is: " + packet.getToken());
        logger.info("page is: " + packet.getPage());
        logger.info("size is: " + packet.getSize());
        logger.info("data is: " + packet.getData());
        CommonMessage message;
        //接收资源id
        String userId = packet.getUsername();
        Map<String, Object> data = packet.getData();
        String resourceId = data.get("resourceId").toString();
        //BaseContent base=contentRepository.findByResourcesId(resourceId);

        List<Comment> collect = new ArrayList<>();
        if (resourceId == null || resourceId.equals("")) {
            message = CommonMessage.failure(ErrorEnum.CONTENT_RESOURCE_INVALID);
        } else {
            Pageable page = new PageRequest(packet.getPage() - 1, packet.getSize());
            Page<Comment> pageComment = commentRepository.findByResourceIdAndStateInOrderByCommentTimeDesc(page, resourceId, "t");
            PageInfo<List<Comment>> basePage = new PageInfo<>();
            //分页数据集合
            List<Comment> listComment = pageComment.getContent();
            if (listComment == null || listComment.size() == 0) {
                message = CommonMessage.failure(ErrorEnum.COMMENT_INVALID);
            } else {
                basePage.setData(listComment);//每一页数据
                basePage.setPage(packet.getPage());//当前页
                basePage.setSize(packet.getSize());//每页显示条数
                basePage.setTotalPage(pageComment.getTotalPages());//总页数
                basePage.setTotalNumber(pageComment.getTotalElements());//总条数
                message = CommonMessage.success(basePage, null);
            }
        }
        return message;
    }
}
