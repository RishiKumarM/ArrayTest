package com.soccertennisgame.soccertennis.RedisRepository;

import com.soccertennisgame.soccertennis.Model.CasinoData;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

@EnableRedisRepositories
public interface CasinoDataRepository extends CrudRepository<CasinoData, Serializable> {

}
