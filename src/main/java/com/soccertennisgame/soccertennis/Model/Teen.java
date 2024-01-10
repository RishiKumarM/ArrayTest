package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
@Getter
@Setter
@RedisHash("TeenData")
public class Teen {
    @Id
    @Indexed
    private String id;
    private String marketId;
    private String sectionId;
    private String lasttime;
    private String UpdateTime;
    private String remark;
    private String gameType;
    private String min;
    private String max;
    private String C1;
    private String C2;
    private String C3;
    private int b1;
    private int bs1;
    private int l1;
    private int ls1;
    private String nation;
    private String Srno;
    private String gstatus;
}
