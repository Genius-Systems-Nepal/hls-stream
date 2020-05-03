package com.multicast.udpstream.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.multicast.udpstream.R;
import com.multicast.udpstream.helper.ChannelsDAO;
import com.multicast.udpstream.model.Channel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditChannels extends AppCompatActivity {

    private EditText textEditName;
    private EditText textEditIp;
    private EditText textEditPort;
    private Channel channelAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_channels);

        Button saveEdit = findViewById(R.id.save);
        Button cancelEdit = findViewById(R.id.cancel);
        textEditName = findViewById(R.id.name);
        textEditIp = findViewById(R.id.ip);
        textEditPort = findViewById(R.id.port);

        channelAtual = (Channel) getIntent().getSerializableExtra("channelSelected");

        if(channelAtual != null){

            String ipPort = channelAtual.getIp();
            String[] split = ipPort.split(":");
            String ip = split[0];
            String port = split[1];

            textEditPort.setText(port);
            textEditIp.setText(ip);
            textEditName.setText(channelAtual.getName());
        }

        ChannelsDAO channelsDAO = new ChannelsDAO(getApplicationContext());

        saveEdit.setOnClickListener(v ->{

            String regexIp = "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
            String regexPort = "(\\d|\\d\\d|\\d\\d\\d|\\d\\d\\d\\d|[0-6][0-5][0-5][0-3][0-5])";

            Pattern patternIp = Pattern.compile(regexIp);
            Matcher matcherIp = patternIp.matcher(textEditIp.getText().toString());

            Pattern patternPort = Pattern.compile(regexPort);
            Matcher matchePort = patternPort.matcher(textEditPort.getText().toString());

            if(matcherIp.matches() && matchePort.matches()){
                if (channelAtual != null) {//Edit

                    Channel channel = new Channel();
                    channel.setName(textEditName.getText().toString());
                    channel.setIp(textEditIp.getText().toString() + ":" + textEditPort.getText().toString());
                    channel.setId(channelAtual.getId());

                    //atualizar no banco de dados
                    if (channelsDAO.atualizar(channel)) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Sucesso ao atualizar canal!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao atualizar canal!", Toast.LENGTH_SHORT).show();
                    }
                } else {// Save


                    Channel channel = new Channel();
                    channel.setIp(textEditIp.getText().toString() + ":" + textEditPort.getText().toString());
                    channel.setName(textEditName.getText().toString());
                    if (!channel.getName().isEmpty()) {
                        if (channelsDAO.salvar(channel)) {
                            finish();
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar canal!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar canal!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }else {
                if (matcherIp.matches()) {
                    textEditPort.setError("Porta inválida!");
                } else {
                    textEditIp.setError("Ip inválido!");
                }
            }
        });

        cancelEdit.setOnClickListener(v -> finish());

    }
}
