package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Place;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    
}
