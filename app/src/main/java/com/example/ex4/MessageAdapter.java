package com.example.ex4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ex4.schemas.Message;

import java.util.List;
public class MessageAdapter  extends BaseAdapter {
    private List<Message> messageList;

    public MessageAdapter(List<Message> messages) {
        this.messageList = messages;
    }

    private static class ViewHolder {
        TextView messageContent;
        TextView messageSide;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.messageContent = convertView.findViewById(R.id.message_content);
            viewHolder.messageSide = convertView.findViewById(R.id.message_side);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = messageList.get(position);

        for (Message message : messages) {

            viewHolder.messageContent.setText(message.getContent());

            // Determine the side of the message (0 for user, 1 for the other side)
            int side = message.getSender().equals(MyApplication.getMyProfile()) ? 0 : 1;
            viewHolder.messageSide.setText(String.valueOf(side));

            // Set different background colors based on the side of the message
            if (side == 0) {
                viewHolder.messageContent.setBackgroundResource(R.color.grey);
            } else {
                viewHolder.messageContent.setBackgroundResource(R.color.default_color);
            }
        }
        return convertView;
    }
}
