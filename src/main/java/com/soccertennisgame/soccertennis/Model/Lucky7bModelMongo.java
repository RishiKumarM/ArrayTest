package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("luck7bData")
public class Lucky7bModelMongo {
    @Id
    @Indexed
    private String id;
    private String mid;
    private String C1;
    private String autotime;
    private LocalDateTime adddate;
    private String adddateSTR;

}
