package io.github.eupedroosouza.urlshortener.repository;

import io.github.eupedroosouza.urlshortener.model.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {

    Optional<UrlShortener> findByShortener(String shortener);

}
