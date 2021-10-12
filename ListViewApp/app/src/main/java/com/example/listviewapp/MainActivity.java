package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewapp.adapter.ContactAdapter;
import com.example.listviewapp.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lvContacts;
    List<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Agenda");
        lvContacts = findViewById(R.id.lvContacts);
        contacts = new ArrayList<>();

        Contact firstContact = new Contact("Luis", "Jaña", "Gutierrez", "+56968069299");
        Contact secondContact = new Contact("Alvaro", "Jaña", "Gutiérrez", "+56968069299");
        contacts.add(firstContact);
        contacts.add(secondContact);

        ArrayAdapter<Contact> adapter = new ContactAdapter(this, R.layout.contact_item, contacts);

        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Contact selectedContact = contacts.get(i);
        String firstName = selectedContact.getFirstName();
        Log.i("MainActivity", "Nombre: " + firstName);
        Toast.makeText(this, "Click en item" + i, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, ContactDetail.class);
        intent.putExtra("contact", selectedContact);
        startActivity(intent);
    }
}