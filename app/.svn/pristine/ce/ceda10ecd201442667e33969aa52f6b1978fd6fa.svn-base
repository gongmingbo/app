package app.bankview;

import app.entity.json.ContentBaseView;
import app.entity.json.CourseBaseView;
import app.entity.json.KnowledgeContent;
import app.entity.smart.Knowledge;
import app.entity.smart.ProfessionClassification;
import app.packet.error.ErrorEnum;
import app.packet.response.CommonMessage;
import app.repository.KnowledgeRepository;
import app.repository.ProfessionRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping(path = "/web")
public class ProfessionController {

    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private KnowledgeRepository knowledgeRepository;

    private Gson gson = new Gson();


    @RequestMapping(path = "/pro", method = RequestMethod.GET)
    String professionsPage(Model model) {
        List<ProfessionClassification> pros = professionRepository.findAll();
        for (ProfessionClassification p : pros) {
            List<Knowledge> knowledges = knowledgeRepository.findByProfessionId(p.getProfessionId());
            int energy = 0;
            for (Knowledge k : knowledges) {
                String content = k.getContents();
                if (content == null){
                    continue;
                }
                KnowledgeContent knowledgeContent = gson.fromJson(content, KnowledgeContent.class);
//                p.setKnowledgeNumber(knowledgeContent.getContents().size() + knowledgeContent.getCourses().size());

                int contentEnergy = knowledgeContent.getContents().stream().mapToInt(ContentBaseView::getEnergy).sum();
                int courseEnergy = knowledgeContent.getCourses().stream().mapToInt(CourseBaseView::getEnergy).sum();
                energy += contentEnergy + courseEnergy;
            }
            p.setKnowledgeNumber(knowledges.size());
            p.setEnergy(energy);
        }
        model.addAttribute("pros", pros);
        model.addAttribute("dir", "content");

        return "profession";
    }

    @RequestMapping(path = "/pro_more", method = RequestMethod.GET)
    String pro_more(Model model) {
        List<ProfessionClassification> pros = professionRepository.findAll();
        model.addAttribute("pros", pros);
        return "profession_more";
    }

    @RequestMapping(path = "/pro_more_add", method = RequestMethod.GET)
    String pro_more_add(Model model) {
        List<ProfessionClassification> pros = professionRepository.findAll();
        model.addAttribute("pros", pros);
        return "profession_more_add";
    }

    @RequestMapping(path = "/pro", method = RequestMethod.GET, consumes = "application/json")
    @ResponseBody
    CommonMessage professions() {
        List<ProfessionClassification> pros = professionRepository.findAll();
        return CommonMessage.success(pros);
    }

    @RequestMapping(path = "/pro/{id}", method = RequestMethod.GET)
    @ResponseBody
    CommonMessage fetchProfession(@PathVariable("id") String id) {
        ProfessionClassification pro = professionRepository.findOne(id);
        return CommonMessage.success(pro);
    }

    @RequestMapping(path = "/pro/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    CommonMessage deleteProfession(@PathVariable("id") String id) {
        ProfessionClassification p = professionRepository.findOne(id);
        if (!p.isActive()){
            professionRepository.delete(id);
            return CommonMessage.success();
        }else {
            return CommonMessage.failure("已启用的职业无法删除..");
        }

    }

    @RequestMapping(path = "/pro/{id}", method = RequestMethod.POST)
    @ResponseBody
    CommonMessage modifyProfession(@PathVariable("id") String id, ProfessionClassification profession) {
        ProfessionClassification pro = professionRepository.findOne(id);
        pro.setProfessionName(profession.getProfessionName());
        pro.setEnergy(profession.getEnergy());
        pro.setKnowledgeNumber(profession.getKnowledgeNumber());
        professionRepository.save(pro);
        return CommonMessage.success();
    }

    @RequestMapping(path = "/pro", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    CommonMessage addProfession(ProfessionClassification profession) {
        if (profession.getProfessionName() == null || profession.getProfessionName().trim().isEmpty()){
            return CommonMessage.failure("名字不能为空..");
        }
        ProfessionClassification p = professionRepository.findByProfessionName(profession.getProfessionName());
        if (p != null){
            return CommonMessage.failure("该职业已经存在..");
        }
        professionRepository.save(profession);
        return CommonMessage.success();
    }


    @RequestMapping(path = "/pro/activate", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    CommonMessage addProfession(String id, boolean activate) {
        ProfessionClassification p = professionRepository.findOne(id);
        p.setActive(activate);
        professionRepository.save(p);
        return CommonMessage.success();
    }


    @ExceptionHandler(value = Exception.class)
    public CommonMessage errorHandler(Exception ex) {
        ex.printStackTrace();
        return CommonMessage.failure(ErrorEnum.SERVER_INTERNAL_ERROR);
    }
}
