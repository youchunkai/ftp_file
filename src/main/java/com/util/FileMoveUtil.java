package com.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Desc:文件移动工具类
 * Author:Kevin
 * Date:2019/6/14
 **/
@SuppressWarnings("all")
public class FileMoveUtil {

    private static Logger logger = Logger.getLogger(FileMoveUtil.class);

    /**
     * @desc 剪切文件
     * @Param: file:被剪切的文件 path：剪切后的文件夹路径
    */
    public static void cutFile(File file, String path){

//        File fileDir = new File(path);
//        if(!fileDir.exists()){
//            fileDir.mkdirs();
//        }

        InputStream fis = null;
        OutputStream fos = null;


        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(path+"/"+file.getName());
            byte[] buff = new byte[1024];
            int c;
            while ((c = fis.read(buff)) != -1) {
                fos.write(buff, 0, c);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            logger.error("文件未发现！");
        } catch (IOException e) {
            logger.error(e);
        }finally {
            if (fos != null){
                try {
                    fos.close();
//                    logger.info("输出流正常关闭！");
                } catch (IOException e) {
                    logger.error("输出流关闭异常！");
                }
            }

            if (fis != null) {
                try {
                    fis.close();
//                    logger.info("输入流正常关闭！");
                } catch (IOException e) {
                    logger.error("输入流关闭异常！");
                }
            }
            deleteFile(file);

            if(file.exists()){
                logger.error("如果走到这，说明程序有异常，file.delete()失败！,再次尝试删除！");
                System.gc();//在删除之前调用垃圾回收,这样jvm就不会占用文件了
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deleteFile(file);
                if(file.exists()){
                    logger.error("文件仍然存在！请检查！");
                }
            }
        }

    }

    /**
     * @desc 删除文件
     * @Param: file:被删除的文件
     */
    public static void deleteFile(File file) {
        try {
//            Path path = file.toPath();
//            logger.info("file.toPath().getParent():"+path.getParent());
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            logger.error("删除文件失败，请检查流是否全部关闭，以及文件夹权限");
        }
    }
}
