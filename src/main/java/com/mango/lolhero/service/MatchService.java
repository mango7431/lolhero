package com.mango.lolhero.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.lolhero.dto.SummonerDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String mykey;

    @Value("${riot.api.server.asia}")
    private String serverUrl;

    public List<String> MatchList(String puuid){

        List<String> result = new ArrayList<String>();

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl+"/lol/match/v5/matches/by-puuid/"+puuid+"/ids?start=0&count=20&api_key="+mykey);

            HttpResponse response = client.execute(request);


            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            List<String> matchIds = objectMapper.readValue(content, new TypeReference<List<String>>() {});
            result.addAll(matchIds);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

}
