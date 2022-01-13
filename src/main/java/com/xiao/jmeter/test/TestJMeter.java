package com.xiao.jmeter.test;

import com.xiao.jmeter.JMeterSamplerClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;

public class TestJMeter {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JMeterSamplerClient jMeterSamplerClient = new JMeterSamplerClient();
        Arguments arguments = new Arguments();

        arguments = jMeterSamplerClient.getDefaultParameters();
        JavaSamplerContext arg0 = new JavaSamplerContext(arguments);
        jMeterSamplerClient.runTest(arg0);

    }
}
