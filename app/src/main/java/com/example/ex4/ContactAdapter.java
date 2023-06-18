package com.example.ex4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ex4.pages.ChatPage;
import com.example.ex4.schemas.Contact;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contacts) {
        this.contactList = contacts;
    }

    private class ViewHolder {
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

    private boolean isBase64Image(String imageString) {
        return imageString != null && imageString.startsWith("data:image/");
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
        // Check if the profile picture is in Base64 format
//        if (isBase64Image(contact.getUser().getProfilePic())) {
//            // Decode the Base64 string to a byte array
//            byte[] decodedBytes = Base64.decode(contact.getUser().getProfilePic(), Base64.DEFAULT);
//
//            // Create a Bitmap from the byte array
//            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
//
//            // Convert the Bitmap to a Drawable
//            Drawable decodedDrawable = new BitmapDrawable(convertView.getResources(), decodedBitmap);
//
//            // Set the decoded Drawable as the image source
//            viewHolder.image.setImageDrawable(decodedDrawable);
//        } else {
//            // The profile picture is not in Base64 format, load it normally
//            String imageUrl = contact.getUser().getProfilePic();
//            Picasso.get().load(imageUrl).into(viewHolder.image);
//        }
        String imageUrl = contact.getUser().getProfilePic();
        Picasso.get().load(imageUrl).into(viewHolder.image);
        viewHolder.displayName.setText(contact.getUser().getDisplayName());

        if (contact.getLastMessage() != null) {
            viewHolder.date.setText(contact.getLastMessage().getCreated());
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date date = inputFormat.parse(contact.getLastMessage().getCreated());
                String formattedDate = outputFormat.format(date);
                viewHolder.date.setText(contact.getLastMessage().getCreated());
                viewHolder.date.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // If there is no last massage we put the default current date.
        } else {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();

            // Format the date and time using SimpleDateFormat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(currentDate);
            viewHolder.date.setText(formattedDate);
        }

//        viewHolder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Clicked on image", Toast.LENGTH_LONG).show();
//            }
//        });

        // Set a click listener on the convertView (list item)
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the desired action when the contact is clicked
                // For example, you can start a new activity and pass the contact information
                Intent intent = new Intent(v.getContext(), ChatPage.class);
                Gson gson = new Gson();
                String contactJson = gson.toJson(contact);
                intent.putExtra("contact", contactJson); // Pass the contact object to the details activity
                v.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

}
