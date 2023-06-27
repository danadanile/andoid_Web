package com.example.ex4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex4.pages.ChatPage;
import com.example.ex4.schemas.Contact;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private final List<Contact> contactList;
    private final int selectedColor;

    public ContactAdapter(List<Contact> contacts, int color) {
        this.contactList = contacts;
        this.selectedColor = color;
    }

    private static class ViewHolder {
        ImageView image;
        TextView displayName;
        TextView date;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_contact, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = convertView.findViewById(R.id.contact_profile_img);
            viewHolder.displayName = convertView.findViewById(R.id.contact_name);
            viewHolder.date = convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        }

        Contact contact = contactList.get(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        String base64String = contact.getUser().getProfilePic();

        // Remove the prefix and line breaks from the base64 string
        base64String = base64String.replace("data:image/png;base64,", "")
                .replaceAll("\\s", "");

        // Convert the base64 string to a byte array
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);

        // Create a Bitmap from the byte array
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        // Set the Bitmap as the image source of the ImageView
        viewHolder.image.setImageBitmap(decodedBitmap);

        viewHolder.displayName.setText(contact.getUser().getDisplayName());

        if (contact.getLastMessage() != null) {
            viewHolder.date.setText(contact.getLastMessage().getCreated());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat =
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat =
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            try {
                Date date = inputFormat.parse(contact.getLastMessage().getCreated());
                if (date != null) {
                    String formattedDate = outputFormat.format(date);
                    viewHolder.date.setText(contact.getLastMessage().getCreated());
                    viewHolder.date.setText(formattedDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // If there is no last massage we put the default current date.
        } else {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            // Format the date and time using SimpleDateFormat
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);
            viewHolder.date.setText(formattedDate);
        }

        // Set a click listener on the convertView (list item)
        convertView.setOnClickListener(v -> {
            // Perform the desired action when the contact is clicked
            // For example, you can start a new activity and pass the contact information
            Intent intent = new Intent(v.getContext(), ChatPage.class);
            intent.putExtra("selectedColor", selectedColor);
            Log.d("v", "Selected Color: " + selectedColor);

            Gson gson = new Gson();
            String contactJson = gson.toJson(contact);
            // Pass the contact object to the details activity
            intent.putExtra("contact", contactJson);
            v.getContext().startActivity(intent);
        });

        return convertView;
    }
}
