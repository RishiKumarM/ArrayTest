package com.soccertennisgame.soccertennis.Model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document("fancy_Details")
public class FancyDetails {

    int matchId;
    String matchName;
    String WinnerTeamName;
    int WinnerTeamId;
    int fancyId;
    String betForTeam;
    int fancyResult;
    LocalDateTime addDate;
    String addDateString;
    String marketType;
    int HomeTeamId;
    int AwayTeamId;
    String marketId;
}