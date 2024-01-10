package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
public class TeenResult {
    @Id
    @Indexed
    private String id;
    private String mid;
    private String result;
}
