package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.FileChannel;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
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
            if(null != client){
                fis = new FileInputStream(new File(pathName));
                result = client.storeFile(fileName, fis);
            }else{
                return result;
            }
        }catch (IOException e){
            logger.error("ftp上传文件出现错误，文件被剪切到上传错误文件夹"+e);
        }finally {
            if (null != fis) {
                try {
                    fis.close();
                    logger.info("ftp文件后，输入流正常关闭！");
                } catch (IOException e) {
                    logger.error("ftp文件后，输入流关闭异常！");
                }
            }
            if(null != client){
                try {
                    client.disconnect();
                    logger.info("ftp连接正常关闭");
                } catch (IOException e) {
                    logger.error("ftp连接关闭异常！");
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
            if(null != client){
                fis = new FileInputStream(new File(pathName));
                result = client.storeFile(fileName, fis);
            }else{
                return result;
            }

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

            if(null != client){
                try {
                    client.disconnect();
                } catch (IOException e) {
                    logger.error("ftp连接关闭异常！");
                }
            }
        }
        return result;
    }

    public static FTPClient getFtpClient(String dataType) throws IOException {
        logger.info("开始获取ftp连接");
        FTPClient client = new FTPClient();
        client.setConnectTimeout(30000); //连接超时时间
        client.connect(hostname, port);
        client.setDataTimeout(30000);    //数据传输超时时间
        client.setSoTimeout(30000);      //socket超时时间
        client.setBufferSize(1024);      //缓冲区大小
        client.setControlEncoding("utf8"); //编码格式
        client.setFileTransferMode(FTP.BINARY_FILE_TYPE); //传输模式
        client.setFileType(FTP.BINARY_FILE_TYPE); //  传输类型

        if(client.isConnected()){
            logger.info("连接成功");
            if("1".equals(dataType)){
                client.login(airUserName, airPWD);
                logger.info("登录成功");
            }else if("2".equals(dataType)){
                client.login(waterUserName, waterPWD);
                logger.info("登录成功");
            }else{
                client.login(testUserName, testPWD);
                logger.info("登录成功");
            }
            client.enterLocalPassiveMode();
            return client;
        }else{
            logger.error("ftp发起连接失败！");
            return null;
        }
    }




//    public static void main(String[] args) throws IOException {
//
//        FTPClient client = null;
//        try {
//
//            client = new FTPClient();  //113.137.35.76  8081
//            String hostname = "117.39.29.99";  //"117.39.29.99"   10.16.146.121     117.39.29.99    10.16.146.111
//            int port = 6162;                    //  6162             8161           6021            21
//
////            String hostname = "10.16.146.121";
////            int port = 8161;
//            String airUserName = "air";
//            String airPWD = "ftp123!@#";
//
////            client.enterRemotePassiveMode();
//            client.connect(hostname, port);
//            client.login(airUserName, airPWD);
//
//            System.out.println("客户端是否连接："+client.isConnected());
////            System.out.println("remote port is :"+client.getRemotePort());
////            int reply = client.getReplyCode();
////            System.out.println("reply="+reply);
////            if (!FTPReply.isPositiveCompletion(reply)) {
////                System.out.println("client isn't positive completion ");
////            }
//
//            client.enterLocalPassiveMode();
//            System.out.println("passive port is "+client.getPassivePort());
//            System.out.println("连接方式为：" + client.getDataConnectionMode());
//
//            String filePath = "D:/1.xml";
//            for (int i = 1; i <= 50; i++) {
//                FileInputStream fis = new FileInputStream(new File(filePath));
//                String fileName = "test" + i + ".xml";
//                boolean result = client.storeFile(fileName, fis);
//
//                System.out.println(i+ "  " + result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            client.disconnect();
//
//        }
//
//
//
//    }
}
