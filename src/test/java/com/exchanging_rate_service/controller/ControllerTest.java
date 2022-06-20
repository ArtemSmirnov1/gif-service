package com.exchanging_rate_service.controller;

import static org.mockito.ArgumentMatchers.eq;

import com.exchanging_rate_service.models.ExchangingRate;
import com.exchanging_rate_service.models.Gif;
import com.exchanging_rate_service.services.ExchangingRateService;
import com.exchanging_rate_service.services.GifService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@TestPropertySource("classpath:service.properties")
@SpringJUnitConfig({Controller.class})
class ControllerTest {

  @Autowired private Controller controller;

  @MockBean private ExchangingRateService exchangingRateService;

  @MockBean private GifService gifService;

  private String richTag, brokeTag;
  private ExchangingRate higherRate, lowerRate;
  private Gif gif;
  private String yesterdaysDatePlusExtension;
  private final HashMap<String, String> fieldForGif = new HashMap<>();
  private final HashMap<String, String> fieldForHigherRate = new HashMap<>();
  private final HashMap<String, String> fieldForLowerRate = new HashMap<>();

  @BeforeEach
  void setUp() {
    yesterdaysDatePlusExtension = LocalDate.now().minusDays(1) + ".json";
    richTag = "rich";
    brokeTag = "broke";
    gif = new Gif(fieldForGif);
    fieldForGif.put("url", "http://giphy.com");
    fieldForGif.put("redundantField", "redundantValue");
    Mockito.doReturn(gif).when(gifService).getRandomGifByTag(Mockito.anyString(), Mockito.anyString());
    fieldForHigherRate.put("AED", "53"); // false rate, just for the check
    fieldForHigherRate.put("RUB", "57.53");
    higherRate = new ExchangingRate(fieldForHigherRate);
    fieldForLowerRate.put("AED", "53");
    fieldForLowerRate.put("RUB", "55.16");
    lowerRate = new ExchangingRate(fieldForLowerRate);
  }

  @Test
  void getRichGifOK() {
    Mockito.doReturn(higherRate)
        .when(exchangingRateService)
        .getExchangingRateForToday(Mockito.anyString());
    Mockito.doReturn(lowerRate)
        .when(exchangingRateService)
        .getExchangingRateForYesterday(eq(yesterdaysDatePlusExtension), Mockito.anyString());
    ResponseEntity<String> response = controller.getGif();
    Mockito.verify(exchangingRateService, Mockito.times(1))
        .getExchangingRateForToday(Mockito.anyString());
    Mockito.verify(exchangingRateService, Mockito.times(1))
        .getExchangingRateForYesterday(eq(yesterdaysDatePlusExtension), Mockito.anyString());
    Mockito.verify(gifService, Mockito.times(0))
        .getRandomGifByTag(Mockito.anyString(), eq(brokeTag));
    Mockito.verify(gifService, Mockito.times(1))
        .getRandomGifByTag(Mockito.anyString(), eq(richTag));
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("giphy"));
  }

  @Test
  void getBrokeGifOK() {
    Mockito.doReturn(lowerRate)
        .when(exchangingRateService)
        .getExchangingRateForToday(Mockito.anyString());
    Mockito.doReturn(higherRate)
        .when(exchangingRateService)
        .getExchangingRateForYesterday(eq(yesterdaysDatePlusExtension), Mockito.anyString());
    ResponseEntity<String> response = controller.getGif();
    Mockito.verify(exchangingRateService, Mockito.times(1))
        .getExchangingRateForToday(Mockito.anyString());
    Mockito.verify(exchangingRateService, Mockito.times(1))
        .getExchangingRateForYesterday(eq(yesterdaysDatePlusExtension), Mockito.anyString());
    Mockito.verify(gifService, Mockito.times(1))
        .getRandomGifByTag(Mockito.anyString(), eq(brokeTag));
    Mockito.verify(gifService, Mockito.times(0))
        .getRandomGifByTag(Mockito.anyString(), eq(richTag));
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("giphy"));
  }
}
