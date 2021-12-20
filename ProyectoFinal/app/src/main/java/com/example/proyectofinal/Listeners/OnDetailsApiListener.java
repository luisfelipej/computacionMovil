package com.example.proyectofinal.Listeners;

import com.example.proyectofinal.Models.DetailApiResponse;

public interface OnDetailsApiListener {
    void onResponse(DetailApiResponse response);
    void onError(String message);
}
