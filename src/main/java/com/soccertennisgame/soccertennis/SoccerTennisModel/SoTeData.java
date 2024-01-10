package com.soccertennisgame.soccertennis.SoccerTennisModel;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SoTeData {
    @Id
    private String id;
    String marketId;
    boolean isMarketDataDelayed;
    String status;
    int betDelay;
    boolean bspReconciled;
    boolean complete;
    boolean inplay;
    int numberOfWinners;
    int numberOfRunners;
    int numberOfActiveRunners;
    String lastMatchTime;
    Double totalMatched;
    Double totalAvailable;
    boolean crossMatching;
    boolean runnersVoidable;
    long version;
    List<Runner> runners;
    int isMatchResultUpdated;
    LocalDateTime modDate;

    public void setIsMarketDataDelayed(boolean isMarketDataDelayed) {
        this.isMarketDataDelayed = isMarketDataDelayed;
    }

    @Data
    public static class Runner {
        int selectionId1;
        String status1;
        int selectionId2;
        String status2;
        int selectionId3;
        String status3;
    }

}
