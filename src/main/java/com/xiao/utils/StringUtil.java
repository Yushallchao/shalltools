package com.xiao.utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * @description 字符串写入文件
     * @author Yxc
     * @date 2021/11/22 10:05
     * @param source
     * @return void
     */
    public static void writeToFile(String source) {
        String filename = "D:\\uncode.txt";
        try {
            File file = new File(filename);
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            //原UTF-8格式输出//BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"GBK"));
            out.write(source);
            out.newLine();
            out.close();
            out = null;
            file = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @description 去除xml非法字段
     * @author Yxc
     * @date 2021/11/22 9:57
     * @param source
     * @return java.lang.String
     */
    public static String exceptForXml(String source) {
        //System.out.println("源数据："+source);
        System.out.println("源数据Unicode："+ChToUnicode(source));
        String out = null;
		/*不可见的特殊字符
		0x00 - 0x08
		0x0b - 0x0c
		0x0e - 0x1f
		fdff专指"�"
		*/
        //中文进行转换
        source = source.replaceAll("[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]", "").replace("�", "").replace("f8f5", "");
        System.out.println("去除否Unicode："+ChToUnicode(source));
        out = source;
        return out;
    }
    //汉字转成Unicode
    public static String ChToUnicode(String in) {
        String str = "";
        for(int i = 0;i<in.length();i++){
            //汉字转成Unicode
            String s = Integer.toHexString(in.charAt(i)&0xffff);
            if(s.length()==2){
                str = str + "00" + s; //给标点符号加00
            }else {
                if(s.length()==4){
                    str = str + s;
                }
            }
        }

        return str;
    }
    //中文转化为16进制unicode
    public static String ChToUnicode2(String s)
    {
        String str="";
        for (int i=0;i<s.length();i++)
        {
            int ch = (int)s.charAt(i);

            String s4 = Integer.toHexString(ch);
            if(s4.length()==2){
                str = str + "00" + s4;
            }else {
                if(s4.length()==4){
                    str = str + s4;
                }
            }

        }
        return str;

    }



    //一般16进制转化为中文
    public static String UnicodeToCh(String str)
    {
        String s="";
        for (int i=0;i<str.length()/4;i++)
        {
            s += (char) (Integer.parseInt(str.substring(i*4, i*4+4),16));
        }

        return s;
    }

    //MB16进制（00与值相反）转化为中文
    public static String UnicodeToCh2(String str)
    {
        String s="";
        for (int i=0;i<str.length()/4;i++)
        {
            s += (char) (Integer.parseInt(str.substring(i*4+2, i*4+4)+str.substring(i*4, i*4+2),16));
        }
        String s1 = null;
        try {
            s1 = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
        //return s1;
    }

    public static String UnicodeToCh3(String str)
    {
        String s="";
        for (int i=0;i<str.length()/2;i++)
        {
            s += (char) (Integer.parseInt("00"+str.substring(i*2, i*2+2),16));
            //System.out.println("00"+str.substring(i*2, i*2+2));
        }

        String s1 = null;
        try {
            s1 = new String(s.getBytes("ISO-8859-1"), "utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return s;
        return s1;
    }


    //中文转换成ISO-8859-1(Latin-1)
    public static String ChToLatin(String in) {
        in = "刘雲";
        System.out.println(in);
        String out = "";
        String gbk = "";
        try {
            out = new String(in.getBytes("GBK"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return out;
    }
    //ISO-8859-1(Latin-1)转换成中文
    public static String LatinToCh(String in) {
        //in = "Áõë";
        //in = "<AS_result><AS10001><PAT_INDEX_NO>1957269</PAT_INDEX_NO><ID_NUMBER>420123196603152018</ID_NUMBER><PAT_NAME>Áõ³¤Èð</PAT_NAME></AS10001></AS_result>";
        System.out.println(in);
        String out = "";
        try {
            out = new String(in.getBytes("ISO-8859-1"), "cp936");

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return out;
    }

    /**
     * @description 判断是否为字母
     * @author Yxc
     * @date 2021/11/22 10:11
     * @param c
     * @return boolean
     */
    public static boolean isLetter(char c) {
        int k = 0x80;//128(字符的范围是0~127)
        return c / k == 0 ? true : false;
    }

    /**
     * @description 字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * @author Yxc
     * @date 2021/11/22 10:11
     * @param str
     * @return int
     */
    public static int getlength(String str) {
        if (str == null) {
            return 0;
        }
        char[] c = str.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            //System.out.println(c[i]+"-"+isLetter(c[i]));
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * @description 网页返回16进制，正则表达式替换并转换
     * @author Yxc
     * @date 2021/11/22 10:23
     * @param
     * @return void
     */
    public static void exHexToCh(){
        // 定义正则表达式来搜索中文字符的转义符号
        Pattern compile = Pattern.compile("&#.*?;");
        // 测试用中文字符
        String sourceString = "C&#x96C6;&#x56E2;&#x5929;c&#x6D25;&#x5927;&#x5510;&#x56FD;&#x9645;&#x76D8;&#x5C71;&#x53D1;&#x7535;&#x6709;&#x9650;&#x8D23;&#x4EFB;&#x516C;&#x53F8;";
        Matcher matcher = compile.matcher(sourceString);
        // 循环搜索 并转换 替换
        while (matcher.find()) {
            String group = matcher.group();
            // 获得16进制的码
            String hexcode = "0" + group.replaceAll("(&#|;)", "");
            // 字符串形式的16进制码转成int并转成char 并替换到源串中
            sourceString = sourceString.replaceAll(group, (char) Integer.decode(hexcode).intValue() + "");
        }
        System.out.println(sourceString);

    }

    /**
     *功能描述 去除小数点后多余的0
     * @author Yxc
     * @date 2021/11/19
     * @param  * @param dotStr
     * @return java.lang.String
     */
    public static String SubZero(String dotStr){
        if(dotStr.indexOf(".")>0){//indexOf(".").在父串中首次出现的位置，从0开始

            dotStr = dotStr.replaceAll("0+?$", "");//去掉后面无用的0
            //System.out.println(dotStr);
            dotStr = dotStr.replaceAll("[.]$", "");//如果.后面全是0,则去掉
        }
        return dotStr;
    }

}
