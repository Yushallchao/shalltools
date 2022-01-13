package com.xiao.webservice;

import javax.jws.WebService;

@WebService(
        portName = "OriginPort",
        serviceName = "OriginService",
        targetNamespace = "http://ewell.cc?wsdl",
        endpointInterface = "com.xiao.webservice.CreateWebService")

public class CreateWebService implements OriginWebService {

    @Override
    public int sum(int add1, int add2) {
        // TODO Auto-generated method stub
        return add1 + add2;
    }

    @Override
    public int multiply(int mul1, int mul2) {
        // TODO Auto-generated method stub
        return mul1 * mul2;
    }

    @Override
    public String print(String request) {
        // TODO Auto-generated method stub
        return "返回"+request;
    }

}

