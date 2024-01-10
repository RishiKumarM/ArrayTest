package com.soccertennisgame.soccertennis.PostgreSQLRepository;

import com.soccertennisgame.soccertennis.PostGreModel.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletPostRepository extends JpaRepository<UserWallet, String> {


}
