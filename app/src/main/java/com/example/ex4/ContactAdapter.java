package com.example.ex4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ex4.schemas.Contact;
import com.squareup.picasso.Picasso;

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
        String imageUrl = contact.getUser().getProfilePic();
        Picasso.get().load(imageUrl).into(viewHolder.image);
        viewHolder.displayName.setText(contact.getUser().getDisplayName());
        viewHolder.date.setText(contact.getLastMessage().getCreated());


//        viewHolder.img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), "Clicked on image", Toast.LENGTH_LONG).show();
//            }
//        });

        return convertView;
    }

}
