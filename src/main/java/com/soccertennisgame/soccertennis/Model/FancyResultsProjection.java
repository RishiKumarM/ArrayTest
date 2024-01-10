package com.soccertennisgame.soccertennis.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FancyResultsProjection {

    private String _id;
    private String userId;
    private int matchId;
    private int fancyId;
    private int betTeamId;
    private double odds;
    private double stake;
    private double betProfit;
    private double betLoss;
    private String matchBetType;
    private int betType;
    private String betForTeam;

    private String rootAdmin;
    private String root;
    private String superAdmin;
    private String admin;
    private String superSuper;
    private String superMaster;
    private String master;

}