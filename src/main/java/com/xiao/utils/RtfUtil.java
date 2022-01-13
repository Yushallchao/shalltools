package com.xiao.utils;

import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RtfUtil {
    /**
     *功能描述 rtf格式转化成字符串
     * @author Yxc
     * @date 2021/11/19
     * @param  * @param rtfStr
     * @return java.lang.String
     */
    public static String encodeRtfToStr(String rtfStr){
        RTFEditorKit kit = new RTFEditorKit();
        Document doc = kit.createDefaultDocument();
        rtfStr = rtfStr.replace("‘", "'");//中文‘转化成英文'
        //System.out.println(str.length());
        //System.out.println(str.getBytes().length);
        InputStream in = new ByteArrayInputStream(rtfStr.getBytes());// 将String转化成字节流
        //System.out.println(in);
        String text = null;
        try {
            kit.read(in, doc, 0);
            //System.out.println(doc.getText(0, doc.getLength()));
            //转换输出字符格式
            text = new String(doc.getText(0, doc.getLength()).getBytes("ISO8859_1"),"GBK");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        text = text.replace("�", "");
        return text;
    }
}
