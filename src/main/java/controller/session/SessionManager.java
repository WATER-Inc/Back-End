package controller.session;


import javax.servlet.http.HttpSession;

public class SessionManager {
    private static final ThreadLocal<HttpSession> sessionThreadLocal = new ThreadLocal<>();

    public static void bindSession(HttpSession session) {
        sessionThreadLocal.set(session);
    }

    public static void unbindSession() {
        sessionThreadLocal.remove();
    }

    public static HttpSession getSession() {
        return sessionThreadLocal.get();
    }
}
