package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.FancyDetails;
import com.soccertennisgame.soccertennis.Model.FancyResultsProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FancyDetailsRepository extends MongoRepository<FancyDetails, String> {

    @Query(value = "{ 'fancyId': ?0,'matchId': ?1 }",
            fields = "{ 'betForTeam': 10, 'odds': 7, 'stake': 8, 'betProfit': 13, 'betLoss': 14 , 'matchBetType': 15, 'betType': 11,'rootAdmin': 28,'root': 29,'superAdmin': 30,'admin': 31,'superSuper': 32,'superMaster' :33,'master' :34 }") //'betPlacedTime':
    List<FancyResultsProjection> getFancyByFancyId(int fancyId, int matchId); //String userId, int matchId

    @Query(value = "{ 'marketType': ?0 }",
            fields = "{ 'matchId': 2, 'matchName': 3, 'winnerTeamName': 4, 'fancyId': 5, 'fancyResult': 6 , 'marketType': 8,'addDate':7}") //'betPlacedTime':
    List<FancyDetails> findFancyDetailsByMarketType(String marketType); //String userId, int matchId

    @Query(value = "{ 'marketType': { $in: ?0 }}",
            fields = "{ 'matchId': 2, 'matchName': 3, 'winnerTeamName': 4, 'fancyId': 5, 'fancyResult': 6 , 'marketType': 8,'addDate':7}") //'betPlacedTime':
    List<FancyDetails> findFancyDetailsByMarketTypes(List<String> marketType); //String userId, int matchId


}