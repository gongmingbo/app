package app.apiservice;

import app.entity.PageInfo;
import app.entity.json.CollectEnergy;
import app.entity.json.wt.WisdomTreeView;
import app.entity.smart.ProfessionClassification;
import app.entity.user.Learnt;
import app.entity.user.PassKnowledge;
import app.entity.user.Ranking;
import app.packet.error.ErrorEnum;
import app.packet.request.CommonPacket;
import app.packet.request.CommonPagePacket;
import app.packet.response.CommonMessage;
import app.repository.PassKnowledgeRepository;
import app.repository.RankingRepository;
import app.service.CareerService;
import app.service.WisdomTreeService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
@RequestMapping(path = "/api")
public class WisdomTree {

    private final Logger logger = LoggerFactory.getLogger(this.getClass()) ;

    @Autowired
    private WisdomTreeService wisdomTreeService;

    @Autowired
    private CareerService careerService;

    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private PassKnowledgeRepository passKnowledgeRepository;

    private Gson gson = new Gson();

//    @ApiLoginRequired
    @RequestMapping(path="/myTree",method= RequestMethod.POST)
    @Transactional
    public CommonMessage myTree(@RequestBody CommonPacket<Map<String ,String>> packet){
        Map<String ,String > data = packet.getData();
        String userId = data.getOrDefault("userId", packet.getUsername());
        String proId = data.getOrDefault("proId", null);
        wisdomTreeService.checkCourse(userId);
        List<PassKnowledge> passKnowledge = wisdomTreeService.getWisdomTree(userId, proId);
        List<WisdomTreeView> wisdomTrees = new ArrayList<>();
        for (PassKnowledge p: passKnowledge){
            String plan = p.getPlan();
            WisdomTreeView tree = gson.fromJson(plan, WisdomTreeView.class);
            tree.setEnergy(p.getEnergy());
            wisdomTrees.add(tree);
        }
        return CommonMessage.success(wisdomTrees);
    }

    @RequestMapping(path="/knowledge",method= RequestMethod.POST)
    public CommonMessage knowledge(@RequestBody CommonPacket<Map<String ,String>> packet){
        Map<String ,String > data = packet.getData();
        String knowledgeId = data.getOrDefault("knowledgeId", null);
        if (knowledgeId == null){
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }else {
            return CommonMessage.success(careerService.getKnowledge(knowledgeId));
        }
    }

//    @ApiLoginRequired
    @RequestMapping(path="/learnt",method= RequestMethod.POST)
    public CommonMessage learnt(@RequestBody CommonPacket<Map<String ,String>> packet){
        Map<String ,String > data = packet.getData();
        String userId = data.getOrDefault("userId", packet.getUsername());
        String proId = data.getOrDefault("proId", null);
        if (proId == null){
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }
        List<Learnt> history = wisdomTreeService.learntHistory(userId, proId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Map<String, List<Learnt>> learntMap = history.stream()
                .peek(p->p.setTime(timeFormat.format(p.getLearntTime())))
                .collect(Collectors.groupingBy(p-> dateFormat.format(p.getLearntTime())));
        List<String> order = learntMap.keySet().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        Map<String,List<Learnt>> orderedMap = new LinkedHashMap<>();
        for (String o : order){
            orderedMap.put(o, learntMap.get(o));
        }
        return CommonMessage.success(orderedMap);
    }

//    @ApiLoginRequired
    @RequestMapping(path="/careers",method= RequestMethod.POST)
    public CommonMessage careers(@RequestBody CommonPacket packet){
        List<ProfessionClassification> pros = careerService.getProfessions();
        return CommonMessage.success(pros);
    }

//    @ApiLoginRequired
    @RequestMapping(path="/changeCareer",method= RequestMethod.POST)
    @Transactional
    public CommonMessage changeCareer(@RequestBody CommonPacket<List<String>> packet){
        List<String> pros = packet.getData();
        List<String> myPros = careerService.getMyProfessions(packet.getUsername());
        if (pros.size() > 2){
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }
        if (myPros == null){
            for (String pro : pros) {
                wisdomTreeService.newTree(packet.getUsername(), pro);
            }
        }else {
            List<String> toDelete = new ArrayList<>();
            List<String > toCreate = new ArrayList<>();
            for (String my : myPros){
                if (!pros.contains(my)){
                    toDelete.add(my);
                }
            }
            for (String p : pros){
                if (!myPros.contains(p)){
                    toCreate.add(p);
                }
            }
            for (String s: toCreate){
                wisdomTreeService.newTree(packet.getUsername(), s);
            }
            for (String p : toDelete){
                passKnowledgeRepository.deleteByUserIdAndProfessionId(packet.getUsername(), p);
            }
        }
        return CommonMessage.success();
    }

//    @ApiLoginRequired
    @RequestMapping(path="/collectEnergy",method= RequestMethod.POST)
    @Transactional
    public CommonMessage collectEnergy(@RequestBody CommonPacket<CollectEnergy> packet){
        CollectEnergy data = packet.getData();
        String proId = data.getProId();
        int energy = data.getEnergy();
        PassKnowledge myCareer = careerService.getMyProfessions(packet.getUsername(), proId);
        WisdomTreeView wisdomTreeView = gson.fromJson(myCareer.getPlan(), WisdomTreeView.class);
        if (wisdomTreeView.getFloatingEnergy() >= energy){
            wisdomTreeView.setFloatingEnergy(wisdomTreeView.getFloatingEnergy() - energy);
            myCareer.setEnergy(myCareer.getEnergy() + energy);
            myCareer.setPlan(gson.toJson(wisdomTreeView));
            passKnowledgeRepository.save(myCareer);
            return CommonMessage.success();
        }else {
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }

    }

//    @ApiLoginRequired
    @RequestMapping(path="/rank",method= RequestMethod.POST)
    public CommonMessage rank(@RequestBody CommonPagePacket<Map<String , String >> packet){
        Map<String ,String > data = packet.getData();
        String userId = data.getOrDefault("userId", packet.getUsername());
        String proId = data.getOrDefault("proId", null);
        if (proId == null){
            return CommonMessage.failure(ErrorEnum.INVALID_PARAM);
        }
        int page, size;
        page = packet.getPage() < 0 ? 0 : packet.getPage() ;
        size = packet.getSize() <= 0 ? 10 : packet.getSize();

        Ranking ranking = rankingRepository.findRankingByUserIdAndProfessionId(userId, proId);
        Pageable pageable = new PageRequest(page, size);
        Page<Ranking> rankings = rankingRepository.findByProfessionIdOrderByRankAsc(proId, pageable);
        Map<String, Object> map = new HashMap<>();
        PageInfo<List<Ranking>> pageInfo = new PageInfo<>();
        pageInfo.setData(rankings.getContent());
        pageInfo.setPage(page);
        pageInfo.setSize(rankings.getSize());
        pageInfo.setTotalPage(rankings.getTotalPages());
        map.put("my", ranking);
        map.put("rank", pageInfo);
        return CommonMessage.success(map);
    }


    @ExceptionHandler(value = Exception.class)
    public CommonMessage errorHandler(Exception ex) {
        ex.printStackTrace();
        return CommonMessage.failure(ErrorEnum.SERVER_INTERNAL_ERROR);
    }
}
