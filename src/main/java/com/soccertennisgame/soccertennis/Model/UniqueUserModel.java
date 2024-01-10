package com.soccertennisgame.soccertennis.Model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UniqueUserModel {
    private String userId;
    private int roleId;
}