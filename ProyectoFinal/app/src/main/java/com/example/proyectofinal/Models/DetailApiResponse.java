package com.example.proyectofinal.Models;

import java.util.List;

public class DetailApiResponse {
    String id = "";
    String title = "";
    String year = "";
    String length = "";
    String rating = "";
    String rating_votes = "";
    String poster = "";
    String plot = "";
    Trailer trailer;
    List<Cast> cast;
    List<List<String>> technical_specs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating_votes() {
        return rating_votes;
    }

    public void setRating_votes(String rating_votes) {
        this.rating_votes = rating_votes;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<List<String>> getTechnical_specs() {
        return technical_specs;
    }

    public void setTechnical_specs(List<List<String>> technical_specs) {
        this.technical_specs = technical_specs;
    }
}
