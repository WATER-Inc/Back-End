package by.fpmibsu.water.entity;

import jdk.internal.net.http.common.Pair;

import java.util.List;

public class Chat {
    private String chatId;
    private List<Pair<User,String> > users; //TODO second element in the pair is the role of the user of this pair
}
