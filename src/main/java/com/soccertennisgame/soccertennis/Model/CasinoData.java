package com.soccertennisgame.soccertennis.Model;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@Getter
@RedisHash("casinoData")
public class CasinoData {

    @Id
    @Indexed
    private String id;
    private boolean success;
    private Data data;

    public CasinoData(String automaticMessageFromServer) {

    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<T1Item> t1;
        private List<T2Item> t2;

        public static class T1Item {
            private String mid;
            private String autotime;
            private String gtype;
            private int min;
            private int max;
            private String c1;
        }

        public static class T2Item {
            private String mid;
            private String nat;
            private String sid;
            private String rate;
            private String gstatus;
            private int min;
            private int max;
        }
    }
}