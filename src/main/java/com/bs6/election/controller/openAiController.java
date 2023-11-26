package com.bs6.election.controller;


import com.bs6.election.util.openAiApi.IOpenAiSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class openAiController {

    private Logger logger = LoggerFactory.getLogger(openAiController.class);

    @RequestMapping("/completion")
    public String completion(){

        return "test";
    }

}
