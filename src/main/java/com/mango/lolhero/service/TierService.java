package com.mango.lolhero.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.lolhero.dto.SummonerDTO;
import com.mango.lolhero.dto.TierDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "application-riotapi.properties")
public class TierService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${riot.api.key}")
    private String mykey;

    @Value("${riot.api.server.kr}")
    private String serverUrl;

    public List<TierDto> getTier(String id){

        List<TierDto> result;

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl+"/lol/league/v4/entries/by-summoner/"+id+"?api_key="+mykey);

            HttpResponse response = client.execute(request);


            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            TierDto[] tierDtos = objectMapper.readValue(entity.getContent(), TierDto[].class);
            result = Arrays.asList(tierDtos);

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

}
