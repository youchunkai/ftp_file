package com.controller;

import com.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 * Author:Kevin
 * Date:2019/7/23
 **/
@RestController
public class TaskController {

    @Autowired
    FileService fileService;

    @GetMapping(value="test")
    public String test(){
        return "aaa";
    }


    @GetMapping(value="backupAirXml")
    public void backupAirXml(){
        fileService.backupAirXml();
    }

    @GetMapping(value = "backupTestXml")
    public void backupTestXml(){
        fileService.backupTestXml();
    }
}
