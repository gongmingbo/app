package app.bankview;

import app.entity.user.User;
import app.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import ognl.IntHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gongmingbo on 2018/12/6.
 */
@Controller
@RequestMapping("/web")
public class ChangPasswordController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/changpassword.html")
    public  String changePwd(){
        return "changePwd";
    }

    @RequestMapping(path = "/changpassword",method = RequestMethod.POST)
    @ResponseBody
    public  String changePwd(HttpServletRequest request, String  odlPassword, String newPassword){
         String telephone =request.getSession().getAttribute("userId").toString();
         User user=userRepository.findByUserTelephone(telephone);
         Map<String,Object> map=new HashMap<>();
         if (!user.getUserPassword().equals(odlPassword)){
             map.put("msg","旧密码不正确");
         }else {
             user.setUserPassword(newPassword);
             userRepository.saveAndFlush(user);
             map.put("state",true);
         }
        return JSON.toJSONString(map);
    }
}
