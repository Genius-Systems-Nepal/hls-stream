package com.multicast.udpstream.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.multicast.udpstream.R;
import com.multicast.udpstream.adapter.ChannelsAdapter;
import com.multicast.udpstream.helper.ChannelsDAO;
import com.multicast.udpstream.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RecyclerView recyclerView;
    private List<Channel> listChannels = new ArrayList<>();
    private Channel channelSelected;
    private ChannelsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.recyclerView);
        loadChannels();

        button = findViewById(R.id.editChannel);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ListChannels.class);
            startActivity(intent);
        });
    }

    public void loadChannels(){

        //Busca do banco de dados os canais
        ChannelsDAO channelsDAO = new ChannelsDAO(getApplicationContext());
        listChannels = channelsDAO.listar();

        //Configura o adapter baseado no CardView
        adapter = new ChannelsAdapter(listChannels);

        //Faz a repetição do CardView
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    //---------------------Overrides------------------------------
    @Override
    protected void onStart() {
        loadChannels();
        super.onStart();
    }

}
