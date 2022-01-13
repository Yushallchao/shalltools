package com.xiao.webservice;

import javax.xml.ws.Endpoint;

public class PublishWebService {
    /**
     * @description 发布ws
     * @author Yxc
     * @date 2021/11/22 10:29
     * @param args
     * @return void
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Endpoint.publish("http://localhost:8080/test/calc", new CreateWebService());
    }
}
