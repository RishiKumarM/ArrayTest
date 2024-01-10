package com.soccertennisgame.soccertennis.SoccerTennisModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class STData {
    private String eventId;
    private int eventTypeId;
    private String marketId;
    private int gameStatus;
    private LocalDateTime addDate;
}
