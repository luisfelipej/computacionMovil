package com.example.listviewapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.listviewapp.models.Contact;

public class ContactDetail extends AppCompatActivity {
    TextView tvFirstName, tvSurname, tvMaternalSurname, tvPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Intent intent = getIntent();
        Contact selectedContact = (Contact) intent.getSerializableExtra("contact");
        setTitle("Detalle " + selectedContact.getFirstName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvSurname = findViewById(R.id.tvSurname);
        tvMaternalSurname = findViewById(R.id.tvMaternalSurname);
        tvPhone = findViewById(R.id.tvPhone);

        tvFirstName.setText(selectedContact.getFirstName());
        tvSurname.setText(selectedContact.getSurName());
        tvMaternalSurname.setText(selectedContact.getMaternalSurname());
        tvPhone.setText(selectedContact.getPhone());

        String firstName = intent.getStringExtra("firstName");
        Log.i("DetailContact", "Recieved Name: " + firstName);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}