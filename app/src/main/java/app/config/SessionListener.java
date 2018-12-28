package app.config;

import ch.qos.logback.core.net.SyslogOutputStream;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by gongmingbo on 2018/12/11.
 */

@WebListener
public class SessionListener implements HttpSessionListener {
    public static Map<HttpSession, String> sessionStringMap = new Hashtable<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        sessionStringMap.remove(session);
        session.invalidate();


    }
}