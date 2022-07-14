package com.auspost.location.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.auspost.location.api.entity.LocationEntity;
import com.auspost.location.api.exception.ResourceNotFoundException;
import com.auspost.location.api.model.AddLocationReq;
import com.auspost.location.api.repository.LocationRepository;
import com.auspost.location.api.service.LocationServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationServiceTest {

 
  private final static String id = "a1b9b31d-e73c-4112-af7c-b68530f38222";
  private final static String nonExistId = "a1b9b31d-e73c-4112-af7c-b68530f38220";
  private static LocationEntity entity;
  private static AddLocationReq addLocationReq;
  @Mock
  private LocationRepository repository;
  @InjectMocks
  private LocationServiceImpl service;

  @BeforeAll
  public static void setup() {
    entity = new LocationEntity()
        .setId(UUID.fromString(id))
       
        .setSuburbs("City").setState("State").setCountry("Country").setPincode("12345");
    addLocationReq = new AddLocationReq().id(entity.getId().toString()).suburbs(entity.getSuburbs())
        .state(entity.getState()).country(entity.getCountry()).pincode(entity.getPincode());
  }

  @Test
  @DisplayName("returns an LocationEntity when private method toEntity() is called with Location model")
  public void convertModelToEntity() {
    // given
    LocationServiceImpl srvc = new LocationServiceImpl(repository);
    // when
    LocationEntity e = ReflectionTestUtils.invokeMethod(srvc, "toEntity", addLocationReq);
    // then
    then(e).as("Check Location entity is returned and not null").isNotNull();
   
    then(e.getSuburbs()).as("Check city is set").isEqualTo(entity.getSuburbs());
    then(e.getState()).as("Check state is set").isEqualTo(entity.getState());
    then(e.getCountry()).as("Check country is set").isEqualTo(entity.getCountry());
    then(e.getPincode()).as("Check pincode is set").isEqualTo(entity.getPincode());
  }

  @Test
  @DisplayName("save a new Location")
  public void createLocation() {
    given(repository.save(any())).willReturn(entity);
    // when
    Optional<LocationEntity> result = service.createLocation(addLocationReq);
    // then
    assertThat(result).isNotNull();
    assertThat(result).isNotEmpty();
    assertThat(result.get()).isEqualTo(entity);
  }

  @Test
  @DisplayName("return Location by the given existing id")
  public void getLocationByIdWhenExists() {
    given(repository.findById(UUID.fromString(id))).willReturn(Optional.of(entity));
    // when
    Optional<LocationEntity> result = service.getLocationById(id);
    // then
    assertThat(result).isNotNull();
    assertThat(result).isNotEmpty();
    assertThat(result.get()).isEqualTo(entity);
  }

  @Test
  @DisplayName("return empty Location by the given non-existing id")
  public void getLocationByIdWhenNotExists() {
    given(repository.findById(UUID.fromString(nonExistId)))
        .willReturn(Optional.empty());
    // when
    Optional<LocationEntity> result = service.getLocationById(nonExistId);
    // then
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("delete Location by given existing id")
  public void deleteLocationByIdWhenExists() {
    given(repository.findById(UUID.fromString(nonExistId)))
        .willReturn(Optional.of(entity));
    willDoNothing().given(repository).deleteById(UUID.fromString(nonExistId));
    // when
    service.deleteLocationById(nonExistId);
    // then
    verify(repository, times(1)).findById(UUID.fromString(nonExistId));
    verify(repository, times(1)).deleteById(UUID.fromString(nonExistId));
  }

  @Test
  @DisplayName("delete Location by given non-existing id, should throw ResourceNotFoundException")
  public void deleteLocationByNonExistId() throws Exception {
    given(repository.findById(UUID.fromString(nonExistId))).willReturn(Optional.empty())
        .willThrow(new ResourceNotFoundException(
            String.format("No Location found with id %s.", nonExistId)));
    // when
    try {
      service.deleteLocationById(nonExistId);
    } catch (Exception ex) {
      // then
      assertThat(ex).isInstanceOf(com.auspost.location.api.exception.ResourceNotFoundException.class);
      assertThat(ex.getMessage()).contains("No Location found with id " + nonExistId);
    }
    // then
    verify(repository, times(1)).findById(UUID.fromString(nonExistId));
    verify(repository, times(0)).deleteById(UUID.fromString(nonExistId));
  }

  @Test
  @DisplayName("return all Locationes")
  public void getAllLocation() {
    given(repository.findAll()).willReturn(List.of(entity));
    // when
    Iterable<LocationEntity> result = service.getAllLocations();
    // then
    assertThat(result).isNotNull();
    assertThat(result).contains(entity);
  }
}
