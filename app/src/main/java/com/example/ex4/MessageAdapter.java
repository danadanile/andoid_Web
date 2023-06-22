package com.example.ex4;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ex4.schemas.Message;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private List<Message> messageList;
    private LayoutInflater inflater;

    public MessageAdapter(Context context, List<Message> messages) {
        this.messageList = messages;
        inflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView messageContent;
        TextView timeTextView;
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
            viewHolder = new ViewHolder();
            Message message = messageList.get(position);

            if (message.getSender().getUsername().equals(MyApplication.getMyProfile())) {
                convertView = inflater.inflate(R.layout.activity_message_me, parent, false);
                viewHolder.messageContent = convertView.findViewById(R.id.sendermessage);

            } else {
                convertView = inflater.inflate(R.layout.activity_message_other, parent, false);
                viewHolder.messageContent = convertView.findViewById(R.id.sendermessage);

            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = messageList.get(position);
        viewHolder.messageContent.setText(message.getContent());

        if (message.getSender().getUsername().equals(MyApplication.getMyProfile())) {
            viewHolder.messageContent.setGravity(Gravity.END);
        } else {
            viewHolder.messageContent.setGravity(Gravity.START);
        }

        return convertView;
    }
}
