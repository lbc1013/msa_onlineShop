package com.example.catalogMicroservice.controller;

import com.example.catalogMicroservice.jpa.CatalogEntity;
import com.example.catalogMicroservice.service.CatalogService;
import com.example.catalogMicroservice.vo.CatalogResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
    Environment env;
    CatalogService catalogService;

    @Autowired
    public CatalogController(Environment env, CatalogService catalogService) {
        this.env = env;
        this.catalogService = catalogService;
    }

    @GetMapping("/health_check")
    public String statusCheck() {
        return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CatalogResponse>> getCatalogs() {
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<CatalogResponse> result = new ArrayList<>();
        catalogList.forEach(catalogEntity -> {
            result.add(new ModelMapper().map(catalogEntity, CatalogResponse.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
