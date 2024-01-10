package com.soccertennisgame.soccertennis.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("bet-table")
public class BetTransaction {
    @Id
    private String id;
    private int betTransId;
    private Integer fancyId;
    private int matchId;
    private int eventTypeId;
    private String matchName;
    private String sportsName;
    private String marketId;
    private String userId;
    private LocalDateTime betCreatedDate;
    private String betCreatedDateString;
    private Double odds;
    private Double stake;
    private Integer betTeamId;
    private String betForTeam;
    private Integer betType;
    private int yesValue;
    private int noValue;
    private Integer fancyResults;
    private Integer betStatus;
    private Double betProfit;
    private Double betLoss;
    private String matchBetType;
    private Double betResult;
    private Double homeTeamPosition;
    private Double awayTeamPosition;
    private Double drawTeamPosition;
    private Double loadValues;
    private Integer isSettled;
    private int srno;
    private LocalDateTime isSettledOn;
    private String isSettledOnString;
    private String ipAddress;
    private LocalDateTime modDate;
    private String modDateString;
    private String modName;
    private LocalDateTime matchedTime;
    private String matchedTimeString;
    private LocalDateTime betPlacedTime;
    private String betPlacedTimeString;
    private LocalDateTime betResultUpdatedOn;
    private String betResultUpdatedOnString;
    private Integer inputFancyResult;
    private String casinoBetType;
    private Integer gameTypeId;
    private Double yesLine;
    private Double noLine;

    private String rootAdmin;
    private String root;
    private String superAdmin;
    private String admin;
    private String superSuper;
    private String superMaster;
    private String master;

    private String runnerName1;
    private String runnerName2;
    private String runnerName3;

    private int selectionId1;
    private int selectionId2;
    private int selectionId3;

    private String ip;
    private String address;
    private String winnerName;
}