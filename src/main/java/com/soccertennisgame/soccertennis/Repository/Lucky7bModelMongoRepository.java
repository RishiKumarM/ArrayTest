package com.soccertennisgame.soccertennis.Repository;

import com.soccertennisgame.soccertennis.Model.Lucky7bModelMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Lucky7bModelMongoRepository extends MongoRepository<Lucky7bModelMongo, String> {

    Lucky7bModelMongo findByMid(String mid);
}
