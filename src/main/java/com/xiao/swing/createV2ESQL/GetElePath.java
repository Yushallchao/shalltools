package com.xiao.swing.createV2ESQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class GetElePath {
    public Document Doc;
    public Element xmlroot;
    public String path;
    public String OutfileName;
    public String ESQL;

    public Document loadDoc(String filename) {
        this.OutfileName = (filename.replace(".xml", "_ESQL") + ".txt");
        this.OutfileName = this.OutfileName.replace(".XML", "_ESQL");
        try
        {
            SAXReader Sr = new SAXReader();

            this.Doc = Sr.read(new File(filename));
            this.xmlroot = this.Doc.getRootElement();
        } catch (Exception ex) {
            ex.printStackTrace();
            return this.Doc;
        }
        return this.Doc;
    }

    private String getFullPath(Element ele) {
        this.path = ele.getUniquePath().replace("']/*[name()='", ".hl7:").replace("/*[name()='", ".hl7:").replace("']", "");
        this.path = this.path.replace("/", ".");

        return this.path;
    }

    private String getFullPath(Attribute attribute) {
        this.path = attribute.getUniquePath().replace("']/*[name()='", ".hl7:").replace("/*[name()='", ".hl7:").replace("']", "").replace("/@", ".(XMLNSC.Attribute)");
        this.path = this.path.replace("/", ".");

        return this.path;
    }
    public void getEleAtt(Element ele) {
        Element element = ele;
        //System.out.println(element.getName());
        for (Iterator<?> itor = ele.elementIterator(); itor.hasNext(); ) {
            element = (Element)itor.next();

            if (element.nodeCount() == 1) { //遍历最小节点
                this.path = element.getName();
                //this.ESQL = ("SET xmlrow." + getFullPath(element) + " = SETNULL(Environment.Variables.Result[1]." + element.getName() + "); --" + element.getText());
                this.ESQL = ("SET xmlrow." + getFullPath(element) + " = " + element.getText() +";");
                this.ESQL = this.ESQL.replace("xmlrow..", "xmlrow.XMLNSC.");
                this.ESQL = this.ESQL.replace("xmlrow..", "xmlrow.XMLNSC.");
                System.out.println(this.ESQL);
                writeToFile(this.ESQL, this.OutfileName);
                this.ESQL = null;
                this.path = null;
            }
            for (int i = 0; i < element.attributeCount(); i++) {
                this.path = ("(XMLNSC.Attribute)" + element.attribute(i).getNamespacePrefix() + ":" + element.attribute(i).getName());
                this.ESQL = ("SET xmlrow." + getFullPath(element.attribute(i)) + " = '" + element.attribute(i).getValue() + "';");
                this.ESQL = this.ESQL.replace("xmlrow..", "xmlrow.XMLNSC.");
                this.ESQL = this.ESQL.replace("xmlrow..", "xmlrow.XMLNSC.");
                System.out.println(this.ESQL);
                writeToFile(this.ESQL, this.OutfileName);
                this.path = null;
                this.ESQL = null;
            }
            this.path = null;

            getEleAtt(element);
        }
    }

    public void writeToFile(String source, String fileName) {

        try { File file = new File(fileName);
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            out.write(source);
            out.newLine();
            out.close();
            out = null;
            file = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean setPath(String fn) {
        GetElePath gep = new GetElePath();
        gep.loadDoc(fn);
        if (gep.Doc == null)
            return false;
        gep.getEleAtt(gep.xmlroot);
        return true;
    }

    /**
     * 根据写好的xml，生成hl7的ESQL代码
     * @param args
     */
    public static void main(String[] args)
    {
        String filename = "D:\\BS10001 病人住院信息 VMH1.00.xml";
        setPath(filename);
    }

}
