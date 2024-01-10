package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.BetTransaction;
import com.soccertennisgame.soccertennis.Model.FancyResultsProjection;
import com.soccertennisgame.soccertennis.mongodbProjection.BetHistoryProjection;
import com.soccertennisgame.soccertennis.mongodbProjection.BetTransactionProjection;
import com.soccertennisgame.soccertennis.mongodbProjection.BetTransactionWithFIDProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BetTransactionRepository extends MongoRepository<BetTransaction, String> {
    BetTransaction findByUserId(String email);

    BetTransaction findByIdAndUserId(String _id,String userId);

    BetTransaction findByUserIdAndMatchId(String userId, Integer matchId);
    List<BetTransaction> findByMatchIdAndBetStatus(Integer matchId,int betStatus);

    @Query(value = "{ 'userId': ?0,'matchId': ?1,'betStatus': ?2 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionByUserId(String userId, int matchId, int betStatus);

    @Query(value = "{ 'master': ?0,'matchId': ?1,'betStatus': ?2 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionByMasterId(String master, int matchId,int betStatus);


    @Query(value = "{ 'superMaster': ?0,'matchId': ?1,'betStatus': ?2}", sort = "{ 'betCreatedDate': -1 }",
            //  @Query(value = "{ 'superMaster': ?0,'matchId': ?1,'betStatus': ?2, 'betPlacedTime': { $gte: ?3, $lte: ?4 } }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionBySuperMasterId(String superMaster, int matchId,int betStatus);


    @Query(value = "{ 'superSuper': ?0,'matchId': ?1,'betStatus': ?2 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionBySuperSuperId(String superSuper, int matchId,int betStatus);

    @Query(value = "{ 'admin': ?0,'matchId': ?1,'betStatus': ?2 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionByadminId(String admin, int matchId,int betStatus);

    @Query(value = "{ 'superAdmin': ?0,'matchId': ?1,'betStatus': ?2}", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionBysuperAdminId(String superAdmin, int matchId,int betStatus);

    @Query(value = "{ 'root': ?0,'matchId': ?1,'betStatus': ?2 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionByrootId(String root, int matchId,int betStatus);

    @Query(value = "{ 'rootAdmin': ?0,'matchId': ?1,'betStatus': ?2 }", sort = "{ 'betCreatedDate': -1 }",
            //    @Query(value = "{ 'rootAdmin': ?0,'matchId': ?1,'betStatus': ?2, 'betPlacedTime': { $gte: ?3, $lte: ?4 } }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'matchId': 4, 'betCreatedDate':8 ,'userId': 7, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionProjection> findBetTransactionByrootAdminId(String rootAdmin, int matchId,int betStatus);


    // for matchId and fancyId bet Details //
    @Query(value = "{ 'userId': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3 }", sort = "{ 'betCreatedDate': -1 }",
            //  @Query(value = "{ 'userId': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3, 'betPlacedTime': { $gte: ?4, $lte: ?5 } }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'ip':55, 'address': 56, 'winnerName': 57, 'betType': 13, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7,'yesLine': 40, 'noLine': 41, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdByUserId(String userId, int matchId, int fancyId, int betStatus);

    @Query(value = "{ 'master': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3}", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'ip':55, 'address': 56, 'winnerName': 57, 'betType': 13, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdByMasterId(String master, int matchId, int fancyId, int betStatus);


    @Query(value = "{ 'superMaster': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3 }", sort = "{ 'betCreatedDate': -1 }",
            //     @Query(value = "{ 'superMaster': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3, 'betPlacedTime': { $gte: ?4, $lte: ?5 } }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'ip':55, 'address': 56, 'winnerName': 57, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdBySuperMasterId(String superMaster, int matchId, int fancyId, int betStatus);


    @Query(value = "{ 'superSuper': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3}", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'ip':55, 'address': 56, 'winnerName': 57, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdBySuperSuperId(String superSuper, int matchId, int fancyId, int betStatus);

    @Query(value = "{ 'admin': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'ip':55, 'address': 56, 'winnerName': 57, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdByadminId(String admin, int matchId, int fancyId, int betStatus);

    @Query(value = "{ 'superAdmin': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3 }", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'ip':55, 'address': 56, 'winnerName': 57, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdBysuperAdminId(String superAdmin, int matchId, int fancyId, int betStatus);

    @Query(value = "{ 'root': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3}", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'ip':55, 'address': 56, 'winnerName': 57, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userid': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdByrootId(String root, int matchId, int fancyId, int betStatus);

    @Query(value = "{ 'rootAdmin': ?0,'matchId': ?1,'fancyId':?2,'betStatus': ?3}", sort = "{ 'betCreatedDate': -1 }",
            fields = "{ 'betForTeam': 12, 'matchName': 5, 'odds': 9, 'stake': 10, 'matchBetType': 20, 'betType': 13, 'ip':55, 'address': 56, 'winnerName': 57, 'fancyId': 3, 'matchId': 4, 'fancyResults':18, 'betCreatedDate':8 ,'userId': 7, 'yesLine': 40, 'noLine': 41, 'master': 46, 'betProfit': 18, 'betLoss': 19, 'betResult': 21, 'ip': 61 , 'address': 62 }")
    List<BetTransactionWithFIDProjection> findBetTransactionWithMatchIdAndFancyIdByrootAdminId(String rootAdmin, int matchId, int fancyId, int betStatus);



    @Query(value = "{ 'fancyId': ?0,'matchId': ?1, 'betStatus': ?2 }",
            fields = "{ '_id': 1, 'userId' :7, 'betForTeam': 11, 'odds': 8, 'stake': 9, 'betProfit': 14, 'betLoss': 15 , 'matchBetType': 16, 'betType': 12,'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }") //'betPlacedTime':
    List<FancyResultsProjection> getFancyByFancyId(int fancyId, int matchId, int betStatus); //String userId, int matchId

    // @Query(value = "{ $and: [ { 'matchId': ?0 }, { 'matchBetType': { $in: [ 'O', 'O' ] } }] } ] }",
    @Query(value = "{ $and: [ { 'matchId': ?0 }, { 'matchBetType': { $in: ?1} }, { 'betStatus': ?2 } ] } ] }",

            //"{ 'fancyId': ?0,'matchId': ?1, 'betStatus': ?2 }",
            fields = "{ '_id': 1, 'userId' :7,'betTeamId' :10, 'betForTeam': 11, 'odds': 8, 'stake': 9, 'betProfit': 14, 'betLoss': 15 , 'matchBetType': 16, 'betType': 12, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }") //'betPlacedTime':
    List<FancyResultsProjection> getOddsBookByMatchId(int matchId, List<String> matchBetType, int betStatus); //String userId, int matchId



    // Bets History by User for all matches

    @Query(value = "{ 'userId': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'matchName': 5, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62  }")
    List<BetHistoryProjection> findBetTransactionByUserId(String userId, int betStatus, LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'master': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionByMasterId(String master,int betStatus, LocalDateTime startTime, LocalDateTime endTime);


    @Query(value = "{ 'superMaster': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionBySuperMasterId(String superMaster,int betStatus, LocalDateTime startTime, LocalDateTime endTime);


    @Query(value = "{ 'superSuper': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionBySuperSuperId(String superSuper,int betStatus, LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'admin': ?1,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionByAdminId(String admin,int betStatus, LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'superAdmin': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionBySuperAdminId(String superAdmin,int betStatus, LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'root': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionByRootId(String root, int betStatus, LocalDateTime startTime, LocalDateTime endTime);

    @Query(value = "{ 'rootAdmin': ?0,'betStatus': ?1, 'betPlacedTime': { $gte: ?2, $lte: ?3 } }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }")
    List<BetHistoryProjection> findBetTransactionByRootAdminId(String rootAdmin, int betStatus, LocalDateTime betPlacedTime, LocalDateTime endTime);

    //@Query(value = "{ 'userId': ?0,'betStatus': ?1,'matchId': ?2 ,'fancyId': ?3, 'betPlacedTime': { $gte: ?4, $lte: ?5 }  }", sort = "{ 'betPlacedTime': -1 }",
    @Query(value = "{ 'userId': ?0,'betStatus': ?1,'matchId': ?2 ,'fancyId': ?3 }", sort = "{ 'betPlacedTime': -1 }",
            fields = "{ '_id':1, 'betForTeam': 10, 'odds': 6, 'stake': 7, 'matchBetType': 16, 'betType': 11, 'userId': 7, 'betPlacedTime': 25, 'betProfit': 16, 'betLoss': 17, 'betResult': 19, 'betStatus': 15, 'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62  }")
    List<BetHistoryProjection> findBetTransactionByUserIdByBetStatusByMatchIdByFancyId(String userId,int betStatus,int matchId,int fancyId);


    // Get Toss Bet
    @Query(value = "{ 'matchBetType': ?0,'matchId': ?1, 'betStatus': ?2 }",
            fields = "{ '_id': 1, 'userId' :7, 'betForTeam': 11, 'odds': 8, 'stake': 9, 'betProfit': 14, 'betLoss': 15 , 'matchBetType': 16, 'betType': 12,'rootAdmin': 30,'root': 31,'superAdmin': 32,'admin': 33,'superSuper': 34,'superMaster' :35,'master' :36, 'ip': 61 , 'address': 62 }") //'betPlacedTime':
    List<FancyResultsProjection> getTossByMatchBetTypeIdByMatchId(String matchBetType, int matchId, int betStatus); //String userId, int matchId

    @Query(value = "{ 'matchId': ?0,  'matchBetType': { $in: ?1 }, 'betStatus': ?2 }")
    List<BetTransaction> getAllBetsByMatchIdMarketType(int matchId,List<String> matchBetType, int betStatus); //String userId, int matchId

    BetTransaction findByUserIdAndBetCreatedDate(String userId, LocalDateTime betCreatedDate);

    List<BetTransaction> findByBetCreatedDateIsBefore(LocalDateTime preTwoDays);

    List<BetTransaction> findByMarketId(String marketId);


//    List<DateWiseData> findByDateBetween(LocalDate startDate, LocalDate endDate);

//    @Query(value = "{ 'fancyId': ?0,'matchId': ?1 }") //'betPlacedTime':
//    int updateBetStatus(int fancyId, int matchId);

}
