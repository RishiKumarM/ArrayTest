package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.AggregatedResult;
import com.soccertennisgame.soccertennis.Model.UserWallet;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserWalletRepository extends MongoRepository<UserWallet, String> {
    UserWallet findAllById(String userId);

    UserWallet findAllByChar01(String char01);

    @Aggregation(pipeline = {
            "{$group: {_id: email, totalBalance: {$sum: \"$balance\"}}}"
    })
    AggregationResults<AggregatedResult> calculateTotalBalance();

    @Query("{'userId': ?0}")
    void updateUser(String email, String update);

//    @Query("{'matchId': ?0,'marketType': { $in: ?1 }}")
//    void deleteByMatchIdAndMarketType(int matchId,  List<String> marketType);

    @Query("{'matchId': :matchId, 'marketType': { $in: ?#{#marketTypes} }}")
    void deleteByMatchIdAndMarketType(@Param("matchId") int matchId, @Param("marketTypes") List<String> marketTypes);

    List<UserWallet> findByCreatedDateBefore(LocalDateTime preTwoDays);

    List<UserWallet> findByMarketId(String marketId);

}