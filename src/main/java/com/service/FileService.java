package com.service;

import com.util.FileMoveUtil;
import com.util.FtpUtils;
import com.util.PropertiesConfigUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/7/22
 **/
@Service
@SuppressWarnings("all")
public class FileService {

    private Logger logger = Logger.getLogger(FileService.class);
    String airOriginal = PropertiesConfigUtil.getConfigByKey("airOriginal");
    String airError = PropertiesConfigUtil.getConfigByKey("airError");
    String airSuccess = PropertiesConfigUtil.getConfigByKey("airSuccess");

    /**备份大气XML文件*/
    public void backupAirXml() {
        //1 扫描源文件夹

        File fileDir = new File(airOriginal);
        int successNum = 0;

        if (!fileDir.exists()){//判断文件夹路径是否有效
            logger.error("文件夹路径不存在,路径为："+airOriginal+",创建文件夹");
            fileDir.mkdirs();
        }
        File[] airFiles = fileDir.listFiles();
        if (airFiles.length == 0) {
            logger.info("没有新xml文件上传");
            return;
        }
        try {
            Thread.sleep(3000);//睡眠三秒 等待文件上传结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("大气文件夹有"+airFiles.length+"条新数据上传");
        //2循环文件夹文件
        for (File airFile : airFiles) {
            //2.1  ftp上传文件
            boolean flag = FtpUtils.uploadAirXml(airFile.getAbsolutePath(), airFile.getName());

            //2.2  本地io 剪切文件
            try {
                if(flag){
                    //成功则备份文件
                    FileMoveUtil.cutFile(airFile,airSuccess);
                    successNum ++;
                    logger.info("文件上传成功,剪切到备份文件夹");
                }else{
                    //上传失败，剪切到失败文件夹，再重复上传
                    FileMoveUtil.cutFile(airFile,airError);
                    logger.error("上传失败，剪切到失败文件夹，再重复上传");
                }
            } catch (IOException e) {
                if(flag){
                    logger.error("文件上传成功，但剪切文件发生错误！");
                }else{
                    logger.error("文件上传失败，且剪切文件发生错误！");
                }

            }
            logger.info("发现"+airFiles.length+"个文件，成功上传"+successNum+"个");
        }
    }

    /**备份大气XML文件*/
    public void backupAirXmlRepeat() {
        //1 扫描源文件夹
        File fileDir = new File(airError);
        int successNum = 0;
        if (!fileDir.exists()){//判断文件夹路径是否有效
            logger.error("文件夹路径不存在,路径为："+airError+",创建文件夹");
            fileDir.mkdirs();
        }
        File[] airFiles = fileDir.listFiles();
        if (airFiles.length == 0) {
            logger.info("不存在上传错误的xml文件");
            return;
        }
        try {
            Thread.sleep(3000);//睡眠三秒 等待文件上传结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("错误文件夹有"+airFiles.length+"条新数据上传");
        //2循环文件夹文件
        for (File airFile : airFiles) {
            //2.1  ftp上传文件
            boolean flag = FtpUtils.uploadAirXml(airFile.getAbsolutePath(), airFile.getName());

            //2.2  上传文件成功后，删除文件
            if(flag){
                successNum++;
                //成功则备份文件
                try {
                    FileMoveUtil.cutFile(airFile,airSuccess);
                } catch (IOException e) {
                    logger.error("文件上传成功，但剪切文件发生错误！");
                }
            }
            logger.info("大气错误文件夹发现"+airFiles.length+"个文件，成功上传"+successNum+"个");
        }

    }

    /**备份测试XML文件*/
    public void backupTestXml() {
        //1 扫描源文件夹
        String testOriginal = PropertiesConfigUtil.getConfigByKey("testOriginal");
        String testError = PropertiesConfigUtil.getConfigByKey("testError");
        File fileDir = new File(testOriginal);
        int successNum = 0;
        if (!fileDir.exists()) {//判断文件夹路径是否有效
            logger.error("文件夹路径不存在,路径为：" + testOriginal+",创建文件夹");
            fileDir.mkdirs();
        }
        File[] files = fileDir.listFiles();
        if (files.length == 0) {
            logger.info("没有新xml文件上传");
            return;
        }
        try {
            Thread.sleep(3000);//睡眠三秒 等待文件上传结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("测试文件夹有" + files.length + "条新数据上传");
        //2循环文件夹文件
        for (File file : files) {
            //2.1  ftp上传文件
            boolean flag = FtpUtils.uploadTestXml(file.getAbsolutePath(), file.getName());

            //2.2  本地io 剪切文件
            try {
                if (flag) {
                    //成功则删除文件
                    FileMoveUtil.deleteFile(file);
                    successNum++;
                } else {
                    //上传失败，剪切到失败文件夹，再重复上传
                    FileMoveUtil.cutFile(file, testError);
                }
            } catch (IOException e) {
                if (flag) {
                    logger.error("文件上传成功，但剪切文件发生错误！");
                } else {
                    logger.error("文件上传失败，且剪切文件发生错误！");
                }

            }
        }
        logger.info("发现"+files.length+"个文件，成功上传"+successNum+"个");
    }

}
