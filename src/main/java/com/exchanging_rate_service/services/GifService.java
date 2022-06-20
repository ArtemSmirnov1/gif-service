package com.exchanging_rate_service.services;

import com.exchanging_rate_service.models.Gif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gifs", url = "${gif-service.url}")
public interface GifService {
  @RequestMapping(method = RequestMethod.GET, value = "${gif-service.urn}")
  Gif getRandomGifByTag(@RequestParam("apiKey") String apiKey, @RequestParam("tag") String tag);
}
