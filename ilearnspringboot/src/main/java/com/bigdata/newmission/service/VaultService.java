package com.bigdata.newmission.service;

import com.bigdata.newmission.domain.http.HCPTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class VaultService {
    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(value = "token")
    public HCPTokenResponse token() throws Exception{
        try {
            var header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("client_id", "WYeaH1PZbgxo0SSR1gVFlMnHnYY2H4AT");
            body.add("client_secret", "5hcjmq3q8cATWbIzLrkRlxxg0WRahEZY7MCi-k05CKKJz8b-84vPZ-opoSojG85j");
            body.add("grant_type", "client_credentials");
            body.add("audience", "https://api.hashicorp.cloud");

            String url = "https://auth.idp.hashicorp.com/oauth2/token";

            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, header);
            var response = restTemplate.postForEntity(url, httpEntity, HCPTokenResponse.class);
            return response.getBody();
        }catch (HttpServerErrorException httpServerErrorException){
            throw httpServerErrorException;
        }catch(Exception ex){
            throw ex;
        }
    }
}
