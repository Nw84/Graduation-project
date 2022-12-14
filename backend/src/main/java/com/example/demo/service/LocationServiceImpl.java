package com.example.demo.service;

import java.util.List;

import org.apache.tomcat.util.descriptor.LocalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Location;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.RegionRepository;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    RegionRepository regionRepository;

    @Override
    public Location getLocation(Long id) {
        if(locationRepository.findById(id).isPresent()) {
            return locationRepository.findById(id).get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"); 
    }

    @Override
    public List<Location> getAllLocations() {
        return (List<Location>)locationRepository.findAll();
    }

    @Override
    public List<Location> getAllLocationsBasedOnRegion(Long id) {
        if(regionRepository.findById(id).isPresent()) {
            return (List<Location>)locationRepository.findAllByRegion(regionRepository.findById(id).get());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"); 
    }

    @Override
    public Location createNewLocation(String name, String text, Long id) {
        if(regionRepository.findById(id).isPresent()) {
            Location location = new Location();
            location.setName(name);
            location.setText(text);
            location.setRegion(regionRepository.findById(id).get());
            return locationRepository.save(location);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request"); 
    }
    
}
