package controller.session;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Получаем идентификатор сессии из куки
        String sessionId = getSessionIdFromCookie(request);

        // Если идентификатор сессии не найден в куки, создаем новую сессию
        if (sessionId == null) {
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(1800); // Таймаут сессии в секундах (здесь - 30 минут)
            sessionId = session.getId();
            saveSessionIdToCookie(sessionId, response);
        }

        // Связываем текущий поток выполнения сессией
        HttpSession session = request.getSession(false);
        if (session != null && session.getId().equals(sessionId)) {
            SessionManager.bindSession(session);
        }

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
                if (cookie.getName().equals("sessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void saveSessionIdToCookie(String sessionId, HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setMaxAge(1800); // Срок жизни куки в секундах (здесь - 30 минут)
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}