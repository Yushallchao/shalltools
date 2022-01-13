package com.xiao.jmeter;

import com.xiao.jmeter.sdk.SDK;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class JMeterSamplerClient extends AbstractJavaSamplerClient{

    /**
     * @description 设置默认入参
     * @author Yxc
     * @date 2021/11/22 15:11
     * @param
     * @return org.apache.jmeter.config.Arguments
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("qmgr", "QMGR.S03");
        arguments.addArgument("queue", "BS10001");

        return arguments;
    }

    /**
     * @description 测试SDK
     * @author Yxc
     * @date 2021/11/22 15:18
     * @param arg0
     * @return org.apache.jmeter.samplers.SampleResult
     */
    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult sr = new SampleResult();
        String qmgr = arg0.getParameter("qmgr");
        String queue = arg0.getParameter("queue");

        try {
            // jmeter 开始统计响应时间标记
            sr.sampleStart();
            String response = SDK.putReqAndGetResp(qmgr,queue);

            // 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。
            sr.setResponseData("结果是："+response, "utf-8");

            sr.setDataType(SampleResult.TEXT);

            //设置响应执行成功
            sr.setSuccessful(true);
        } catch (Throwable e) {
            //有异常,执行失败
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            // jmeter 结束统计响应时间标记
            sr.sampleEnd();
        }
        return sr;
    }

}
