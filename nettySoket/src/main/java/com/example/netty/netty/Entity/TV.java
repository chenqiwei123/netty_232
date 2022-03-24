package com.example.netty.netty.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TV implements Serializable {
    private String table;
    private String DT;
    private Float VAL;
    private Integer STATUS;

    public TV(String table, String DT, Float VAL, Integer STATUS) {
        this.table = table;
        this.DT = DT;
        this.VAL = VAL;
        this.STATUS = STATUS;
    }
}
