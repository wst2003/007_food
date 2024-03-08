package org.tju.food_007.controller.sto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.sto.StoinformationdetailResponseDTO;
import org.tju.food_007.service.sto.StoinformationdetailService;

@RestController
@RequestMapping("/api/sto")
public class StoinformationdetailController {

    @Autowired
    private StoinformationdetailService stoinformationdetailService;

    @RequestMapping(value ="/informationdetail",method = RequestMethod.GET)
    public ResponseEntity<StoinformationdetailResponseDTO> Stoinformationdetail
            (@RequestParam Integer sto_ID){
        System.out.println("正在获取商家详细信息");

        StoinformationdetailResponseDTO response =stoinformationdetailService.stoinformationdetail(sto_ID);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
