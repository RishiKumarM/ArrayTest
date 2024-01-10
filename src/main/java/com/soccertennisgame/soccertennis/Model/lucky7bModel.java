package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("lucky7b")
public class lucky7bModel {
    @Id
    @Indexed
    private String id;
    private String mid;
    private String autotime;
    private String gtype;
    private int min;
    private int max;
    private String C1;
    private String nat;
    private String sid;
    private String rate;
    private String gstatus;
    private String nodeType;

}
