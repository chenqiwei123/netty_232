package com.example.netty.netty.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TVTable implements Serializable {
    private Date DT;
    private Float VAL;
    private Integer STATUS;

    public TVTable(Date DT, Float VAL, Integer STATUS) {
        this.DT = DT;
        this.VAL = VAL;
        this.STATUS = STATUS;
    }
}
