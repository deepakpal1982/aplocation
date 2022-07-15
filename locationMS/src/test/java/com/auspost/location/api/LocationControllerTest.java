package com.auspost.location.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.auspost.location.api.controller.LocationController;
import com.auspost.location.api.entity.LocationEntity;
import com.auspost.location.api.exception.RestApiErrorHandler;
import com.auspost.location.api.hateoas.LocationRepresentationModelAssembler;
import com.auspost.location.api.model.AddLocationReq;
import com.auspost.location.api.model.Location;
import com.auspost.location.api.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author : github.com/deepakpal1982
 * @project : locationMS
 * @created : 14/07/2021, Tuesday
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
    private JacksonTester<List<Location>> LocationJackson;
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
        ObjectMapper mapper = new AppConfig().objectMapper();
        JacksonTester.initFields(this, mapper);
        MappingJackson2HttpMessageConverter mappingConverter = new MappingJackson2HttpMessageConverter();
        mappingConverter.setObjectMapper(mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestApiErrorHandler(msgSource))
                .setMessageConverters(mappingConverter)
                .build();
    }

    private void verifyJson(final ResultActions result) throws Exception {
        final String BASE_PATH = "http://localhost";
        result
                .andExpect(jsonPath("id", is(entity.getId().toString())))

                .andExpect(jsonPath("suburbs", is(entity.getSuburbs())))
                .andExpect(jsonPath("state", is(entity.getState())))
                .andExpect(jsonPath("country", is(entity.getCountry())))
                .andExpect(jsonPath("pincode", is(entity.getPincode())));
    }

    @Test
    @DisplayName("returns Location by given existing ID")
    public void getLocationByOrderIdWhenExists() throws Exception {
        given(service.getLocationById(id)).willReturn(Optional.of(entity));

        // when
        ResultActions result = mockMvc.perform(
                get(URI + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        verifyJson(result);
    }

    @Test
    @DisplayName("returns NOT FOUND response when non-existing ID is used for fetching address")
    public void getLocatinByOrderIdWhenNotExists() throws Exception {
        given(service.getLocationById("a1b9b31d-e73c-4112-af7c-b68530f38199"))
                .willReturn(Optional.empty());

        // when
        MockHttpServletResponse response = mockMvc.perform(
                get("/api/v1/locations/a1b9b31d-e73c-4112-af7c-b68530f38199")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    @DisplayName("returns newly created location with CREATED status")
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

  

}