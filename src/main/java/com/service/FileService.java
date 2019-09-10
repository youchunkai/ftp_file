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

    /**备份大气XML文件*/
    public void backupAirXml() {
        //1 扫描源文件夹
        String airOriginal = PropertiesConfigUtil.getConfigByKey("airOriginal");
        String airError = PropertiesConfigUtil.getConfigByKey("airError");
        File fileDir = new File(airOriginal);
        if (!fileDir.isDirectory()){//判断文件夹路径是否有效
            logger.error("文件夹路径不存在,路径为："+airOriginal);
            return;
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
                    //成功则删除文件
                    FileMoveUtil.deleteFile(airFile);
                }else{
                    //上传失败，剪切到失败文件夹，再重复上传
                    FileMoveUtil.cutFile(airFile,airError);
                }
            } catch (IOException e) {
                if(flag){
                    logger.error("文件上传成功，但剪切文件发生错误！");
                }else{
                    logger.error("文件上传失败，且剪切文件发生错误！");
                }

            }
        }
    }


    /**备份测试XML文件*/
    public void backupTestXml() {
        //1 扫描源文件夹
        String testOriginal = PropertiesConfigUtil.getConfigByKey("testOriginal");
        String testError = PropertiesConfigUtil.getConfigByKey("testError");
        File fileDir = new File(testOriginal);
        if (!fileDir.isDirectory()) {//判断文件夹路径是否有效
            logger.error("文件夹路径不存在,路径为：" + testOriginal);
            return;
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
    }

}
