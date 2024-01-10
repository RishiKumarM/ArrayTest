package com.soccertennisgame.soccertennis.SqlRepository;

import com.soccertennisgame.soccertennis.SqlModel.EX_T4_Table_Live_TL_VW;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EX_T4_Table_Live_TLRepository extends JpaRepository<EX_T4_Table_Live_TL_VW, String> {

    @Query("SELECT top 1 status  FROM EX_T4_Table_Live_TL_VW e1 WHERE event=:event and srno=1")
    List<EX_T4_Table_Live_TL_VW> findByEvent(String event);

    @Procedure(name = "addMatchToMarket_ZDB")
    String getToss(@Param("MatchId") int MatchId);


}
