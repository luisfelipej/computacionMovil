package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.proyectofinal.Adapters.CastRecyclerAdapter;
import com.example.proyectofinal.Listeners.OnDetailsApiListener;
import com.example.proyectofinal.Models.DetailApiResponse;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView textViewMovieName;
    TextView textViewMovieReleased;
    TextView textViewMovieRuntime;
    TextView textViewMovieRating;
    TextView textViewMovieVotes;
    TextView textViewMoviePlot;
    ImageView imageViewMoviePoster;
    RecyclerView recyclerMovieCast;
    CastRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textViewMoviePlot = findViewById(R.id.textViewMoviePlot);
        textViewMovieName = findViewById(R.id.textViewMovieName);
        textViewMovieReleased = findViewById(R.id.textViewMovieReleased);
        textViewMovieRuntime = findViewById(R.id.textViewMovieRuntime);
        textViewMovieRating = findViewById(R.id.textViewMovieRating);
        textViewMovieVotes = findViewById(R.id.textViewMovieVotes);
        imageViewMoviePoster = findViewById(R.id.imageViewMoviePoster);
        recyclerMovieCast = findViewById(R.id.recyclerMovieCast);

        manager = new RequestManager(this);
        String movie_id = getIntent().getStringExtra("data");
        dialog = new ProgressDialog(this);
        dialog.setTitle("Cargando...");
        dialog.show();
        manager.searchMovieDetails(listener, movie_id);

    }

    private OnDetailsApiListener listener = new OnDetailsApiListener() {
        @Override
        public void onResponse(DetailApiResponse response) {
            dialog.dismiss();
            if(response.equals(null)) {
                Toast.makeText(DetailsActivity.this, "No hay detalles de la pelicula.", Toast.LENGTH_SHORT).show();
                return;
            }
            showResults(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailsActivity.this, "Ha ocurrido un error, reintente.", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResults(DetailApiResponse response) {
        textViewMovieName.setText(response.getTitle());
        textViewMovieReleased.setText("Año de lanzamiento: " + response.getYear());
        textViewMovieRuntime.setText("Duración: " + response.getLength());
        textViewMovieRating.setText("Calificación: " + response.getRating());
        textViewMovieVotes.setText(response.getRating_votes() + " votos");
        textViewMoviePlot.setText(response.getPlot());
        try {
            Picasso.get().load(response.getPoster()).into(imageViewMoviePoster);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        recyclerMovieCast.setHasFixedSize(true);
        recyclerMovieCast.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CastRecyclerAdapter(this, response.getCast());
        recyclerMovieCast.setAdapter(adapter);


    }
}