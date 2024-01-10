package com.soccertennisgame.soccertennis.mongodbProjection;

import java.time.LocalDateTime;

public interface BetTransactionProjection {

    String getUserId();
    String getMaster();
    String getBetForTeam();
    String getMatchName();
    int getMatchId();
    Double getOdds();
    Double getStake();
    Integer getBetType();
    LocalDateTime getBetCreatedDate();
    String getBetCreatedDateString();
    Double getBetProfit();
    Double getBetLoss();
    Double getBetResult();
    String  getMatchBetType();

}
