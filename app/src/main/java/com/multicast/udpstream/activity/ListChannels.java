package com.multicast.udpstream.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.multicast.udpstream.R;
import com.multicast.udpstream.adapter.ChannelsListAdapter;
import com.multicast.udpstream.helper.ChannelsDAO;
import com.multicast.udpstream.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ListChannels extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private List<Channel> listChannels = new ArrayList<>();
    private Channel channelSelected;
    private ChannelsListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_channels);

        button = findViewById(R.id.addChannel);
        recyclerView = findViewById(R.id.recyclerViewEdit);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditChannels.class);
            startActivity(intent);
        });
    }


    public void loadChannels(){

        //Busca do banco de dados os canais
        ChannelsDAO channelsDAO = new ChannelsDAO(getApplicationContext());
        listChannels = channelsDAO.listar();
        Log.i("INFO ","teste");

        //Configura o adapter baseado no CardView
        adapter = new ChannelsListAdapter(listChannels);

        for (Channel c : listChannels){
            Log.i("INFO ", c.getName());
        }



        //Faz a repetição do CardView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    //---------------------Overrides------------------------------
    @Override
    protected void onStart() {
        loadChannels();
        super.onStart();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        loadChannels();
        super.onWindowFocusChanged(hasFocus);
    }
}
