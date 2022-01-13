package com.xiao.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

public class XslUtil {

    /**
     * @description 简单xml根据xsl模板生成复杂xml
     * @author Yxc
     * @date 2021/11/22 16:35
     * @param
     * @return void
     */
    public static void mergeToXml() {

        String xslFile = "D://PTFM//std_xsl//BS91032B.xsl";
        String xmlFile = "";

        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Templates templates = transFactory.newTemplates(new StreamSource(xslFile));

            DocumentSource docSource = new DocumentSource(DocumentHelper.parseText(xmlFile));
            DocumentResult docResult = new DocumentResult();
            templates.newTransformer().transform(docSource, docResult);
            Document transformDoc = docResult.getDocument();
            String documentStr = transformDoc.asXML();
            System.out.println(documentStr);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
