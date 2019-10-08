package com.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static void cutFile(File file, String path) throws IOException {

//        File fileDir = new File(path);
//        if(!fileDir.exists()){
//            fileDir.mkdirs();
//        }

        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(path+"/"+file.getName());
        byte[] buff = new byte[1024];
        int c;
        while ((c = fis.read(buff)) != -1) {
            fos.write(buff, 0, c);
        }
        fos.flush();
        fos.close();
        fis.close();
        file.delete();
    }

    /**
     * @desc 删除文件
     * @Param: file:被删除的文件
     */
    public static void deleteFile(File file) {
        file.delete();
    }
}
