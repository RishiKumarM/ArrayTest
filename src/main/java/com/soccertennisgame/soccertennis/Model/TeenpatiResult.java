package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
@Getter
@Setter
@RedisHash("TeenpatiResult")
public class TeenpatiResult {

        @Id
        @Indexed
        private String id;
        private String mid;
        private String result;

}
