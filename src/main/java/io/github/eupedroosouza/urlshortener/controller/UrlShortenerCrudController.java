package io.github.eupedroosouza.urlshortener.controller;

import io.github.eupedroosouza.urlshortener.dto.CreateShortenerDTO;
import io.github.eupedroosouza.urlshortener.dto.CreatedShortenerDTO;
import io.github.eupedroosouza.urlshortener.model.UrlShortener;
import io.github.eupedroosouza.urlshortener.service.UrlShortenerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shorten-url")
public class UrlShortenerCrudController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerCrudController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<CreatedShortenerDTO> createShortener(@Validated @RequestBody CreateShortenerDTO createdShortenerDTO) {
        UrlShortener urlShortener = urlShortenerService.create(createdShortenerDTO.url());
        return ResponseEntity.ok(new CreatedShortenerDTO(urlShortenerService.createUrl(urlShortener)));
    }


}
