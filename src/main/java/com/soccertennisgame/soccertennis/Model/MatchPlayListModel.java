package com.soccertennisgame.soccertennis.Model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("addedmatchlists")
public class MatchPlayListModel {
   String eventId;
   int EventTypeId;
   String EventTypeName;
   int GameStatus;
   String MatchTimeIST;
   String MatchTimeISTString;
   String SeriesName;
   String Source;
   String WinnTeamName;
   int WinnerTeamId;
   LocalDateTime addDate;
   String addDateString;
   int back1;
   int back11;
   int back12;
   String eventName;
   int isAddedToMarket;
   int isInPlay;
   int lay1;
   int lay11;
   int lay12;
   int market_runner_count;
   String runnerName1;
   String runnerName2;
   String runnerName3;
   String selectionId1;
   String selectionId2;
   String selectionId3;

}