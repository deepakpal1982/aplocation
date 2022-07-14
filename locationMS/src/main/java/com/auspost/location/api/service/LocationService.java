package com.auspost.location.api.service;

import java.util.Optional;

import com.auspost.location.api.entity.LocationEntity;
import com.auspost.location.api.model.AddLocationReq;

public interface LocationService {
    public Optional<LocationEntity> createLocation(AddLocationReq addLocationReq);
    public void deleteLocationById(String id);
    public Optional<LocationEntity> getLocationById(String id);
    public Iterable<LocationEntity> getAllLocations();
    public Iterable<LocationEntity> getLocationsBySuburbsOrPincode(String suburbs,String pincode);
}
