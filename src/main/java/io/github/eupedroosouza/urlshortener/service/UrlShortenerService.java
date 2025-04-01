package io.github.eupedroosouza.urlshortener.service;

import io.github.eupedroosouza.urlshortener.config.ApplicationConfiguration;
import io.github.eupedroosouza.urlshortener.model.UrlShortener;
import io.github.eupedroosouza.urlshortener.repository.UrlShortenerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UrlShortenerService {

    private final UrlShortenerRepository urlShortenerRepository;
    private final ApplicationConfiguration applicationConfiguration;

    private final long expireTimeMillis;

    public UrlShortenerService(UrlShortenerRepository urlShortenerRepository, ApplicationConfiguration applicationConfiguration) {
        this.urlShortenerRepository = urlShortenerRepository;
        this.applicationConfiguration = applicationConfiguration;
        this.expireTimeMillis = applicationConfiguration.getTimeToExpire().toMillis();
    }

    public UrlShortener create(String shortenerUrl) {
        UrlShortener urlShortener = new UrlShortener();
        urlShortener.setUrl(shortenerUrl);
        urlShortener.setShortener(validatedRandomShortener());
        urlShortener.setCreated(System.currentTimeMillis());
        return urlShortenerRepository.save(urlShortener);
    }

    public Optional<UrlShortener> getByShortener(String shortener) {
        Optional<UrlShortener> urlShortenerOptional = urlShortenerRepository.findByShortener(shortener);
        if (urlShortenerOptional.isPresent()) {
            UrlShortener urlShortener = urlShortenerOptional.get();
            long expireAt = (urlShortener.getCreated() + expireTimeMillis);
            if ((expireAt - System.currentTimeMillis()) < 0) {
                urlShortenerRepository.deleteById(urlShortener.getId());
                return Optional.empty();
            }
        }
        return urlShortenerOptional;
    }

    private String validatedRandomShortener() {
        String generated;
        Optional<UrlShortener> urlShortenerByShortener;
        do {
            generated = randomShortener();
            urlShortenerByShortener = urlShortenerRepository.findByShortener(generated);
        } while (urlShortenerByShortener.isPresent());
        return generated;
    }

    private String randomShortener() {
        String RANDOM_SHORTENER_CHARS = "ABCDEFGHIJKLMNOPQRSTUV123456";
        StringBuilder shortener = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int numberOfChars = random.nextInt(5, 10);
        for (int i = 1; i <= numberOfChars; i++) {
            int randomizedChar = random.nextInt(0, RANDOM_SHORTENER_CHARS.length() - 1);
            shortener.append(RANDOM_SHORTENER_CHARS.charAt(randomizedChar));
        }
        return shortener.toString();
    }

    public String createUrl(UrlShortener urlShortener) {
        return applicationConfiguration.getBaseUrl() + urlShortener.getShortener();
    }


}
