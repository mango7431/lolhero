package com.mango.lolhero.dto;

import lombok.Data;

@Data
public class ChampionMasteryDto {

    private String puuid;
    private int championId;
    private int championLevel;
    private long championPoints;
    private long lastPlayTime;
    private long championPointsSinceLastLevel;
    private long championPointsUntilNextLevel;
    private boolean chestGranted;
    private int tokensEarned;
    private String summonerId;
    private String championName;

}
