package com.auspost.location.api.model;

import java.util.Objects;
import com.auspost.location.api.model.AddLocationReqAllOf;
import com.auspost.location.api.model.Location;
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
 * AddLocationReq
 */
@JacksonXmlRootElement(localName = "AddLocationReq")
@XmlRootElement(name = "AddLocationReq")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddLocationReq extends RepresentationModel<AddLocationReq>  implements Serializable {
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

  @JsonProperty("userId")
  @JacksonXmlProperty(localName = "userId")
  private String userId;

  public AddLocationReq id(String id) {
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

  public AddLocationReq suburbs(String suburbs) {
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

  public AddLocationReq state(String state) {
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

  public AddLocationReq country(String country) {
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

  public AddLocationReq pincode(String pincode) {
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

  public AddLocationReq userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  @ApiModelProperty(value = "")


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddLocationReq addLocationReq = (AddLocationReq) o;
    return Objects.equals(this.id, addLocationReq.id) &&
        Objects.equals(this.suburbs, addLocationReq.suburbs) &&
        Objects.equals(this.state, addLocationReq.state) &&
        Objects.equals(this.country, addLocationReq.country) &&
        Objects.equals(this.pincode, addLocationReq.pincode) &&
        Objects.equals(this.userId, addLocationReq.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, suburbs, state, country, pincode, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddLocationReq {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    suburbs: ").append(toIndentedString(suburbs)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    pincode: ").append(toIndentedString(pincode)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

