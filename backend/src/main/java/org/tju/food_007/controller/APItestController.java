package org.tju.food_007.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.service.com.GptService;


import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/test")
public class APItestController {
    @Autowired
    GptService gptService;


    @RequestMapping(value = "iflytek",method = RequestMethod.POST)
    public ResponseEntity<String> testiflytek(@RequestBody String words){
        System.out.println(words);
        return new ResponseEntity("success",HttpStatus.OK);
    }
    @RequestMapping(value = "gpt",method = RequestMethod.POST)
    public ResponseEntity<ArrayList<Integer>> testGPT(@RequestBody String words){
        System.out.println(words);
        return new ResponseEntity<>( gptService.AISearch(words),HttpStatus.OK);
    }

    @RequestMapping(value = "aliOCR",method = RequestMethod.POST)
    public ResponseEntity<ArrayList<Integer>> testAliOCR(@RequestBody String words){
        System.out.println(words);
        return new ResponseEntity<>( gptService.AISearch(words),HttpStatus.OK);
    }
}
