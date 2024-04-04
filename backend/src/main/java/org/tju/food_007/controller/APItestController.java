package org.tju.food_007.controller;

import cn.hutool.core.io.resource.MultiFileResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tju.food_007.service.com.GptService;
import org.tju.food_007.utils.WebIATWS;


import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api/test")
public class APItestController {
    @Autowired
    GptService gptService;


    @RequestMapping(value = "iflytek",method = RequestMethod.POST)
    public ResponseEntity<String> testiflytek(@RequestParam("file") MultipartFile file) {
        try {
            String result = WebIATWS.TranlateVideoIntoWords(file);
            System.out.println(result);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
