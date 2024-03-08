package org.tju.food_007.controller.sto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.sto.StoCategoriesOnsaleResponseDTO;
import org.tju.food_007.service.sto.StocategoriesService;

@RestController
@RequestMapping("/api/sto")
public class StocategoriesController {

    @Autowired
    private StocategoriesService stocategoriesService;

    @RequestMapping(value = "/stocategories",method = RequestMethod.GET)
    public ResponseEntity<StoCategoriesOnsaleResponseDTO> getStoCategoriesOnsale
            (@RequestParam Integer sto_ID){
        StoCategoriesOnsaleResponseDTO response =stocategoriesService.getStoCategoriesOnsale(sto_ID);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
