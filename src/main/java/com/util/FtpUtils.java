package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

@SuppressWarnings("all")
public class FtpUtils {
	private static final String hostname = PropertiesConfigUtil.getConfigByKey("ftpHost");
    private static final Integer port = Integer.valueOf(PropertiesConfigUtil.getConfigByKey("ftpPort"));
    private static final String airUserName = PropertiesConfigUtil.getConfigByKey("airUserName");
    private static final String airPWD = PropertiesConfigUtil.getConfigByKey("airPWD");
    private static final String waterUserName = PropertiesConfigUtil.getConfigByKey("waterUserName");
    private static final String waterPWD = PropertiesConfigUtil.getConfigByKey("waterPWD");

    private static final String ftpAir = PropertiesConfigUtil.getConfigByKey("ftpAir");
    private static final String ftpWater = PropertiesConfigUtil.getConfigByKey("ftpWater");

    /**
     * @desc ftp上传文件
     * @Param: pathName:源文件的绝对路径文件名 fileName：文件名
     * @return 上传成功 true，失败 false
     * @throws
    */
    public static boolean uploadAirXml(String pathName,String fileName){
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        boolean result = false;

        try {
            client.connect(hostname, port);
            client.login(airUserName, airPWD);
            fis = new FileInputStream(new File(pathName));

            //设置上传目录
            client.changeWorkingDirectory(ftpAir);
            client.setBufferSize(1024);
            client.setControlEncoding("utf8");

            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            result = client.storeFile(fileName, fis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
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
    public static boolean uploadWaterXml(String pathName,String fileName){
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        boolean result = false;

        try {
            client.connect(hostname, port);
            client.login(waterUserName, waterPWD);
            fis = new FileInputStream(new File(pathName));

            //设置上传目录
            client.changeWorkingDirectory(ftpWater);
            client.setBufferSize(1024);
            client.setControlEncoding("utf8");

            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            result = client.storeFile(fileName, fis);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }

    
    public static void main(String[] args) throws IOException {

        FTPClient client = new FTPClient();
        String hostname = "117.39.29.99";
        int port = 6021;
        String airUserName = "xahj1";
        String airPWD = "xahj1123";
        client.connect(hostname, port);
        client.login(airUserName, airPWD);

        String fileName = "test.xml";
        String filePath = "D:/1.xml";
        FileInputStream fis = new FileInputStream(new File(filePath));

        boolean result = client.storeFile(fileName, fis);

        System.out.println(client);

    }
}
