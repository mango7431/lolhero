package com.mango.lolhero.dto;

import lombok.ToString;

import java.util.Map;

@ToString
public class ChampionData {

    private Map<String, ChampionInfo> data;

    public Map<String, ChampionInfo> getData() {
        return data;
    }

    public void setData(Map<String, ChampionInfo> data) {
        this.data = data;
    }
}
