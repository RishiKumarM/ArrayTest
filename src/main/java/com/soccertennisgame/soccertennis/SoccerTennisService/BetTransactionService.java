package com.soccertennisgame.soccertennis.SoccerTennisService;

import com.soccertennisgame.soccertennis.Model.AggregatedResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;


@Service
public class BetTransactionService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public AggregatedResult getAllLoad(String userId, int roleId, String matchBetType, int betStatus) {

        if(roleId==40){
            Criteria criteria = Criteria.where("userId").is(userId).and("matchBetType").is(matchBetType).and("betStatus").is(betStatus);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("userId")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();

        } else if(roleId==35){
            Criteria criteria = Criteria.where("master").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("master")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==30){
            Criteria criteria = Criteria.where("superMaster").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("superMaster")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }

        else if(roleId==25){
            Criteria criteria = Criteria.where("superSuper").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("superSuper")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }

        else if(roleId==20){
            Criteria criteria = Criteria.where("admin").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("admin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==15){
            Criteria criteria = Criteria.where("superAdmin").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("superAdmin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==10){
            Criteria criteria = Criteria.where("root").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("root")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else if(roleId==5){
            Criteria criteria = Criteria.where("rootAdmin").is(userId).and("matchBetType").is(matchBetType);
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("rootAdmin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.project("homeTeamPosition", "awayTeamPosition", "drawTeamPosition")
                            .andExpression("homeTeamPosition * -1").as("homeTeamPosition")
                            .andExpression("awayTeamPosition * -1").as("awayTeamPosition")
                            .andExpression("drawTeamPosition * -1").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }
        else {
            Criteria criteria = Criteria.where("rootAdmin").is(userId).and("matchId");
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("rootAdmin")
                            .sum("homeTeamPosition").as("homeTeamPosition")
                            .sum("awayTeamPosition").as("awayTeamPosition")
                            .sum("drawTeamPosition").as("drawTeamPosition"),
                    Aggregation.limit(1)
            );

            AggregationResults<AggregatedResult> result = mongoTemplate.aggregate(
                    aggregation, "bet-table", AggregatedResult.class
            );

            return result.getUniqueMappedResult();
        }




    }

}




