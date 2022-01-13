package com.xiao.oggconfig;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseConfig {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(readXML("HIS.MS_CF01","FID"));
    }
    /**
     * OGG推送字段解析
     * @param tableName
     * @param rootName
     * @return rootValue
     */
    //解析XML
    public static String readXML(String tableName,String rootName) {
        // 读取配置文件
        File input = new File("D:/JMSWorks/ESBJMSConfig.xml") ;
        SAXReader reader = new SAXReader();
        Document document = null;
        String rootValue = null;
        //转化成大写
        tableName  = tableName.toUpperCase();
        rootName = rootName.toUpperCase();
        try {
            //document = DocumentHelper.parseText(xml);// XML
            document = reader.read(input);
            Element rootElt = document.getRootElement();
            //System.out.println(rootElt.getName());
            Iterator<?> iter = rootElt.elementIterator("JMSCONFIG"); // 解析到JMSCONFIG节点
            while (iter.hasNext()) {

                Element tableElt = (Element) iter.next();
                //String fid = recordElt.elementTextTrim("TEST"); //
                //System.out.println("TEST:" + fid);
                Iterator<?> it = tableElt.elementIterator(tableName); // 解析到表名节点
                while(it.hasNext()){					//解析到表名下节点
                    Element e = (Element) it.next();
                    rootValue = e.elementTextTrim(rootName); //解析到表名下节点内容
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rootValue;
    }

}
