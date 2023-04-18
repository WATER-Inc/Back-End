package by.fpmibsu.water.dao.entity;

import by.fpmibsu.water.dao.Identified;

public class ChatLink implements Identified<String> {
    private String linkId;
    private String chatId;
    private String userId;
    private String roleId;

    @Override
    public String getId() {
        return linkId;
    }

    public void setId(String linkId) {
        this.linkId = linkId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
