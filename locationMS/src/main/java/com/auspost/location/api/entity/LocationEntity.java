package com.auspost.location.api.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author : github.com/deepakpal1982
 * @project : locationMS
 * @created : 14/07/2021, Tuesday
 **/

@Entity
@Table(name = "location")
public class LocationEntity {
    
  @Id
  @GeneratedValue
  @Column(name = "ID", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "SUBURBS")
  private String suburbs;

  @Column(name = "STATE")
  private String state;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "PINCODE")
  private String pincode;

  
  public UUID getId() {
    return id;
  }

  public LocationEntity setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getSuburbs() {
    return suburbs;
  }

  public LocationEntity setSuburbs(String suburbs) {
    this.suburbs = suburbs;
    return this;
  }

  public String getState() {
    return state;
  }

  public LocationEntity setState(String state) {
    this.state = state;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public LocationEntity setCountry(String country) {
    this.country = country;
    return this;
  }

  public String getPincode() {
    return pincode;
  }

  public LocationEntity setPincode(String pincode) {
    this.pincode = pincode;
    return this;
  }
}
