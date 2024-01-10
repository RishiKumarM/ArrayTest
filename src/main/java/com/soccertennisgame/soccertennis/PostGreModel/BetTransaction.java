package com.soccertennisgame.soccertennis.PostGreModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bet_table")
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
    private double odds;
    private double stake;
    private Integer betTeamId;
    private String betForTeam;
    private Integer betType;
    private int yesValue;
    private int noValue;
    private Integer fancyResults;
    private Integer betStatus;
    private double betProfit;
    private double betLoss;
    private String matchBetType;
    private double betResult;
    private double homeTeamPosition;
    private double awayTeamPosition;
    private double drawTeamPosition;
    private double loadValues;
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
    private double yesLine;
    private double noLine;

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
