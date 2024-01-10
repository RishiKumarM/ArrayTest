package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("TeenpatiData")
public class TeenpatiModel {
    @Id
    @Indexed
    private String id;
    private String mid;
    private String autotime;
    private String remark;
    private String gtype;
    private int min;
    private int max;
    private String C1;
    private String C2;
    private String C3;
    private String C4;
    private String C5;
    private String C6;
    private String nation;
    private String sid;
    private String rate;
    private String gstatus;
    private String nodeType;
}
