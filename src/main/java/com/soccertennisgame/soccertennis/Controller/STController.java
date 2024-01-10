package com.soccertennisgame.soccertennis.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soccertennisgame.soccertennis.CasinoService.CasinoDataService;
import com.soccertennisgame.soccertennis.SoccerTennisService.AutoCricketServices;
import com.soccertennisgame.soccertennis.SqlRepository.EX_T4_Table_Live_TLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@CrossOrigin("*")
@RestController
@RequestMapping("st")
public class STController {

    @Autowired
    private CasinoDataService casinoDataService;



    @GetMapping("/getMatchList")
    public ResponseEntity<String> getData() {
//        String key = "lucky7bData";
        String key = "Teen20";
        String result = casinoDataService.getLucky7bData(key);
        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getMatchResult")
    public ResponseEntity<String> getResultData() {
//        String key = "luck7bResul";
        String key = "Teen20Result";
        String result = casinoDataService.getResultData(key);

        if (!result.isEmpty()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getL7Results")
    public String getL7BResults() throws JsonProcessingException {
//        String key = "luck7bResul";
        String key = "Teen20Result";
        String result = casinoDataService.getResultData(key);
        casinoDataService.setCasinoResults();
        return "OK";
    }


}
