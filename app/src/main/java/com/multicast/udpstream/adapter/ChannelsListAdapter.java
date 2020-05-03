package com.multicast.udpstream.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.multicast.udpstream.R;
import com.multicast.udpstream.activity.EditChannels;
import com.multicast.udpstream.helper.ChannelsDAO;
import com.multicast.udpstream.model.Channel;

import java.util.List;

public class ChannelsListAdapter extends RecyclerView.Adapter<ChannelsListAdapter.MyViewHolder> {

    private List<Channel> listChannels;


    public ChannelsListAdapter(List<Channel> p){
        this.listChannels = p;
    }


    @NonNull
    @Override
    public ChannelsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_list,parent,false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Channel channel = listChannels.get(position);
        holder.name.setText(channel.getName());
        holder.ip.setText(channel.getIp());

        Context context = holder.itemView.getContext();

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditChannels.class);
            intent.putExtra("channelSelected", channel);

            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("Deseja realmente excluir "+channel.getName()+"?");

            alertDialogBuilder.setPositiveButton("Sim",
                    (arg0, arg1) -> {
                        ChannelsDAO channelsDAO = new ChannelsDAO(context);
                        if ( channelsDAO.deletar(channel) ){
                            Toast.makeText(context,
                                    "Canal Excluido com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context,
                                    "Erro ao excluir tarefa!",
                                    Toast.LENGTH_SHORT).show();
                    }

            });

            alertDialogBuilder.setNegativeButton("Não", (dialog, which) -> Log.e("You clicked NO",""));

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        });



    }

    @Override
    public int getItemCount() {
        return listChannels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView ip;
        private Button editButton;
        private Button deleteButton;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            ip = itemView.findViewById(R.id.editListIp);
            name = itemView.findViewById(R.id.editList);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.delete);

        }

    }
}
