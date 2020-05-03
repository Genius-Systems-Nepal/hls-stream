package com.multicast.udpstream.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.multicast.udpstream.R;
import com.multicast.udpstream.activity.Player;
import com.multicast.udpstream.model.Channel;

import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.MyViewHolder> {

    private List<Channel> listChannels;


    public ChannelsAdapter(List<Channel> p){
        this.listChannels = p;
    }


    @NonNull
    @Override
    public ChannelsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels,parent,false);

        return new MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ChannelsAdapter.MyViewHolder holder, int position) {

        Channel channel = listChannels.get(position);
        holder.name.setText(channel.getName());
        holder.ip.setText(channel.getIp());

        Context context = holder.itemView.getContext();

        holder.focus.setOnClickListener (v -> {
            Intent intent = new Intent(context, Player.class);
            intent.putExtra("ip", channel.getIp());
            context.startActivity(intent);

            Toast.makeText(holder.itemView.getContext(), channel.getName(), Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return listChannels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView ip;
        private CardView focus;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            focus = itemView.findViewById(R.id.focusTeste);
            name = itemView.findViewById(R.id.textName);
            ip = itemView.findViewById(R.id.textIp);

        }

    }
}
