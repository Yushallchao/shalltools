package com.xiao.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://ewell.cc?wsdl")
public interface OriginWebService {
    @WebMethod
    public int sum(int add1, int add2);
    @WebMethod
    public int multiply(int mul1, int mul2);
    @WebMethod
    public String print(String request);
}