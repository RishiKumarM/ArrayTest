package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.SoccerTennisModel.SoTeData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SoTeDataRepository extends MongoRepository<SoTeData, String> {
    SoTeData findByMarketId(String marketId);
    List<SoTeData> findByStatusNotAndIsMatchResultUpdated(String status, int isMatchResultUpdated);

}
