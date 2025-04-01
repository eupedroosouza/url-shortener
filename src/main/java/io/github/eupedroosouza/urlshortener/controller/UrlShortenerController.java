package io.github.eupedroosouza.urlshortener.controller;

import io.github.eupedroosouza.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{shortener}")
    public ResponseEntity<Object> shortener(@PathVariable("shortener") String shortener) {
        return urlShortenerService.getByShortener(shortener)
                .map(it -> ResponseEntity
                        .status(HttpStatus.MOVED_PERMANENTLY)
                        .header("Location", it.getUrl())
                        .build())
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
