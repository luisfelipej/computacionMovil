package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.proyectofinal.Adapters.CastRecyclerAdapter;
import com.example.proyectofinal.Listeners.OnDetailsApiListener;
import com.example.proyectofinal.Models.DetailApiResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

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
    DetailApiResponse currentMovie;
    Boolean hasSaved = false;
    private FirebaseFirestore db;
    private Menu menu;

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

        db = FirebaseFirestore.getInstance();
        manager = new RequestManager(this);
        String movie_id = getIntent().getStringExtra("data");
        dialog = new ProgressDialog(this);
        dialog.setTitle("Cargando...");
        dialog.show();
        manager.searchMovieDetails(listener, movie_id);
        isOnDb(movie_id);
    }

    private void isOnDb(String movieId) {
        CollectionReference moviesRef = db.collection("movies");
        Query query = moviesRef.whereEqualTo("movieId", movieId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            hasSaved = true;
                            DetailsActivity.this.menu.findItem(R.id.actionBarSave).setTitle("Guardado");
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Fail", e.getMessage());
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        this.menu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            hasSaved = false;
            finish();
            return true;
        }
        if (id == R.id.actionBarSave && !hasSaved) {
            Log.i("SAVING", "Guardando...");
            Map<String, Object> movie = new HashMap<>();
            movie.put("movieId", currentMovie.getId());
            movie.put("title", currentMovie.getTitle());
            movie.put("posterUrl", currentMovie.getPoster());
            movie.put("hasViewed", false);
            db.collection("movies").add(movie).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(DetailsActivity.this, "Pelicula guardada", Toast.LENGTH_SHORT).show();
                    item.setTitle("Guardado");
                    hasSaved = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DetailsActivity.this, "Ha ocurrido un error, reintete", Toast.LENGTH_SHORT).show();
                }
            });


            return true;
        }
        return super.onOptionsItemSelected(item);
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
        currentMovie = response;
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