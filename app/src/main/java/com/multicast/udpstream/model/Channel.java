package com.multicast.udpstream.model;

import java.io.Serializable;

public class Channel implements Serializable {

    private Long id ;
    private String name;
    private String ip;

    public Channel(String name, String ip){
        this.name = name;
        this.ip = ip;
    }
    public Channel(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
