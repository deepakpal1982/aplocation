package com.auspost.location.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.auspost.location.api.controller.LocationController;
import com.auspost.location.api.entity.LocationEntity;
import com.auspost.location.api.exception.ResourceNotFoundException;
import com.auspost.location.api.exception.RestApiErrorHandler;
import com.auspost.location.api.hateoas.LocationRepresentationModelAssembler;
import com.auspost.location.api.model.AddLocationReq;
import com.auspost.location.api.model.Location;
import com.auspost.location.api.service.LocationService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author : github.com/sharmasourabh
 * @project : Chapter08 - Modern API Development with Spring and Spring Boot
 **/
@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

  private static final String URI = "/api/v1/locations";
  private static final String id = "a731fda1-aaad-42ea-bdbc-a27eeebe2cc0";
  private static MockMvc mockMvc;
  private static LocationService service = mock(LocationService.class);
  private static MessageSource msgSource = mock(MessageSource.class);
  private static LocationController controller;
  private static LocationEntity entity;
  private static Location location;
  private static AddLocationReq addLocationReq;
  private JacksonTester<Location> LocationJackson;
  private JacksonTester<AddLocationReq> addLocationReqJackson;

  @BeforeAll
  public static void setup() {
    controller = new LocationController(service, new LocationRepresentationModelAssembler());
    mockMvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(new RestApiErrorHandler(msgSource))
        .alwaysDo(print())
        .build();

    entity = new LocationEntity()
        .setId(UUID.fromString(id))
        .setSuburbs("City").setState("State").setCountry("Country").setPincode("12345");
    addLocationReq = new AddLocationReq().id(entity.getId().toString()).suburbs(entity.getSuburbs())
        .state(entity.getState()).country(entity.getCountry()).pincode(entity.getPincode());
    location = new Location().id(entity.getId().toString()).suburbs(entity.getSuburbs())
        .state(entity.getState()).country(entity.getCountry()).pincode(entity.getPincode());
  }

  @BeforeEach
  public void configureJsonTester() {
    JacksonTester.initFields(this, new AppConfig().objectMapper());
  }

  @Test
  @DisplayName("returns Location by given existing ID")
  public void getLocationByOrderIdWhenExists() throws Exception {
    given(service.getLocationById(id)).willReturn(Optional.of(entity));

    // when
    ResultActions result = mockMvc.perform(
        get("/api/v1/locations/a731fda1-aaad-42ea-bdbc-a27eeebe2cc0")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    // then
    result.andExpect(status().isOk());
    verifyJson(result);
  }

  private void verifyJson(final ResultActions result) throws Exception {
    final String BASE_PATH = "http://localhost:8080";
    result
        .andExpect(jsonPath("id", is(entity.getId().toString())))
       
        .andExpect(jsonPath("suburbs", is(entity.getSuburbs())))
        .andExpect(jsonPath("state", is(entity.getState())))
        .andExpect(jsonPath("country", is(entity.getCountry())))
        .andExpect(jsonPath("pincode", is(entity.getPincode())))
       
        .andExpect(
            jsonPath("links[1].href", is(BASE_PATH + URI + "/" + entity.getId())));
  }

  @Test
  @DisplayName("returns NOT FOUND response when non-existing ID is used for fetching Location")
  public void getLocationByOrderIdWhenNotExists() throws Exception {
    given(service.getLocationById("a1b9b31d-e73c-4112-af7c-b68530f38199"))
        .willReturn(Optional.empty());

    // when
    MockHttpServletResponse response = mockMvc.perform(
        get("/api/v1/locations/a1b9b31d-e73c-4112-af7c-b68530f38199")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.getContentAsString()).isEmpty();
  }

  @Test
  @DisplayName("returns newly created Location with CREATED status")
  public void createLocation() throws Exception {
    given(service.createLocation(addLocationReq)).willReturn(Optional.of(entity));

    // when
    ResultActions result = mockMvc.perform(
        post("/api/v1/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(addLocationReqJackson.write(addLocationReq).getJson())
            .accept(MediaType.APPLICATION_JSON));

    // then
    result.andExpect(status().isCreated());
    verifyJson(result);
  }

  @Test
  @DisplayName("returns all Location")
  public void getAllLocations() throws Exception {
    given(service.getAllLocations()).willReturn(List.of(entity));

    // when
    ResultActions result = mockMvc.perform(
        get("/api/v1/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    // then
    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$", hasSize(1)));
    result.andExpect(jsonPath("$[0].id", is(id)));
  }

  @Test
  @DisplayName("delete an Location by given existing id")
  public void deleteLocationByIdWhenExists() throws Exception {
    // given
    willDoNothing().given(service).deleteLocationById(id);

    // when
    ResultActions result = mockMvc.perform(
        delete("/api/v1/locations/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    // then
    verify(service, times(1)).deleteLocationById(id);
    result.andExpect(status().isAccepted());
  }

  @Test
  @DisplayName("delete an Location by given non-existing id will throw the exception")
  public void deleteLocationByIdWhenNotExists() throws Exception {
    // given
    final String nonExistId = "abc";
    willThrow(
        new ResourceNotFoundException(String.format("No Location found with id %s.", nonExistId)))
        .willDoNothing().given(service).deleteLocationById(nonExistId);

    // when
    ResultActions result = mockMvc.perform(
        delete("/api/v1/locations/{id}", nonExistId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    // then
    verify(service, times(1)).deleteLocationById(nonExistId);
    result.andExpect(status().isNotFound());
    result.andExpect(jsonPath("errorCode", is("AUSPOST-0010")));
    result.andExpect(jsonPath("message", is("Requested resource not found. No Location found with id abc.")));
    result.andExpect(jsonPath("url", is("http://localhost:8080/api/v1/locations/abc")));
    result.andExpect(jsonPath("reqMethod", is("DELETE")));
    result.andExpect(jsonPath("timestamp", Matchers.isA(BigDecimal.class)));
  }
}