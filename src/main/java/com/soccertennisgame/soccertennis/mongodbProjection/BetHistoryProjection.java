package com.soccertennisgame.soccertennis.mongodbProjection;


import java.time.LocalDateTime;

public interface BetHistoryProjection {

    String getBetForTeam();
    Double getOdds();
    Double getStake();
    Integer getBetType();
    LocalDateTime getBetPlacedTime();
    String getBetPlacedTimeString();
    String  getMatchBetType();
    String  getBetProfit();
    String  getBetLoss();
    String  getBetResult();
    String  getBetStatus();

    String  getUserId();
    String  getId();

    String getRootAdmin();

    String getRoot();
    String getSuperAdmin();

    String getAdmin();
    String getSuperSuper();
    String getSuperMaster();

    String getMaster();
    String getIp();
    String getAddress();
    String getMatchName();
}
