package com.auspost.location.api.service;

import com.auspost.location.api.entity.LocationEntity;
import com.auspost.location.api.model.AddLocationReq;
import com.auspost.location.api.repository.LocationRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository repository;

    public LocationServiceImpl(LocationRepository repository) {
      this.repository = repository;
    }
  
    @Override
    public Optional<LocationEntity> createLocation(AddLocationReq addLocationReq) {
      return Optional.of(repository.save(toEntity(addLocationReq)));
    }
  
    @Override
    public void deleteLocationById(String id) {
      repository.deleteById(UUID.fromString(id));
    }
  
    @Override
    public Optional<LocationEntity> getLocationById(String id) {
      return repository.findById(UUID.fromString(id));
    }
  
    @Override
    public Iterable<LocationEntity> getAllLocations() {
      return repository.findAll();
    }

    @Override
    public Iterable<LocationEntity> getLocationsBySuburbsOrPincode(String suburbs,String pincode) {
     
      return repository.findBySuburbsOrPincode(suburbs,pincode);
    }
  
    private LocationEntity toEntity(AddLocationReq model) {
      LocationEntity entity = new LocationEntity();
      return entity.setSuburbs(model.getSuburbs()).setState(model.getState())
          .setCountry(model.getCountry()).setPincode(model.getPincode());
    }
    
}
