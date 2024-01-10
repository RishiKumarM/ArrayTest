package com.soccertennisgame.soccertennis.SqlModel;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EX_T4_Table_Live_TL_VW")
public class EX_T4_Table_Live_TL_VW {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int Trans_Id;
//    public int getTransId()
//    {
//        return this.Trans_Id;
//    }
//    public void setTransId(int value)
//    {
//        this.Trans_Id = value;
//    }
//
//    private String mid;
//    public String getMid()
//    {
//        return this.mid;
//    }
//    public void setMid(String value)
//    {
//        this.mid = value;
//    }
//
//    private String MName;
//    public String getMName()
//    {
//        return this.MName;
//    }
//    public void setMName(String value)
//    {
//        this.MName = value;
//    }


//    private String sid;
//    public String getSid()
//    {
//        return this.sid;
//    }
//    public void setSid(String value)
//    {
//        this.sid = value;
//    }
//
//    private String nat;
//    public String getNat()
//    {
//        return this.nat;
//    }
//    public void setNat(String value)
//    {
//        this.nat = value;
//    }
//



//    private String utime;
//    public String getUtime()
//    {
//        return this.utime;
//    }
//    public void setUtime(String value)
//    {
//        this.utime = value;
//    }

    private String status;
    public String getStatus()
    {
        return this.status;
    }
    public void setStatus(String value)
    {
        this.status = value;
    }

    private String srno;
    public String getSrno()
    {
        return this.srno;
    }
    public void setSrno(String value)
    {
        this.srno = value;
    }

    private String event;
    public String getEvent()
    {
        return this.event;
    }
    public void setEvent(String value)
    {
        this.event = value;
    }


}
