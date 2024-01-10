package com.soccertennisgame.soccertennis.SqlModel;

import jakarta.persistence.*;


import java.math.BigDecimal;

@Entity
@Table(name = "EX_T1_Table_Live_TL_VW")
public class EX_T1_Table_Live_TL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Trans_Id11;
    public int getTransId()
    {
        return this.Trans_Id11;
    }
    public void setTransId(int value)
    {
        this.Trans_Id11 = value;
    }

    private String mid;
    public String getMid()
    {
        return this.mid;
    }
    public void setMid(String value)
    {
        this.mid = value;
    }

    private String MName;
    public String getMName()
    {
        return this.MName;
    }
    public void setMName(String value)
    {
        this.MName = value;
    }

    private String iplay;
    public String getIplay()
    {
        return this.iplay;
    }
    public void setIplay(String value)
    {
        this.iplay = value;
    }

    private String sid;
    public String getSid()
    {
        return this.sid;
    }
    public void setSid(String value)
    {
        this.sid = value;
    }

    private String nat;
    public String getNat()
    {
        return this.nat;
    }
    public void setNat(String value)
    {
        this.nat = value;
    }

    private BigDecimal b1;
    public BigDecimal getB1()
    {
        return this.b1;
    }
    public void setB1(BigDecimal value)
    {
        this.b1 = value;
    }

    private BigDecimal bs1;
    public BigDecimal getBs1()
    {
        return this.bs1;
    }
    public void setBs1(BigDecimal value)
    {
        this.bs1 = value;
    }

    private BigDecimal l1;
    public BigDecimal getL1()
    {
        return this.l1;
    }
    public void setL1(BigDecimal value)
    {
        this.l1 = value;
    }

    private BigDecimal ls1;
    public BigDecimal getLs1()
    {
        return this.ls1;
    }
    public void setLs1(BigDecimal value)
    {
        this.ls1 = value;
    }

    private BigDecimal b2;
    public BigDecimal getB2()
    {
        return this.b2;
    }
    public void setB2(BigDecimal value)
    {
        this.b2 = value;
    }

    private BigDecimal bs2;
    public BigDecimal getBs2()
    {
        return this.bs2;
    }
    public void setBs2(BigDecimal value)
    {
        this.bs2 = value;
    }

    private BigDecimal l2;
    public BigDecimal getL2()
    {
        return this.l2;
    }
    public void setl2(BigDecimal value)
    {
        this.l2 = value;
    }

    private BigDecimal ls2;
    public BigDecimal getLs2()
    {
        return this.ls2;
    }
    public void setLs2(BigDecimal value)
    {
        this.ls2 = value;
    }

    private BigDecimal b3;
    public BigDecimal getB3()
    {
        return this.b3;
    }
    public void setB3(BigDecimal value)
    {
        this.b3 = value;
    }

    private BigDecimal bs3;
    public BigDecimal getBs3()
    {
        return this.bs3;
    }
    public void setBs3(BigDecimal value)
    {
        this.bs3 = value;
    }

    private BigDecimal l3;
    public BigDecimal getL3()
    {
        return this.l3;
    }
    public void setL3(BigDecimal value)
    {
        this.l3 = value;
    }

    private BigDecimal ls3;
    public BigDecimal getLs3()
    {
        return this.ls3;
    }
    public void setLs3(BigDecimal value)
    {
        this.ls3 = value;
    }

    private String gtype;
    public String getGtype()
    {
        return this.gtype;
    }
    public void setGType(String value)
    {
        this.gtype = value;
    }

    private String utime;
    public String getUtime()
    {
        return this.utime;
    }
    public void setUtime(String value)
    {
        this.utime = value;
    }

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

    public EX_T1_Table_Live_TL() {

    }
    // public EX_T1_Table_Live_TL(int TransId_,String mid_,String mstatus_,String mname_,String iplay_,String sid_,String nat_,BigDecimal b1_,BigDecimal bs1_,BigDecimal l1_,BigDecimal ls1_,BigDecimal b2_,BigDecimal bs2_,BigDecimal l2_,BigDecimal ls2_,BigDecimal b3_,BigDecimal bs3_,BigDecimal l3_,BigDecimal ls3_,String gtype_,String utime_,String status_,String srno_,java.sql.Date adddate_,String event_id_,String key_,String type_,java.sql.Date LastChecked_,String vendor_,int isClosed_,String record_type_,int isAddedtoMarket_)
    public EX_T1_Table_Live_TL(int TransId_,String mid_,String mname_,String srno_,String event_id_,String iplay_,String sid_,String nat_,BigDecimal b1_,BigDecimal bs1_,BigDecimal l1_,BigDecimal ls1_,BigDecimal b2_,BigDecimal bs2_,BigDecimal l2_,BigDecimal ls2_,BigDecimal b3_,BigDecimal bs3_,BigDecimal l3_,BigDecimal ls3_,String gtype_,String utime_,String status_)
    {
        this.Trans_Id11 = TransId_;
        this.mid = mid_;
//        this.mStatus = mstatus_;
        this.MName = mname_;
        this.iplay = iplay_;
        this.sid = sid_;
        this.nat = nat_;
        this.b1 = b1_;
        this.bs1 = bs1_;
        this.l1 = l1_;
        this.ls1 = ls1_;
        this.b2 = b2_;
        this.bs2 = bs2_;
        this.l2 = l2_;
        this.ls2 = ls2_;
        this.b3 = b3_;
        this.bs3 = bs3_;
        this.l3 = l3_;
        this.ls3 = ls3_;
        this.gtype = gtype_;
        this.utime = utime_;
        this.status = status_;
        this.srno = srno_;
//        this.adddate = adddate_;
        this.event = event_id_;
//        this.key = key_;
//        this.type = type_;
        // this.LastChecked = LastChecked_;
//        this.vendor = vendor_;
//        this.isClosed = isClosed_;
//        this.record_type = record_type_;
//        this.isAddedToMarket = isAddedtoMarket_;
    }
}
