package com.example.proyectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.proyectofinal.Adapters.HomeRecyclerAdapter;
import com.example.proyectofinal.Adapters.SavedRecyclerAdapter;
import com.example.proyectofinal.Listeners.OnMovieClickListener;
import com.example.proyectofinal.Listeners.OnSearchApiListener;
import com.example.proyectofinal.Models.Movie;
import com.example.proyectofinal.Models.SearchApiResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {
    FirebaseAuth mAuth;
    SearchView searchView;
    RecyclerView recyclerViewHome;
    RecyclerView recyclerViewSaved;
    HomeRecyclerAdapter adapter;
    SavedRecyclerAdapter savedAdapter;
    RequestManager manager;
    ProgressDialog dialog;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        db = FirebaseFirestore.getInstance();

        searchView = findViewById(R.id.searchView);
        recyclerViewHome = findViewById(R.id.recyclerViewHome);
        recyclerViewSaved = findViewById(R.id.recyclerViewSaved);
        dialog = new ProgressDialog(this);
        manager = new RequestManager(this);
        showSavedMovies();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Buscando...");
                dialog.show();
                manager.searchMovies(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void showSavedMovies() {
        db.collection("movies").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    return;
                }

                List<Movie> movies = new ArrayList<>();
                for (QueryDocumentSnapshot document : value){
                    Movie movie = document.toObject(Movie.class);
                    Log.i("MOVIE", movie.getTitle());
                    movies.add(movie);
                }
                recyclerViewSaved.setHasFixedSize(true);
                recyclerViewSaved.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                savedAdapter = new SavedRecyclerAdapter(MainActivity.this, movies);
                recyclerViewSaved.setAdapter(savedAdapter);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionBarLogout) {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        }
    }

    private final OnSearchApiListener listener = new OnSearchApiListener() {
        @Override
        public void onResponse(SearchApiResponse response) {
            dialog.dismiss();
            if(response == null){
                Toast.makeText(MainActivity.this, "No se ha encontrado la película", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Ha ocurrido un error, reintente.", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResult(SearchApiResponse response) {
        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        adapter = new HomeRecyclerAdapter(this, response.getTitles(), this);
        recyclerViewHome.setAdapter(adapter);
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class).putExtra("data", id));
    }
}