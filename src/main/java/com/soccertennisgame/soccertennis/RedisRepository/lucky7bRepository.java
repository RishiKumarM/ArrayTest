package com.soccertennisgame.soccertennis.RedisRepository;

import com.soccertennisgame.soccertennis.Model.CasinoData;
import com.soccertennisgame.soccertennis.Model.lucky7bModel;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

@EnableRedisRepositories
public interface lucky7bRepository extends CrudRepository<lucky7bModel, Serializable> {
}
