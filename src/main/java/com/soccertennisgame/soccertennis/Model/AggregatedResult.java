package com.soccertennisgame.soccertennis.Model;
import lombok.Data;

@Data
public class AggregatedResult {
    private double homeTeamPosition;
    private double awayTeamPosition;
    private double drawTeamPosition;
    private int matchId;
    private String matchBetType;
    private String userId;
    private int roleId;
}