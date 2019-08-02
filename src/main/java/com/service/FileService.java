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
        String originalDirPath = PropertiesConfigUtil.getConfigByKey("originalAir");
        String analyseAir = PropertiesConfigUtil.getConfigByKey("analyseAir");
        File fileDir = new File(originalDirPath);
        if (!fileDir.isDirectory()){//判断文件夹路径是否有效
            logger.error("文件夹路径不存在");
            return;
        }
        File[] airFiles = fileDir.listFiles();
        if (airFiles.length == 0) {
            logger.info("没有新xml文件上传");
            return;
        }
        try {
            Thread.sleep(10000);//睡眠三秒 等待文件上传结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("大气文件夹有"+airFiles.length+"条新数据上传");
        //2循环文件夹文件
        for (File airFile : airFiles) {
            //2.1  ftp上传文件
            FtpUtils.uploadAirXml(airFile.getAbsolutePath(), airFile.getName());

            //2.2  本地io 剪切文件
            try {
                FileMoveUtil.cutFile(airFile,analyseAir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**备份水XML文件*/
    public void backupWaterXml() {
        //1 扫描源文件夹
        String originalDirPath = PropertiesConfigUtil.getConfigByKey("originalWater");
        String analyseAir = PropertiesConfigUtil.getConfigByKey("analyseWater");
        File fileDir = new File(originalDirPath);
        if (!fileDir.isDirectory()){//判断文件夹路径是否有效
            logger.error("文件夹路径不存在");
            return;
        }
        File[] waterFiles = fileDir.listFiles();
        if (waterFiles.length == 0) {
            logger.info("没有新xml文件上传");
            return;
        }
        try {
            Thread.sleep(10000);//睡眠三秒 等待文件上传结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("水文件夹有"+waterFiles.length+"条新数据上传");
        //2循环文件夹文件
        for (File waterFile : waterFiles) {
            //2.1  ftp上传文件
            FtpUtils.uploadWaterXml(waterFile.getAbsolutePath(), waterFile.getName());
            //2.2  本地io 剪切文件
            try {
                FileMoveUtil.cutFile(waterFile,analyseAir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args) {
        FileService service = new FileService();
        service.backupWaterXml();
    }
}
