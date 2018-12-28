package app.bankview;

import app.entity.content.Tags;
import app.packet.error.ErrorEnum;
import app.packet.response.CommonMessage;
import app.repository.TagRepository;
import com.alibaba.fastjson.JSON;
import ognl.IntHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gongmingbo on 2018/12/6.
 */
@Controller
@ControllerAdvice
@RequestMapping("/web")
public class LabelController {
    @Autowired
    private TagRepository tagRepository;

    @RequestMapping("/label.html")
    public String getHtml() {
        return "label_management";
    }

    @RequestMapping(path = "/label", method = RequestMethod.GET)
    @ResponseBody
    public String getHLabeltml() {
       // Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Tags> tagsList = tagRepository.findByStateOrderById(true);
        return JSON.toJSONString(tagsList);
    }

    @RequestMapping(path = "/label", method = RequestMethod.POST)
    @ResponseBody
    public String save(@ModelAttribute Tags tags) {
        tags.setState(true);
        Tags tagt = tagRepository.saveAndFlush(tags);
        return JSON.toJSONString(tagt);
    }

    @RequestMapping(path = "/deleteLabel", method = RequestMethod.POST)
    @ResponseBody
    public String deleteLabel(String id) {
        Tags tagt = tagRepository.findOne(id);
        tagt.setState(false);
        tagRepository.saveAndFlush(tagt);
        return JSON.toJSONString(tagt);
    }

    @RequestMapping(path = "/distinctLabel", method = RequestMethod.GET)
    @ResponseBody
    public String distinctLabel(String tagName) {
        List<Tags> tagt = tagRepository.findByTagNameAndState(tagName,true);
        Map<String, Boolean> map = new HashMap<>();
        if (tagt != null && tagt.size() > 0) {
            map.put("state", true);
        } else {
            map.put("state", false);
        }
        return JSON.toJSONString(map);
    }

    @ExceptionHandler(value = Exception.class)
    public CommonMessage errorHandler(Exception ex) {
        ex.printStackTrace();
        return CommonMessage.failure(ErrorEnum.SERVER_INTERNAL_ERROR);
    }
}
