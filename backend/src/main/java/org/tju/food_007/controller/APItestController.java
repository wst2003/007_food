package org.tju.food_007.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/test")
public class APItestController {
    @RequestMapping(value = "iflytek",method = RequestMethod.POST)
    public ResponseEntity<String> testiflytek(@RequestBody String words){
        System.out.println(words);
        return new ResponseEntity("success",HttpStatus.OK);
    }
}
