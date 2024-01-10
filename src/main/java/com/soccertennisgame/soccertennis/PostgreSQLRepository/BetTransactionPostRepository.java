package com.soccertennisgame.soccertennis.PostgreSQLRepository;

import com.soccertennisgame.soccertennis.PostGreModel.BetTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BetTransactionPostRepository extends JpaRepository<BetTransaction, String> {

}