package app.bankview;

import app.config.SessionListener;
import app.entity.user.User;
import app.packet.error.ErrorEnum;
import app.packet.response.CommonMessage;
import app.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by gongmingbo on 2018/11/22.
 */
@Controller
@RequestMapping("/web")
public class LoginContrller {
    @Autowired
    private UserService userService;

    @RequestMapping("/tologin")
    public String toLogin(String then,String state, Model model) {
        model.addAttribute("then", then);
        model.addAttribute("state", state);
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = userService.getUserByTelephone(userName);
        CommonMessage message;
        if (user == null) {
            message = CommonMessage.failure(ErrorEnum.USER_NOT_EXISTS);
        } else if (!userService.validatePassword(userName, password)) {
            message = CommonMessage.failure(ErrorEnum.USER_PASSWORD_INVALID);
        } else if (!"admin".equals(user.getUserType())) {
            message = CommonMessage.failure(ErrorEnum.ADMIN_PERMISSION);
        } else {
            String token = userService.getToken(userName, password);
            message = CommonMessage.success(null, token);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("userId", userName);
            httpSession.setAttribute("userName", user.getUserName());
            httpSession.setAttribute("state", "state");
            Map<HttpSession, String> sessionStringMap = SessionListener.sessionStringMap;
            if (sessionStringMap.size() == 0 || !sessionStringMap.containsKey(httpSession)) {
                sessionStringMap.put(httpSession, userName);
            }
            for (Map.Entry<HttpSession, String> map : sessionStringMap.entrySet()) {
                if (userName.equals(map.getValue()) && !httpSession.equals(map.getKey())) {
                   // map.getKey().invalidate();
                   // sessionStringMap.remove(map.getKey());
                    map.getKey().removeAttribute("userId");
                    map.getKey().removeAttribute("userName");
                    sessionStringMap.put(httpSession, userName);
                    break;
                }
            }
        }
        return JSON.toJSONString(message);

    }

    @RequestMapping("/quit")
    public String quit(HttpServletRequest request) {
        request.getSession().invalidate();
        // request.getSession().removeAttribute("userId");
        // request.getSession().removeAttribute("userName");
        return "login";

    }
}
