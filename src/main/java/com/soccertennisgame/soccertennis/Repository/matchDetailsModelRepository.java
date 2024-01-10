package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.matchDetailsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface matchDetailsModelRepository extends MongoRepository<matchDetailsModel, String> {
    List<matchDetailsModel> findByGameStatus(int GameStatus);
    @Query("{ 'event_date' : { $gt : ?0 } }")
    List<matchDetailsModel> getEventDate(LocalDateTime lastDateInSQL);

    matchDetailsModel findByMarketId(String marketId);

    matchDetailsModel findByEventId(String eventId);

}