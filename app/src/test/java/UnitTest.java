import app.Application;
import app.entity.content.BaseContent;
import app.entity.content.Comment;
import app.entity.json.SearchResultView;
import app.entity.user.PassKnowledge;
import app.repository.*;
import app.service.WisdomTreeService;
import app.util.AESUtil;
import com.google.gson.Gson;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnitTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    @Value("${sms.appKey}")
    private String appKey;
    @Value("${sms.masterCode}")
    private String masterCode;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private PassKnowledgeRepository passKnowledgeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RankingRepository rankingRepository;
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;

    @Autowired
    private WisdomTreeService wisdomTreeService;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void commentTest(){
        Comment comment = new Comment();
        BaseContent baseContent = new BaseContent();
        baseContent.setContenBody("test");
        baseContent.setResourcesId(UUID.randomUUID().toString());
//        comment.setResource(baseContent);
        commentRepository.save(comment);
        List<Comment> commentList = commentRepository.findAll();
//        System.out.println(commentList.get(0).getResource().getContenBody());
    }

    @Test
    public void sqlTest(){
        logger.warn("sb");
    }

    @Test
    public void encrypt(){
        String key = "khjghfgdfszds!@#$%hbdssjkd";
        String encrypted = AESUtil.encrypt("nishigesb", key);
        String decrypted = AESUtil.decrypt(encrypted, key);
        logger.warn("encrypted: " + encrypted);
        logger.warn("decrypted: " + decrypted);
    }

    @Test
    public void smsSend() throws UnsupportedEncodingException {
        String sendUrl = "https://api.sms.jpush.cn/v1/codes";

        RestTemplate restTemplate = new RestTemplate();
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

            }
        };
        restTemplate.setErrorHandler(responseErrorHandler);
        Map<String,Object > req = new HashMap<>();
        req.put("temp_id", 1);
        req.put("mobile", "13883786116");
//        req.put("sign_id", "")
        Gson gson = new Gson();
        MultiValueMap<String ,String > headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + Base64.encodeBase64String((appKey + ":" + masterCode).getBytes("UTF-8")));
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(req), headers);
        String res = restTemplate.postForObject(sendUrl, entity, String .class);
        logger.info(res);
    }

    @Test
    public void smsValid() throws UnsupportedEncodingException {
        String sendUrl = "https://api.sms.jpush.cn/v1/codes/637290010350/valid";

        RestTemplate restTemplate = new RestTemplate();
        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

            }
        };
        restTemplate.setErrorHandler(responseErrorHandler);
        Map<String,Object > req = new HashMap<>();
        req.put("code", "026008");
//        req.put("mobile", "13883786116");
        Gson gson = new Gson();
        MultiValueMap<String ,String > headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Basic " + Base64.encodeBase64String((appKey + ":" + masterCode).getBytes("UTF-8")));
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(req), headers);
        String res = restTemplate.postForObject(sendUrl, entity, String .class);
        logger.info(res);
    }

    @Test
    public void json(){
        Set<String> ss = new HashSet<>();
        ss.add("a");
        ss.add("b");
        ss.add("d");
        ss.add("q");
        ss.add("m");
        ss.add("u");
        ss.add("c");
        List<String > dd = ss.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        Map<String,String> orderedMap = new LinkedHashMap<>();
        orderedMap.put("5","5" );
        orderedMap.put("6","6 ");
        orderedMap.put("7","7" );
        orderedMap.put("8","8" );
        orderedMap.put("1","1" );
        orderedMap.put("2","2" );
        orderedMap.put("3","3" );
        orderedMap.put("4","4" );

        String json = new Gson().toJson(orderedMap);
        dd.size();
    }

    @Test
    public void PassKnowledgeRepositoryTest(){
        List<String> list = new ArrayList<>();
        list.add("f2b77d9f6796ec9a0167977ebfa5001b");
        list.add("2c9ebc046786ac37016786ac9e6a0000");
        list.add("f2b77d9f6796ec9a0167977dd7d8001a");
        list.add("f2b77d9f6786bb78016786c5d7270008");
        list.add("f2b77d9f6787bdba016787ef91a90009");
        list.add("f2b77d9f6796ec9a016797869d450021");
        list.add("f2b77d9f6796ec9a0167978608ef001f");
        list.add("f2b77d9f6796ec9a0167978da3dd0026");
        list.add("f2b77d9f6796ec9a0167978875df0024");
        list.add("f2b77d9f6787bdba016787f21e9c000d");

        List<SearchResultView> views = contentRepository.search("%图形%", list);
        views.size();
    }

    @Test
    public void wisdomTreeTest(){
        String userId = "402886816773336301677841046a0001",proId = "402886816771ce0a016771cee12b0000";
//        wisdomTreeService.newTree(userId, proId);
        List<PassKnowledge> passKnowledge = wisdomTreeService.getWisdomTree(userId, proId);
        passKnowledge.size();
    }

    @Test
    public void knowledgeTest(){
        int count = knowledgeRepository.countByPre("4028868167819e7e0167822b53c00007");
        logger.info("count: " + count);
    }

}
