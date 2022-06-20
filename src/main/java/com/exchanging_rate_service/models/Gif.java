package com.exchanging_rate_service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Gif extends RepresentationModel<Gif> {

  @JsonIgnoreProperties({"images", "user", "cta"})
  HashMap<String, String> data;

  @JsonCreator
  public Gif(@JsonProperty("data") HashMap<String, String> data) {
    this.data = data;
  }
}
