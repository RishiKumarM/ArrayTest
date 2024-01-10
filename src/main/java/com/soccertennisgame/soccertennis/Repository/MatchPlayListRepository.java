package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.MatchPlayListModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MatchPlayListRepository extends MongoRepository<MatchPlayListModel, String> {

    void deleteByEventId(String eventId);

    @Query(value = "{ 'eventId': ?0}")
        /// fields = "{ 'EventTypeId': 2, 'runnerName1': 25 , 'runnerName2': 26, 'runnerName3': 27, 'selectionId1': 27, 'selectionId2': 29, 'selectionId3': 30 }")
    MatchPlayListModel findByEventId(String eventId);

}