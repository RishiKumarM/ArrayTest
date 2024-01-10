package com.soccertennisgame.soccertennis.Controller;


import com.soccertennisgame.soccertennis.Model.CasinoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @Autowired
//    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

//    @Scheduled(fixedRate = 1000)
//    public void sendAutomaticMessage() {
//        String automaticMessage = "Automatic message from server!";
//        messagingTemplate.convertAndSend("/receive-message", automaticMessage);
//    }

}