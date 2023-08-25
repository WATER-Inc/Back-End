package controller.servlet.websocket;

import action.AbstractAction;
import action.ActionFactory;
import action.websocket.WsAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.servlet.Dispatcher;
import dao.PersistException;

import javax.websocket.Session;
import java.io.IOException;

public abstract class WebSocketAbstractEndPoint extends Dispatcher<String, Session> {
    @Override
    protected AbstractAction<String, Session> getAction(String s, Session session) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON string into a JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uri = "/";
        if(jsonNode != null)
            uri = jsonNode.get("action").asText();
        Class<WsAction> actionClass = (Class<WsAction>) ActionFactory.getAction(uri);
        WsAction action = null;
        try {
            action = actionClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        action.setEndPoint(this);
        return action;
    }

    @Override
    protected void sendError(Exception e, String s, Session session) {
        try {
            session.getBasicRemote().sendText(e.getMessage());//TODO
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public abstract void sendToAll(String message);
}
