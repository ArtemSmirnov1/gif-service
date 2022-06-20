package com.exchanging_rate_service.controller;

import com.exchanging_rate_service.services.ExchangingRateService;
import com.exchanging_rate_service.services.GifService;
import java.time.LocalDate;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @Value("${currency-service.appId}")
  private String appId;

  @Value("${currency.name}")
  private String currencyName;

  @Value("${gif-service.api-key}")
  private String apiKey;

  @Value("${gif-service.rich-tag}")
  private String richTag;

  @Value("${gif-service.broke-tag}")
  private String brokeTag;

  @Value("${gif-service.search-key}")
  private String key;

  final ExchangingRateService exchangingRateService;
  final GifService gifService;

  public Controller(ExchangingRateService exchangingRateService, GifService gifService) {
    this.exchangingRateService = exchangingRateService;
    this.gifService = gifService;
  }

  @GetMapping("/get-the-gif")
  ResponseEntity<String> getGif() {
    LocalDate localDate = LocalDate.now().minusDays(1);
    Double yesterdayResult =
        Double.valueOf(
            exchangingRateService
                .getExchangingRateForYesterday(localDate + ".json", appId)
                .getRates()
                .get(currencyName));
    Double todayResult =
        Double.valueOf(
            exchangingRateService.getExchangingRateForToday(appId).getRates().get(currencyName));
    HashMap<String, String> gifResultMap;
    if (todayResult > yesterdayResult) {
      gifResultMap = gifService.getRandomGifByTag(apiKey, richTag).getData();
    } else
      gifResultMap = gifService.getRandomGifByTag(apiKey, brokeTag).getData();
    return new ResponseEntity<>(gifResultMap.get(key), HttpStatus.OK);
  }
}
