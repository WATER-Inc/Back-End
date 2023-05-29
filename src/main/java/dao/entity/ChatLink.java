package dao.entity;

import entity.Identified;

public class ChatLink implements Identified<String> {
    private String id;
    private String chatId;
    private String userId;
    private String roleId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String linkId) {
        this.id = linkId;
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

    @Override
    public String toString() {
        return "ChatLink{" +
                "id='" + id + '\'' +
                ", chatId='" + chatId + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
