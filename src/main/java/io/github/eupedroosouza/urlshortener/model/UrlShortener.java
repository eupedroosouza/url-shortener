package io.github.eupedroosouza.urlshortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UrlShortener {

    @Id
    private long id;
    private String url;
    private String shortener;
    private long created;

}
