package com.xiao.socket.test;

import com.xiao.socket.Client;
import com.xiao.socket.Server;

import java.io.IOException;

public class TestServer {

    public static void main( String[] args ) {

        Server server = new Server();

        try {
            server.socketServer();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
