package com.xiao.socket.test;

import com.xiao.socket.Client;
import com.xiao.socket.Server;

import java.io.IOException;

public class TestClient {
    public static void main( String[] args ) {

        Client client = new Client();

        try {

            client.socketClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
