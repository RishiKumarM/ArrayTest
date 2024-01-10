package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.BetTransaction;
import com.soccertennisgame.soccertennis.Model.Luck7bResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Luck7bResultMongoRepository  extends MongoRepository<Luck7bResult, String> {

    Luck7bResult findByMid(String mid);
}
