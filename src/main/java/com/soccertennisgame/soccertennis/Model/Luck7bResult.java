package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@Setter
//@RedisHash("luck7bResult")
@Document("luck7bResult")
public class Luck7bResult {
    @Id
    @Indexed
    private String id;
    private String mid;
    private String result;
    private LocalDateTime adddate;
    private String adddateSTR;
    private String WinnerName;
}


