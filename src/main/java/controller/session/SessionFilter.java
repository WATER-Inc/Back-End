package controller.session;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
public class SessionFilter implements Filter {
    private static Logger logger = Logger.getLogger(SessionFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Получаем идентификатор сессии из куки
        String sessionId = getSessionIdFromCookie(request);
        logger.debug(sessionId);
        // Если идентификатор сессии не найден в куки, создаем новую сессию
        if (sessionId == null || SessionManager.getSession() == null) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1800); // Таймаут сессии в секундах (здесь - 30 минут)
            sessionId = session.getId();
            logger.info("Create new Session: " + sessionId);
            //saveSessionIdToCookie(sessionId, response);
        }
        logger.debug(sessionId);
        // Связываем текущий поток выполнения сессией
        HttpSession session = request.getSession(false);
        if (session != null && session.getId().equals(sessionId)) {
            SessionManager.bindSession(session);
        }
//        response.addHeader("Set-Cookie", "JSESSIONID=" + sessionId + "; Secure; SameSite=Lax");
        try {
            // Передаем запрос дальше в цепочку фильтров и сервлетов
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // Освобождаем текущий поток выполнения от сессии
            SessionManager.unbindSession();
        }
    }

    @Override
    public void destroy() {}

    private String getSessionIdFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    logger.info("Got session " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}