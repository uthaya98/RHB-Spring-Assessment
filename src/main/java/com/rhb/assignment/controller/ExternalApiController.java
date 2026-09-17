package com.rhb.assignment.controller;

import com.rhb.assignment.service.ExternalApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getExternalUser(@PathVariable Long id){
        return ResponseEntity.ok(externalApiService.getExternalUser(id));
    }
}
