package io.github.eupedroosouza.urlshortener.dto;


import jakarta.validation.constraints.Pattern;

public record CreateShortenerDTO(@Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL") String url) {


}
