package com.xiao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtil {
    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    private FTPClient ftpClient;
    private boolean		is_connected;

    //FTP基本参数
    private static String		FTPIP				= "";	//FTP ip地址
    private static int			FTPPORT				= 1;    //FTP 端口
    private static String		FTPUSER				= "";	//FTP 登录用户名
    private static String		FTPPASS				= "";	//FTP 登录密码
    private static String		FTPDISTFOLDER		= "";	//FTP 目标保存路径
    private static int			bufferSize			= 1024 * 1024 * 10;	//10M

    public FtpUtil() {
        ftpClient = new FTPClient();
        is_connected = false;
    }

    /**
     * 设置超时时间
     *
     * @param defaultTimeoutSecond		设置默认超时时间
     * @param connectTimeoutSecond		设置连接超时时间
     * @param dataTimeoutSecond			设置从数据连接读取时使用的超时（以毫秒为单位）。
     */
    public FtpUtil(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond) {
        ftpClient = new FTPClient();
        is_connected = false;

        ftpClient.setDefaultTimeout(defaultTimeoutSecond * 1000);
        ftpClient.setConnectTimeout(connectTimeoutSecond * 1000);
        ftpClient.setDataTimeout(dataTimeoutSecond * 1000);
    }

    /**
     * FTP 服务器连接
     *
     * @param isTextMode	文本 / 二进制 	1 :text 2 : 二进制
     * @throws IOException	on I/O errors
     *
     * @return is_connected	true:连接成功，false:连接失败
     */
    public boolean connectFTP(int isTextMode) throws IOException {
        is_connected = true;

        is_connected = connect(isTextMode);
        return is_connected;
    }


    /**
     * FTP 服务器连接
     *
     * @throws IOException	on I/O errors
     *
     * @return is_connected	true:连接成功，false:连接失败
     */
    public boolean connectFTP() throws IOException {
        is_connected = true;

        is_connected = connect(0);
        return is_connected;
    }


    /**
     * FTP 服务器连接
     *
     * @param isTextMode	文本 / 二进制 	1 :text 2 : 二进制
     * @throws IOException	on I/O errors
     *
     * @return is_connected	true:连接成功，false:连接失败
     */
    private boolean connect(int isTextMode) throws IOException {

        // 连接FTP服务器
        try {

            ftpClient.connect(FTPIP, FTPPORT);

            // 设置中文编码格式
            ftpClient.setControlEncoding("UTF-8");

            // 为缓冲数据流设置内部缓冲区大小。
            ftpClient.setSendBufferSize(bufferSize);

            // 测试FTP返回状态
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                is_connected = false;
                disconnect();
                logger.debug("FTP 服务器" + FTPIP + "拒绝连接！");
            }

            // 登录FTP
            if (!ftpClient.login(FTPUSER, FTPPASS)) {
                is_connected = false;
                disconnect();
                logger.debug("Method[connect]；{} 登录FTP 服务器,FTPIP为：{},登录失败。检查用户名，密码是否正确", FTPUSER, FTPIP);
            }

            // FTP上传数据类型
            if (isTextMode == 1) {
                // 设置文本格式传输
                ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
            } else if (isTextMode == 2) {
                // 设置以二进制方式传输
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            }else {
                // 默认二进制
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            }

        } catch (SocketException e) {
            is_connected = false;
            logger.error("Method[connect] FTP服务器连接超时：{}", e.getMessage(), e);
        } catch (UnknownHostException e) {
            is_connected = false;
            logger.error("Method[connect] 找不到FTP服务器：{}", e.getMessage(), e);
        } catch (Exception e){
            is_connected = false;
            logger.error("Method[connect] 系统异常：{}", e.getMessage(), e);
        }

        return is_connected;
    }


    /**
     * 上传文件
     * @param filename  上传到FTP服务器后的文件名称
     * @param originfilename 待上传文件的名称（绝对地址）
     * @return
     */
    public boolean uploadFileFromProduction(String filename, String originfilename) {
        boolean flag = false;
        try {

            // 设置被动模式
            ftpClient.enterLocalPassiveMode();
            // 文件流传输模式
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            InputStream inputStream = new FileInputStream(new File(originfilename));
            flag = uploadFile(filename, inputStream);
        } catch (Exception e) {
            logger.error("Method[uploadFileFromProduction]：上传文件异常{}", e.getMessage(), e);
        }
        return flag;
    }


    /**
     * 上传文件
     * @param originFilePath 待上传文件的名称（绝对地址）
     * @return
     */
    public boolean uploadFileFromProduction(String originFilePath) {
        boolean uploadFlag = false;
        try {
            String fileName = new File(originFilePath).getName();

            // 设置被动模式
            // 调用FTPClient.enterLocalPassiveMode();这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            // 为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在Linux上，由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftpClient.enterLocalPassiveMode();
            // 文件流传输模式
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            InputStream inputStream = new FileInputStream(new File(originFilePath));

            uploadFlag = uploadFile(fileName, inputStream);
        } catch (Exception e) {
            logger.error("Method[uploadFileFromProduction]：上传文件异常{}", e.getMessage(), e);
        }
        return uploadFlag;
    }

    /**
     * 上传文件
     * @param fileName  上传到FTP服务器后的文件名称
     * @param inputStream 输入文件流
     * @return
     */
    private boolean uploadFile(String fileName, InputStream inputStream) {

        // 判断是否上传成功
        boolean uploadFlag = false;

        try {
            // 判断此时ftpClient是否断开
            if(!ftpReconnection()){
                logger.error("Method[uploadFile]：FTP服务器异常,请检查。。。");
                return false;
            }

            // 选择目标路径
            ftpClient.changeWorkingDirectory(FTPDISTFOLDER);
            // 保存文件
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            uploadFlag = true;
        } catch (Exception e) {
            logger.error("Method[uploadFile]：上传文件异常{}", e.getMessage(), e);
        } finally {
            disconnect();
        }

        return uploadFlag;
    }


    /**
     * 删除FTP服务器文件
     *
     * @param ftpFileName 文件路径
     * @throws IOException on I/O errors
     */
    public void removeOriginfile(String ftpFileName) {
        remove(ftpFileName);
    }


    /**
     * 删除FTP服务器所有文件
     */
    public void removeAllFile() {
        remove("");
    }


    /**
     * 删除FTP服务器文件
     *
     * @param ftpFileName
     */
    private void remove(String ftpFileName){

        try {
            // 判断此时ftpClient是否断开
            if(!ftpReconnection()){
                logger.error("Method[remove]：FTP服务器异常,请检查。。。");
                return ;
            }

            FTPFile[] fileInfoArray=null;

            // 设置当前数据连接模式 PASSIVE_LOCAL_DATA_CONNECTION_MODE
            ftpClient.enterLocalPassiveMode();

            // 获取文件信息
            fileInfoArray = ftpClient.listFiles(ftpFileName);

            if (fileInfoArray == null) {
                logger.error("Method[remove]：File {} Ftp服务器中未找到",ftpFileName);
                return ;
            }

            // 改变目录
            ftpClient.changeWorkingDirectory(FTPDISTFOLDER);

            // 删除文件
            for(int i=0;i<fileInfoArray.length;i++){
                ftpClient.deleteFile(fileInfoArray[i].getName());
            }

        } catch (FileNotFoundException e) {
            logger.error("Method[remove]：File {} Ftp服务器中未找到",ftpFileName,e);
        } catch (Exception e) {
            logger.error("Method[remove]：FTP服务器文件 {} 删除失败, 详细信息{}", ftpFileName, e.getMessage());
        }
    }


    /**
     * 获取FTP根目录文件名
     *
     * @return
     */
    public List<String> getListFiles(){

        List<String> list = new ArrayList<String>();
        list = getFtpListFiles("");
        return list;
    }


    /**
     * 获取文件名
     *
     * @param filePath
     * @return
     */
    public List<String> getListFiles(String filePath){

        List<String> list = new ArrayList<String>();
        list = getFtpListFiles(filePath);
        return list;
    }


    /**
     * 获取ftp 文件名
     *
     * @param filePath	绝对路径
     * @return
     */
    private List<String> getFtpListFiles(String filePath){

        List<String> list = new ArrayList<String>();

        FTPFile[] fileInfoArray=null;

        // 判断此时ftpClient是否断开
        if(!ftpReconnection()){
            logger.error("Method[getFtpListFiles]：FTP服务器异常,请检查。。。");
            return list;
        }

        try {
            // 获取更目录下文件
            fileInfoArray = ftpClient.listFiles(filePath);

            // 获取ftp
            for(int i=0;i<fileInfoArray.length;i++){
                list.add(fileInfoArray[i].getName());
            }

        } catch (IOException e) {
            logger.error("Method[getFtpListFiles]：FTP异常{}", e.getMessage(),e);
        }

        return list;
    }

    /**
     * 判断此时ftpClient是否断开,进行重连
     *
     * @return
     */
    private boolean ftpReconnection(){

        boolean flg = true;

        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) || is_connected == false){
            // 关闭连接
            disconnect();

            // 开启连接
            try {
                is_connected = connectFTP();

                if(!is_connected){
                    is_connected = false;
                    flg = false;
                    logger.error("Method[remove]：FTP服务器连接失败");
                    return false;
                }

            } catch (IOException e) {
                is_connected = false;
                flg = false;
                logger.error("Method[ftpReconnection]：FTP服务器异常{}",e.getMessage(),e);
            }
        }

        return flg;
    }


    /**
     * 断开FTP服务器连接
     *
     * @throws IOException on I/O errors
     */
    public void disconnect(){

        if (ftpClient == null){
            return;
        } else if(ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
                ftpClient=null;
                is_connected = false;
            } catch (IOException ex) {
                logger.error("Method[disconnect]FTP服务器断开异常：{}", ex.getMessage(), ex);
            }
        }
    }
    /**
     * FTP配置验证
     * */
    public boolean checkFTP(){
        try {
            //判断FTP验证开关是否打开
            boolean connFlag = connectFTP(2);
            if(!connFlag){
                logger.debug("FTP验证失败，请关闭FTP开关或配置正确的FTP连接参数");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
