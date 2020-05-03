package com.multicast.udpstream.helper;

import com.multicast.udpstream.model.Channel;

import java.util.List;

public interface IChannelsDAO {

    boolean salvar(Channel channel);
    boolean atualizar(Channel channel);
    boolean deletar(Channel channel);
    List<Channel> listar();

}
