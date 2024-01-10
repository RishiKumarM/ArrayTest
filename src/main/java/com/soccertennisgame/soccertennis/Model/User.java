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
@Document("userData")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private String registrationDateString;
    private String parentEmail;
    private String parent_userName;
    private int matchShare;
    private int matchCommission;
    private int sessionCommission;
    private int roleId;
    private String roleName;
    private String role_DisplayName;
    private double balance;
    private double odds_Exposer;
    private double book_Exposer;
    private double toss_Exposer;
    private double fancy_Exposer;
    private double casino_Exposer;
    private int isBetLock;
    private int isUserLock;
    private int isUserAccountLock;
    private int odds_Min;
    private int odds_Max;
    private int book_Min;
    private int book_Max;
    private int toss_Min;
    private int toss_Max;
    private int fancy_Min;
    private int fancy_Max;
    private int casino_Min;
    private int casino_Max;
    private int soccer_Min;
    private int soccer_Max;
    private int maxSoccerProfit;
    private int maxSoccerLoss;
    private int odds_Max_Profit;
    private int book_Max_Profit;
    private int toss_Max_Profit;
    private int fancy_Max_Profit;
    private int soccer_Max_Profit;
    private int casino_Max_Profit;
    private int odds_Max_Loss;
    private int book_Max_Loss;
    private int toss_Max_Loss;
    private int fancy_Max_Loss;
    private int casino_Max_Loss;
    private int soccer_Max_Loss;
    private int odd_Delay;
    private int book_Delay;
    private int toss_Delay;
    private int fancy_Delay;
    private int casino_Delay;
    private int soccer_Delay;
    private int isCricketOn;
    private int isTennisOn;
    private int isSoccerOn;
    private int isCasinoOn;
    private double profit;
    private double loss;
    private int minStack;
    private int maxStack;
    private int casinoShare;
    private int partnerShare;
    private int preInPlayStack;
    private int preInPlayProfit;
    private int creditLimit;
    private double creditGiven;
    private String rootAdmin;
    private String root;
    private String superAdmin;
    private String admin;
    private String superSuper;
    private String superMaster;
    private String master;
    private String passwordO;

}
