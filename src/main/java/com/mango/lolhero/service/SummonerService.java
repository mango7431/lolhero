package com.mango.lolhero.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.lolhero.dto.SummonerDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "application-riotapi.properties")
public class SummonerService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String mykey;

    @Value("${riot.api.server.kr}")
    private String serverUrl;

    public SummonerDTO callRiotAPISummonerByName(String summonerName){

        SummonerDTO result;

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl+"/lol/summoner/v4/summoners/by-name/"+summonerName+"?api_key="+mykey);

            HttpResponse response = client.execute(request);


            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), SummonerDTO.class);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
