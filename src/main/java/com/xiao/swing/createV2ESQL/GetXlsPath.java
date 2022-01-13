package com.xiao.swing.createV2ESQL;

import java.io.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

//import jxl.*;


public class GetXlsPath {

    public Document Doc;
    public static String OutfileName;


    private static int rows;
    private static int cells;

    public void writeXml(String xmlfile) {
        try {
            //输出xml
            FileWriter fileWriter = new FileWriter(xmlfile);
            //美化xml
            OutputFormat format = OutputFormat.createPrettyPrint();

            format.setEncoding("utf-8"); //定义xml编码格式
            //format.setSuppressDeclaration(true);
            format.setIndent(true); //设置是否缩进
            format.setIndent("   "); //以空格方式实现缩进
            //format.setNewlines(true); //设置是否换行
            XMLWriter xmlWriter = new XMLWriter(System.out,format);

            xmlWriter.setWriter(fileWriter);
            xmlWriter.write(Doc);
            xmlWriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //readExcel(xls,xlsx)
    public static void readExcels(String filename) {
        String filetype = filename.substring(filename.indexOf(".") + 1,filename.length());
        System.out.println(filetype);
        Workbook wb = null;
        try {
            InputStream stream = new FileInputStream(filename);

            if(filetype == "xls"){
                wb = new HSSFWorkbook(stream);
            }else if (filetype == "xlsx") {
                wb = new XSSFWorkbook(stream);
            } else {
                System.out.println("您输入的excel格式不正确");
            }
            Sheet sheet = wb.getSheetAt(0); //第一个sheet

            rows = sheet.getLastRowNum() + 1;//行数
            cells = sheet.getRow(0).getPhysicalNumberOfCells();//列数

            System.out.println(rows);
            System.out.println(cells);

            for (Row row : sheet) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//预定义读取字符串

                for (Cell cell : row) {

                    System.out.print(cell.getStringCellValue()+"  ");
                }
                System.out.println();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String[][] treadExcels(String filename) {
        String result[][] = null;
        String filetype = filename.substring(filename.indexOf(".") + 1,filename.length());
        System.out.println(filetype);
        Workbook wb = null;
        try {
            InputStream stream = new FileInputStream(filename);

            if(filetype=="xls"){
                OutfileName = filename.replace(".xls", ".xml");//定义输出文件
                wb = new HSSFWorkbook(stream);
            }else if (filetype == "xlsx") {
                OutfileName = filename.replace(".xlsx", ".xml");
                wb = new XSSFWorkbook(stream);
            } else {
                System.out.println("您输入的excel格式不正确");
            }
            Sheet sheet = wb.getSheetAt(0); //第一个sheet

            rows = sheet.getLastRowNum();//行数
            cells = sheet.getRow(0).getPhysicalNumberOfCells();//列数

            result = new String[rows][cells];
            //读取xls内容(i=?,j=?)
            for(int i = 1;i <= rows;i++){
                //getCell(列,行)
                sheet.getRow(i).getCell(0).setCellType(Cell.CELL_TYPE_STRING);//预定义读取字符串
                for(int j = 0;j<cells;j++){

                    result[i-1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return result;
        }
        return result;
    }

    //writeTxt
    public static void writeTxt(String pathname,String stream) {

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(new File(pathname));

            long begin = System.currentTimeMillis();

            out.write(stream.getBytes());

            out.close();

            long end = System.currentTimeMillis();
            //时间
            System.out.println("FileOutputStream时间:" + (end - begin) + "毫秒");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //createXml
    public static boolean createXml(String filename){

        //readExcel
        String stream[][] = null;
        stream = treadExcels(filename);
        //创建xml
        try {
            //创建xml
            Document doc = DocumentHelper.createDocument();
            Element rootElement = doc.addElement("msg");
            //head-begin
            Element headElement = rootElement.addElement("head");//head节点
            Element msgId = headElement.addElement("msgId");
            msgId.setText("@服务编号");
            Element version = headElement.addElement("version");
            version.setText("2");
            Element msgName = headElement.addElement("msgName");
            msgName.setText("@消息名称");
            Element sourceSysCode = headElement.addElement("sourceSysCode");
            sourceSysCode.setText("@消息来源的系统编码");
            Element targetSysCode = headElement.addElement("targetSysCode");
            targetSysCode.setText("@消息目标的系统编码");
            Element createTime = headElement.addElement("createTime");
            createTime.setText("@消息发送时间（取系统当前时间，格式：YYYY-MM-DD HH:MI:SS）");
            //head-end
            //body-begin
            Element bodyElement = rootElement.addElement("body");//body节点
            Element row = bodyElement.addElement("row");
            row.addAttribute("action", "select");
            //正文
            for(int i = 0;i < rows;i++){
                //getCell(列,行)
                Element field = row.addElement(stream[i][0]);
                //field.setText("SETNULL(Environment.Variables.Result[1]." + stream[i][0] + ")" + "@" + stream[i][1]);
                field.setText("@" + stream[i][1]);
            }

            //输出xml
            OutfileName = OutfileName.replace(".xml", " V2.00.xml");
            FileWriter fileWriter = new FileWriter(OutfileName);
            //美化xml
            OutputFormat format = OutputFormat.createPrettyPrint();

            format.setEncoding("UTF-8"); //定义xml编码格式
            //format.setSuppressDeclaration(true);
            format.setIndent(true); //设置是否缩进
            format.setIndent("   "); //以空格方式实现缩进
            //format.setNewlines(true); //设置是否换行
            XMLWriter xmlWriter = new XMLWriter(System.out,format);

            xmlWriter.setWriter(fileWriter);
            xmlWriter.write(doc);
            xmlWriter.close();

            //生成ESQL
            GetElePath.setPath(OutfileName);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String xlsfile = "d://test.xlsx";
        System.out.print(xlsfile);
        //String txtfile = "d://test.txt";
        //String xmlfile = "d://test.xml";

        createXml(xlsfile);
        //GetElePath.setPath(OutfileName);

        //readExcels("src\\source\\test.xlsx");
    }
}

