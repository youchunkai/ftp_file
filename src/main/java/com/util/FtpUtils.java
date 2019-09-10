package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public class FtpUtils {
	private static final String hostname = PropertiesConfigUtil.getConfigByKey("ftpHost");
    private static final Integer port = Integer.valueOf(PropertiesConfigUtil.getConfigByKey("ftpPort"));

    private static final String airUserName = PropertiesConfigUtil.getConfigByKey("airUserName");
    private static final String airPWD = PropertiesConfigUtil.getConfigByKey("airPWD");

    private static final String waterUserName = PropertiesConfigUtil.getConfigByKey("waterUserName");
    private static final String waterPWD = PropertiesConfigUtil.getConfigByKey("waterPWD");

    private static final String testUserName = PropertiesConfigUtil.getConfigByKey("testUserName");
    private static final String testPWD = PropertiesConfigUtil.getConfigByKey("testPWD");


    private static Logger logger = Logger.getLogger(FTPClient.class);

    /**
     * @desc ftp上传文件
     * @Param: pathName:源文件的绝对路径文件名 fileName：文件名
     * @return 上传成功 true，失败 false
     * @throws
    */
    public static boolean uploadAirXml(String pathName,String fileName){
        FTPClient client = null;
        FileInputStream fis = null;
        boolean result = false;
        try {
            client = getFtpClient("1");
            fis = new FileInputStream(new File(pathName));
            result = client.storeFile(fileName, fis);
        }catch (IOException e){
            logger.error("ftp上传文件出现错误，文件被剪切到上传错误文件夹");
        }finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常！");
                }
            }
        }
        return result;
    }


    /**
     * @desc ftp上传文件
     * @Param: pathName:源文件的绝对路径文件名 fileName：文件名
     * @return 上传成功 true，失败 false
     * @throws
     */
    public static boolean uploadTestXml(String pathName,String fileName){
        FTPClient client = null;
        FileInputStream fis = null;
        boolean result = false;

        try {
            client = getFtpClient("3");
            fis = new FileInputStream(new File(pathName));
            result = client.storeFile(fileName, fis);
        }catch (IOException e){
            logger.error("ftp上传文件出现错误，文件被剪切到上传错误文件夹");
        }finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("输入流关闭异常！");
                }
            }
        }
        return result;
    }

    public static FTPClient getFtpClient(String dataType) throws IOException {
        FTPClient client = new FTPClient();
        client.connect(hostname, port);

        if("1".equals(dataType)){
            client.login(airUserName, airPWD);
        }else if("2".equals(dataType)){
            client.login(waterUserName, waterPWD);
        }else{
            client.login(testUserName, testPWD);
        }

        client.setBufferSize(1024);
        client.setControlEncoding("utf8");
        client.setFileType(FTPClient.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();
        return client;
    }



    
    public static void main(String[] args) throws IOException {

        FTPClient client = new FTPClient();
        String hostname = "117.39.29.99";
        int port = 6021;
        String airUserName = "test";
        String airPWD = "test123";
        client.connect(hostname, port);
        client.login(airUserName, airPWD);

        client.setFileType(FTPClient.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();

        String fileName = "test.xml";
        String filePath = "D:/1.xml";
        FileInputStream fis = new FileInputStream(new File(filePath));

        boolean result = client.storeFile(fileName, fis);

        System.out.println(client);
        System.out.println(result);

    }
}
