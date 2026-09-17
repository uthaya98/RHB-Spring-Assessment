package com.rhb.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExternalApiService {

    private final RestClient restClient;

    public ExternalApiService() {
        this.restClient = RestClient.builder().baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    public String getExternalUser(Long id){
        return restClient.get().uri("/users/{id}", id).retrieve().body(String.class);
    }
}
