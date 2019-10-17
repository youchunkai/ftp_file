package com.task;

import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:config/config.properties")
public class TaskJob {

    @Autowired
    FileService fileService;

    @Scheduled(cron = "${ftpAirXml}")
    public void backupAirXml(){
        fileService.backupAirXml();
    }

    @Scheduled(cron = "${ftpAirXmlRepeat}")
    public void backupAirXmlRepeat(){
        fileService.backupAirXmlRepeat();
    }

//    @Scheduled(cron = "${ftpAirXml}")
    public void deleteXml(){
        fileService.deleteXml();
    }

}
