package com.example.proyectofinal.Models;

import java.util.List;

public class SearchApiResponse {
    List<SearchArrayObject> titles = null;
    List<SearchArrayObject> names = null;
    List<SearchArrayObject> companies = null;

    public List<SearchArrayObject> getTitles() {
        return titles;
    }

    public void setTitles(List<SearchArrayObject> titles) {
        this.titles = titles;
    }

    public List<SearchArrayObject> getNames() {
        return names;
    }

    public void setNames(List<SearchArrayObject> names) {
        this.names = names;
    }

    public List<SearchArrayObject> getCompanies() {
        return companies;
    }

    public void setCompanies(List<SearchArrayObject> companies) {
        this.companies = companies;
    }
}
