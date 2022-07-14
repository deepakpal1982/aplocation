package com.auspost.location.api.controller;

import static org.springframework.http.ResponseEntity.accepted;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import com.auspost.location.api.LocationApi;
import com.auspost.location.api.hateoas.LocationRepresentationModelAssembler;
import com.auspost.location.api.model.AddLocationReq;
import com.auspost.location.api.model.Location;
import com.auspost.location.api.service.LocationService;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : github.com/deepakpal1982
 * @project : locationMS
 * @created : 14/07/2021, Tuesday
 **/
@RestController
public class LocationController implements LocationApi {
  
  private LocationService service;
  private final LocationRepresentationModelAssembler assembler;

  public LocationController(LocationService service, LocationRepresentationModelAssembler assembler) {
    this.service = service;
    this.assembler = assembler;
  }

  @Override
  public ResponseEntity<Location> createLocation(@Valid AddLocationReq addLocationReq) {
    return status(HttpStatus.CREATED).body(service.createLocation(addLocationReq)
    .map(assembler::toModel).get());
  }

  @Override
  public ResponseEntity<Void> deleteLocationById(String id) {
    service.deleteLocationById(id);
    return accepted().build();
  }

  @Override
  public ResponseEntity<List<Location>> getAllLocations() {
    return ok(assembler.toListModel(service.getAllLocations()));
  }

  @Override
  public ResponseEntity<Location> getLocationById(String id) {
    return service.getLocationById(id).map(assembler::toModel)
        .map(ResponseEntity::ok).orElse(notFound().build());
  }

  @Override
  public ResponseEntity<List<Location>> getLocations(@Valid String suburbs, @Valid String pincode, @Valid Integer page,
      @Valid Integer size) {
        return ok(assembler.toListModel(service.getLocationsBySuburbsOrPincode(suburbs,pincode)));
  }

  
  
}
