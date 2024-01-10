package com.soccertennisgame.soccertennis.Model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("matchdetails")
public class matchDetailsModel {
    String eventId;
    int EventTypeId;
    String EventTypeName;
    int GameStatus;
    String SeriesId;
    String SeriesName;
    String OddsSource;
    String Source;
    String WinnTeamName;
    int WinnerTeamId;
    LocalDateTime addDate;
    String addDateString;
    LocalDateTime eventDate;
    String eventName;
    int isAddedToMarket;
    int isInPlay;
    String marketId;
    String marketName;
    String marketType;
    String MatchTimeIST;
    int market_runner_count;
    String runnerName1;
    LocalDateTime modDate;
    String modDateString;
    String match_type;
    String runnerName2;
    String runnerName3;
    int selectionId1;
    int selectionId2;
    int selectionId3;

    int srno;
    LocalDateTime startDate;
    String startDateIST;



}