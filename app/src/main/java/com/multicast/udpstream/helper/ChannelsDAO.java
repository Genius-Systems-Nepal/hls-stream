package com.multicast.udpstream.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.multicast.udpstream.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsDAO implements IChannelsDAO {

    private SQLiteDatabase read;
    private SQLiteDatabase write;

    public ChannelsDAO(Context context){
        DbHelper db = new DbHelper( context );
        write = db.getWritableDatabase();
        read  = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Channel channel) {

        ContentValues cv = new ContentValues();

        cv.put("ip", channel.getIp());
        cv.put("name", channel.getName());

        try {
            write.insert(DbHelper.CHANNELS_TABLE, null, cv );
            Log.i("INFO ", "Canal salvo com sucesso!");
        }catch (Exception e){
            Log.e("INFO ", "Erro ao salvar canal " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Channel channel) {

        ContentValues cv = new ContentValues();
        cv.put("name", channel.getName());
        cv.put("ip", channel.getIp());

        try {
            String[] args = {channel.getId().toString()};
            write.update(DbHelper.CHANNELS_TABLE, cv, "id=?", args );
            Log.i("INFO ", "Canal atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO ", "Erro ao atualizada canal " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Channel channel) {

        try {
            String[] args = {channel.getId().toString()};
            write.delete(DbHelper.CHANNELS_TABLE,"id=?", args );
            Log.i("INFO", "Canal atualizada com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao atualizada Canal " + e.getMessage() );
            return false;
        }

        return true;
    }

    @Override
    public List<Channel> listar() {

        List<Channel> channels = new ArrayList<>();

        String sql = "SELECT * FROM "+DbHelper.CHANNELS_TABLE+" ;";
        Cursor c = read.rawQuery(sql, null);

       // c.moveToFirst();
        while (c.moveToNext()){

            Channel channel = new Channel();

            channel.setId(
                    c.getLong(c.getColumnIndex("ID"))
            );

            channel.setName(
                    c.getString(c.getColumnIndex("name"))
            );

            channel.setIp(
                    c.getString(c.getColumnIndex("ip"))
            );

            channels.add(channel);
        }

        c.close();
        return channels;
    }
}
