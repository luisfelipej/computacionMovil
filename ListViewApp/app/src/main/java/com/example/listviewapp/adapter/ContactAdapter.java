package com.example.listviewapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.listviewapp.R;
import com.example.listviewapp.models.Contact;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    Context context;
    List<Contact> contacts;
    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.contacts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contact contactToShow = contacts.get(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.contact_item, null);
        TextView firstNameContact = view.findViewById(R.id.tvFirstNameContact);
        TextView fullSurnameContact = view.findViewById(R.id.tvFullSurname);

        firstNameContact.setText(contactToShow.getFirstName());
        fullSurnameContact.setText(contactToShow.getSurName() + " " + contactToShow.getMaternalSurname());
        return view;
    }
}
