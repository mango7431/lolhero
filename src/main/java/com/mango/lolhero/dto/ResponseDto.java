package com.mango.lolhero.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {

    // 응답성공
    private String responseBody;

    // 응답실패
    private StatusDto status;

    public ResponseDto(String responseBody){
        this.responseBody = responseBody;
    }

    public ResponseDto(int status_cod, String message){
        this.status = new StatusDto(status_cod,message);
        this.responseBody = message;
    }

    public boolean isOK(){
        if(this.status!=null){
            return false;
        }
        return true;
    }

}
