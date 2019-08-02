package com.task;

import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/7/22
 **/
@Component("TaskJob")
@EnableScheduling
public class TaskJob {

    @Autowired
    FileService fileService;

//    @Scheduled(cron = "0 0/3 * * * ?")
    public void backupAirXml(){
        fileService.backupAirXml();
    }

//    @Scheduled(cron = "0 2/20 * * * ?")
    public void backupWaterXml(){
        fileService.backupWaterXml();
    }



}
