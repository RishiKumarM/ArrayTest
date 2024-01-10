package com.soccertennisgame.soccertennis.PostGreModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_wallet")
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
    @Column(name = "add_name")
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
    @Column(name = "action_user_for")
    private String actionUserFor;
    @Column(name = "action_for_user_id")
    private String actionForUserId;
    @Column(name = "action_for_user_role")
    private String actionForUserRole;
    private String betForTeam;
    private LocalDateTime betCreatedDate;
    private String betCreatedDateString;
    @Column(name = "actual_results")
    private String actualResults;
    private String matchBetType;
    private int fancyResults;
    private String winnerName;
    private double mComm;
    private double sComm;
    @Column(name = "c_m_comm")
    private double cMComm;
    @Column(name = "c_s_comm")
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
    @Column(name = "master_s_comm")
    private double masterSComm;
    @Column(name = "super_master_s_comm")
    private double superMasterSComm;
    @Column(name = "super_super_s_comm")
    private double superSuperSComm;
    @Column(name = "admin_s_comm")
    private double adminSComm;
    @Column(name = "super_admin_s_comm")
    private double superAdminSComm;
    @Column(name = "root_s_comm")
    private double rootSComm;
    @Column(name = "root_admin_s_comm")
    private double rootAdminSComm;
    private double masterShareRemaining;
    private double superMasterShareRemaining;
    private double superSuperShareRemaining;
    private double adminShareRemaining;
    private double superAdminShareRemaining;
    private double rootShareRemaining;
    @Column(name = "root_admin_share_remaining")
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
