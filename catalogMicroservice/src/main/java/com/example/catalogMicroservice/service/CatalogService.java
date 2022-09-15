package com.example.catalogMicroservice.service;

import com.example.catalogMicroservice.jpa.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
