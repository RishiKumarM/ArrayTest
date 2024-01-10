package com.soccertennisgame.soccertennis.mongodbProjection;


import lombok.Data;

import javax.swing.*;
import java.time.LocalDateTime;


public interface BetTransactionWithFIDProjection {
    String getUserId();
    String getMaster();
    String getBetForTeam();
    String getMatchName();
    int getMatchId();
    int getFancyId();
    String getIp();
    String getAddress();
    int getFancyResults();
    int getYesLine();
    int getNoLine();
    Double getOdds();
    Double getStake();
    Integer getBetType();
    LocalDateTime getBetCreatedDate();
    String getBetCreatedDateString();
    Double getBetProfit();
    Double getBetLoss();
    Double getBetResult();
    String  getMatchBetType();
    String getWinnerName();

}
