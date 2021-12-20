package com.example.proyectofinal.Listeners;

import com.example.proyectofinal.Models.SearchApiResponse;

public interface OnSearchApiListener {
    void onResponse(SearchApiResponse response);
    void onError(String message);
}
