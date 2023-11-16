package com.mango.lolhero.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.lolhero.dto.ChampionData;
import com.mango.lolhero.dto.ChampionInfo;
import com.mango.lolhero.dto.ChampionMasteryDto;
import com.mango.lolhero.dto.SummonerDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "application-riotapi.properties")
public class ChampionMasteryService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String mykey;

    @Value("${riot.api.server.kr}")
    private String serverUrl;

    public List<ChampionMasteryDto> ChampionMastery(String id){

        List<ChampionMasteryDto> result = new ArrayList<>();

        String url = "https://ddragon.leagueoflegends.com/cdn/13.23.1/data/ko_KR/champion.json";
        RestTemplate restTemplate = new RestTemplate();
        ChampionData championData = restTemplate.getForObject(url, ChampionData.class);

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl+"/lol/champion-mastery/v4/champion-masteries/by-summoner/"+id+"?api_key="+mykey);

            HttpResponse response = client.execute(request);


            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            ChampionMasteryDto[] championMasteryDtos = objectMapper.readValue(entity.getContent(), ChampionMasteryDto[].class);
            for (ChampionMasteryDto champion : championMasteryDtos){
                ChampionInfo championInfo = null;
                for (ChampionInfo info : championData.getData().values()){
                    if(Integer.parseInt(info.getKey())==champion.getChampionId()){
                        championInfo = info;
                        break;
                    }
                }
                if(championInfo!=null){
                    champion.setChampionName(championInfo.getId());
                }
            }
            result = Arrays.asList(championMasteryDtos);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }


}
