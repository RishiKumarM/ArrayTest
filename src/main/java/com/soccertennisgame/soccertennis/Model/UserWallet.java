package com.soccertennisgame.soccertennis.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("user-wallet")
public class UserWallet {
    @Id
    private String id;
    private int walletTransId;
    private LocalDateTime createdDate;
    private String createdDateString;
    private LocalDateTime matchClosedOn;
    private String matchClosedOnString;
    private String userId;
    private double credits;
    private double debit;
    private int transTypeId;
    private String descriptions;
    private String addName;
    private int betTransId;
    private int matchId;
    private int fancyId;
    private int seq;
    private double currentShare;
    private double remainingShare;
    private int isActive;
    private String marketId;
    private String gameTypeName;
    private String actionUserFor;
    private String actionForUserId;
    private String actionForUserRole;
    private String betForTeam;
    private LocalDateTime betCreatedDate;
    private String betCreatedDateString;
    private String actualResults;
    private String matchBetType;
    private int fancyResults;
    private String winnerName;
    private double mComm;
    private double sComm;
    private double cMComm;
    private double cSComm;
    private double alpha01;
    private double alpha02;
    private double alpha03;
    private double masterShare;
    private double superMasterShare;
    private double superSuperShare;
    private double adminShare;
    private double superAdminShare;
    private double rootShare;
    private double rootAdminShare;
    private double masterComm;
    private double superMasterComm;
    private double superSuperComm;
    private double adminComm;
    private double superAdminComm;
    private double rootComm;
    private double rootAdminComm;
    private double masterSComm;
    private double superMasterSComm;
    private double superSuperSComm;
    private double adminSComm;
    private double superAdminSComm;
    private double rootSComm;
    private double rootAdminSComm;
    private double masterShareRemaining;
    private double superMasterShareRemaining;
    private double superSuperShareRemaining;
    private double adminShareRemaining;
    private double superAdminShareRemaining;
    private double rootShareRemaining;
    private double RootAdminShareRemaining;
    private String userMap;
    private String homeTeamName;
    private String awayTeamName;
    private int homeTeamId;
    private int awayTeamId;
    private String leagueName;
    private String char01;
    private String char02;
    private String matchName;
    private String rootAdmin;
    private String root;
    private String superAdmin;
    private String admin;
    private String superSuper;
    private String superMaster;
    private String master;

}

