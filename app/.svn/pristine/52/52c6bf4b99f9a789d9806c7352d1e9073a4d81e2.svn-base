package app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Component
public class WebInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session=httpServletRequest.getSession();
        Object object = session.getAttribute("userId");
        Object state = session.getAttribute("state");
        String uri = httpServletRequest.getRequestURI();
        if (StringUtils.isEmpty(object)&&!StringUtils.isEmpty(state)){
            httpServletResponse.sendRedirect("/web/tologin?state=" +"state");
            session.invalidate();
            SessionListener.sessionStringMap.remove(session);
            return true;
        }
        if (!StringUtils.isEmpty(object) || uri.equals("/web/tologin") || uri.equals("/web/login")) {
            return true;
        } else {
            httpServletResponse.sendRedirect("/web/tologin?then=" + URLEncoder.encode(uri));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        String uri = httpServletRequest.getRequestURI();
        if (modelAndView == null) {
            return;
        }
        if (uri.startsWith("/web/course/required")) {
            modelAndView.getModel().put("dir", "compulsory");
        } else if (uri.startsWith("/web/course/elective")) {
            modelAndView.getModel().put("dir", "elective");
        } else if (uri.startsWith("/web/recommend") || uri.startsWith("/web/index")) {
            modelAndView.getModel().put("dir", "content");
        } else if (uri.startsWith("/web/pro") || uri.startsWith("/web/knowledge")) {
            modelAndView.getModel().put("dir", "career");
        }else if (uri.startsWith("/web/icon.html")){
            modelAndView.getModel().put("dir", "icon");
        }else if (uri.startsWith("/web/label.html")){
            modelAndView.getModel().put("dir", "label");
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
