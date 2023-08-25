package action.http.chat.longpolling;

import action.http.chat.ChatHttpAction;
import dao.PersistException;
import entity.Chat;

import javax.servlet.AsyncContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class ChatLongPollingHttpAction extends ChatHttpAction {
    public ChatLongPollingHttpAction() throws PersistException {
        super();
    }

    protected static void addContext(Chat chat, AsyncContext context) {
        synchronized (longPollingContext) {
            if (!longPollingContext.containsKey(chat.getId()))
                longPollingContext.put(chat.getId(), new CopyOnWriteArraySet<>());
            longPollingContext.get(chat.getId()).add(context);
        }
    }

    protected static void removeContext(Chat chat, AsyncContext context) {
        synchronized (longPollingContext) {
            if (longPollingContext.containsKey(chat.getId())) {
                Set<AsyncContext> contexts = longPollingContext.get(chat.getId());
                contexts.remove(context);
                if (contexts.isEmpty())
                    longPollingContext.remove(chat.getId());
            }
        }
    }

    protected static void notifyAllContexts(Chat chat) {
        logger.debug(longPollingContext.keySet());
        logger.debug(chat);
        logger.debug(longPollingContext.get(chat.getId()));
        for (AsyncContext contexts : longPollingContext.get(chat.getId()))
            synchronized (contexts) {
                contexts.notifyAll();
            }
    }

    private static final Map<String, Set<AsyncContext>> longPollingContext;

    static {
        longPollingContext = new HashMap<>();
    }
}