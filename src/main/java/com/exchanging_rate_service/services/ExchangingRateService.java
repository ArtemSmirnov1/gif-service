package com.exchanging_rate_service.services;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import com.exchanging_rate_service.models.ExchangingRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchanging-rates", url = "https://openexchangerates.org/api")
public interface ExchangingRateService {
  @RequestMapping(
      method = RequestMethod.GET,
      value = "/historical/{date}?app_id={appId}",
      consumes = APPLICATION_JSON_VALUE)
  ExchangingRate getExchangingRateForYesterday(
      @RequestParam("date") String date, @RequestParam("appId") String appId);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/latest.json?app_id={appId}",
      consumes = APPLICATION_JSON_VALUE)
  ExchangingRate getExchangingRateForToday(@RequestParam("appId") String appId);
}
