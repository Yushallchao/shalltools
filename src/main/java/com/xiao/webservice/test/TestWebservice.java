package com.xiao.webservice.test;

//AXIS4WS
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestWebservice {

    /*
     * @description 不推荐，前端经常使用
     * @author Yxc
     * @date 2021/11/22 10:41
     * @param
     * @return void
     */
    public static void AXIS4WS() throws Exception {
    }

    public static String param() {
        String param = null;
        return param;
    }

    /**
     * @description http方式调用ws
     * @author Yxc
     * @date 2021/11/22 10:41
     * @param
     * @return void
     */
    public static void HTTP4WS() throws Exception {

        String wsdl = "http://172.30.12.222:8088/CallWS.asmx?wsdl";

        String request =
                "<PARAMS>"+
                        "<ROOM_IP>172.30.22.228</ROOM_IP>"+
                        "<DOC_CODE>102716</DOC_CODE>"+
                        "<YARD>三香</YARD>"+
                        "</PARAMS>";

        String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">"+
                "<soapenv:Header/>"+
                "<soapenv:Body>"+
                "<tem:LogIn>"+

                "<tem:InputXML><![CDATA["+request+"]]></tem:InputXML>"+
                " </tem:LogIn>"+
                "</soapenv:Body>"+
                "</soapenv:Envelope>";

        PostMethod post = new PostMethod(wsdl);
        // 然后把Soap请求数据添加到PostMethod中
        byte[] b = soap.getBytes("utf-8");
        InputStream is = new ByteArrayInputStream(b, 0, b.length);
        /*	text/xml 这是基于soap1.1协议.
			application/soap+xml 这是基于soap1.2协议.
        */
        RequestEntity re = new InputStreamRequestEntity(is, b.length,
                "text/xml; charset=utf-8");
        post.setRequestEntity(re);
        //post.addRequestHeader("SOAPAction", "Apache-HttpClient/4.1.1(java 1.5)");
        //post.setRequestHeader("Accept", "text/plain;charset=utf-8");

        // 最后生成一个HttpClient对象，并发出postMethod请求
        HttpClient client = new HttpClient();
        int statusCode = client.executeMethod(post);
        if(statusCode == 200) {
            String response = post.getResponseBodyAsString();
            System.out.println(response);
        }
        else {
            System.out.println("调用失败！错误码：" + statusCode);
        }
    }

   /**
    * @description soap方式调用ws
    * @author Yxc
    * @date 2021/11/22 10:42
    * @param
    * @return void
    */
    public static void SOAP4WS() throws Exception {

        //服务的地址
        URL wsUrl = new URL("http://192.168.10.177:7802/WS10003");

        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        OutputStream os = conn.getOutputStream();

        //请求体
        String request = "<![CDATA[<ESBEntry> <AccessControl> <SysFlag>1</SysFlag> <UserName>HIS</UserName> <Password>HIS</Password> <Fid>BS02003</Fid> <OrderNo>BS02003S01001</OrderNo> </AccessControl> <MessageHeader> <Fid>BS02003</Fid> <OrderNo>BS02003S01001</OrderNo> <SourceSysCode>S01</SourceSysCode> <TargetSysCode>S00</TargetSysCode> <MsgDate>2020-04-14 15:43:44</MsgDate> </MessageHeader> <RequestOption> <triggerData>0</triggerData> <dataAmount>500</dataAmount> </RequestOption> <MsgInfo flag=\"1\" > <Msg></Msg> <distinct value=\"0\"/> <query item=\"BED_CODE\" compy=\"=\" value=\"'0803'\" splice=\"AND\"/> </MsgInfo> <GroupInfo flag=\"0\"> <AS ID=\"\" linkField=\"\"/> </GroupInfo> </ESBEntry>]]>";

        String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:esb=\"http://esb.ewell.cc\">"+
                "<soapenv:Header/>"+
                "<soapenv:Body>"+
                "<esb:HIPMessageServer>"+
                " <esb:inputDataChannel>DC10001</esb:inputDataChannel>"+
                "<esb:input>"+request+
                "</esb:input>"+
                "</esb:HIPMessageServer>"+
                "</soapenv:Body>"+
                "</soapenv:Envelope>";

        os.write(soap.getBytes());

        System.out.println("request:"+os);
        InputStream is = conn.getInputStream();

        byte[] b = new byte[1024];
        int len = 0;
        String s = "";
        while((len = is.read(b)) != -1){
            System.out.println(len);
            String ss = new String(b,0,len,"UTF-8");
            s += ss;
        }
        System.out.println("response:"+s);

        is.close();
        os.close();
        conn.disconnect();

    }

}
