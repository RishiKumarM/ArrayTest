package com.soccertennisgame.soccertennis.SoccerTennisService;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class UserWalletService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public double getUserWinning(String userId) {
        List<Integer> transId1 = Arrays.asList(3,4);
        Criteria criteria = Criteria.where("userId").is(userId).and("transTypeId").in(transId1);
        double actualValue;
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group()
                        .sum("credits").as("totalCredits")
                        .sum("debit").as("totalDebit")
        );

        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                aggregation, "user-wallet", BasicDBObject.class
        );

        BasicDBObject aggregatedResult = result.getUniqueMappedResult();

        if (aggregatedResult != null) {
            double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
            double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
            return totalCredits + totalDebit; // Calculate balance as credits - debits
        } else {
            return 0.0;
        }
    }

    public double getBalanceFromWallet(String userId) {
        Criteria criteria = Criteria.where("userId").is(userId);
        double actualValue;
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group()
                        .sum("credits").as("totalCredits")
                        .sum("debit").as("totalDebit")
        );

        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                aggregation, "user-wallet", BasicDBObject.class
        );

        BasicDBObject aggregatedResult = result.getUniqueMappedResult();

        if (aggregatedResult != null) {
            double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
            double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
            return totalCredits + totalDebit; // Calculate balance as credits - debits
        } else {
            return 0.0;
        }
    }

    public double getUserWinningByRole(String userId, int roleId) {
        List<Integer> transId1 = Arrays.asList(3,4);
        Criteria criteria;
        double actualValue;

        if(roleId==35){
            criteria = Criteria.where("master").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==30){
            criteria = Criteria.where("superMaster").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==25){
            criteria = Criteria.where("superSuper").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==20){
            criteria = Criteria.where("admin").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==15){
            criteria = Criteria.where("superAdmin").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==10){
            criteria = Criteria.where("root").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else if(roleId==5){
            criteria = Criteria.where("rootAdmin").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return (-1)*(totalCredits + totalDebit); // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }
        else {
            criteria = Criteria.where("userId").is(userId).and("transTypeId").in(transId1);

            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group()
                            .sum("credits").as("totalCredits")
                            .sum("debit").as("totalDebit")
            );

            AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(
                    aggregation, "user-wallet", BasicDBObject.class
            );

            BasicDBObject aggregatedResult = result.getUniqueMappedResult();

            if (aggregatedResult != null) {
                double totalCredits = aggregatedResult.getDouble("totalCredits", 0.0);
                double totalDebit = aggregatedResult.getDouble("totalDebit", 0.0);
                return totalCredits + totalDebit; // Calculate balance as credits - debits
            } else {
                return 0.0;
            }
        }

    }
}
