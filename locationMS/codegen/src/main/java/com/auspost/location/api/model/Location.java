package com.auspost.location.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javax.xml.bind.annotation.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Location
 */
@JacksonXmlRootElement(localName = "Location")
@XmlRootElement(name = "Location")
@XmlAccessorType(XmlAccessType.FIELD)
public class Location extends RepresentationModel<Location>  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  @JacksonXmlProperty(localName = "id")
  private String id;

  @JsonProperty("suburbs")
  @JacksonXmlProperty(localName = "suburbs")
  private String suburbs;

  @JsonProperty("state")
  @JacksonXmlProperty(localName = "state")
  private String state;

  @JsonProperty("country")
  @JacksonXmlProperty(localName = "country")
  private String country;

  @JsonProperty("pincode")
  @JacksonXmlProperty(localName = "pincode")
  private String pincode;

  public Location id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Location identifier
   * @return id
  */
  @ApiModelProperty(value = "Location identifier")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Location suburbs(String suburbs) {
    this.suburbs = suburbs;
    return this;
  }

  /**
   * suburbs name
   * @return suburbs
  */
  @ApiModelProperty(value = "suburbs name")


  public String getSuburbs() {
    return suburbs;
  }

  public void setSuburbs(String suburbs) {
    this.suburbs = suburbs;
  }

  public Location state(String state) {
    this.state = state;
    return this;
  }

  /**
   * state name
   * @return state
  */
  @ApiModelProperty(value = "state name")


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Location country(String country) {
    this.country = country;
    return this;
  }

  /**
   * country name
   * @return country
  */
  @ApiModelProperty(value = "country name")


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Location pincode(String pincode) {
    this.pincode = pincode;
    return this;
  }

  /**
   * postal code
   * @return pincode
  */
  @ApiModelProperty(value = "postal code")


  public String getPincode() {
    return pincode;
  }

  public void setPincode(String pincode) {
    this.pincode = pincode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(this.id, location.id) &&
        Objects.equals(this.suburbs, location.suburbs) &&
        Objects.equals(this.state, location.state) &&
        Objects.equals(this.country, location.country) &&
        Objects.equals(this.pincode, location.pincode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, suburbs, state, country, pincode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Location {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    suburbs: ").append(toIndentedString(suburbs)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

