package com.soccertennisgame.soccertennis.SqlRepository;


import com.soccertennisgame.soccertennis.SqlModel.EX_T1_Table_Live_TL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EX_T1_Table_Live_TLRepository extends JpaRepository<EX_T1_Table_Live_TL, Integer> {
    @Query("SELECT top 1 e1.* FROM EX_T1_Table_Live_TL e1 WHERE event=:event  e1.srno = :srno and e1.MName=:MName  ")
    EX_T1_Table_Live_TL findByEventAndSrnoAndMName(String event, String srno, String MName);
    @Query("SELECT top 1 e1.* FROM EX_T1_Table_Live_TL e1 WHERE event=:event  e1.sid = :sid and e1.MName=:MName  ")
    EX_T1_Table_Live_TL findByEventAndSidAndMName(String event,String sid, String MName);

}