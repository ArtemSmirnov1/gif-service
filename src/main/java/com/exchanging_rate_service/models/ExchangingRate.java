package com.exchanging_rate_service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
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
public class ExchangingRate extends RepresentationModel<ExchangingRate> {

  private HashMap<String, String> rates;

  @JsonCreator
  public ExchangingRate(@JsonProperty("rates") HashMap<String, String> rates) {
    this.rates = rates;
  }
}
