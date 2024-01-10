package com.soccertennisgame.soccertennis.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Luck7bResultResponse {
    private boolean success;
    private List<Luck7bResult> luck7bResults;
    private String graphdata;
}
