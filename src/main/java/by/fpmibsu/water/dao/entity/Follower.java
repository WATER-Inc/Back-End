package by.fpmibsu.water.dao.entity;

import by.fpmibsu.water.dao.Identified;

public class Follower implements Identified<String>{
    private String id;
    private String followerId;
    private String followedId;

    public void setId(String id) {
        this.id = id;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowedId() {
        return followedId;
    }

    public void setFollowedId(String followedId) {
        this.followedId = followedId;
    }

    @Override
    public String getId() {
        return id;
    }
}
