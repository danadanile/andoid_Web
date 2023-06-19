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
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            // Inflate the layout based on the message side
            Message message = messageList.get(position);
            int layoutId = message.getSender().getUsername().equals(MyApplication.getMyProfile()) ?
                    R.layout.activity_message_me : R.layout.activity_message_other;

            convertView = inflater.inflate(layoutId, parent, false);

            viewHolder = new ViewHolder();
            if (layoutId == R.layout.activity_message_me) {
                viewHolder.messageContent = convertView.findViewById(R.id.message_content_me);
            } else {
                viewHolder.messageContent = convertView.findViewById(R.id.message_content_other);
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = messageList.get(position);

        viewHolder.messageContent.setText(message.getContent());

        return convertView;
    }


}
