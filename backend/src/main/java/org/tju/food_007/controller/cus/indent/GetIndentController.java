package org.tju.food_007.controller.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tju.food_007.dto.cus.indent.GetIndentResponseDTO;
import org.tju.food_007.dto.cus.indent.RatingRequestDTO;
import org.tju.food_007.service.cus.indent.GetIndentService;
import org.tju.food_007.service.cus.indent.IndentRatingService;

/**
 * @author WGY
 * @create 2024-03-17-21:35
 */
@RestController
@RequestMapping("/api/cus/getIndById")
public class GetIndentController {
    @Autowired
    GetIndentService getIndentService;


    @GetMapping("")
    public ResponseEntity<GetIndentResponseDTO> GetIndent(@RequestParam("ind_id") int indentId){
        return new ResponseEntity<>(getIndentService.GetIndent(indentId), HttpStatus.OK);
    }
}
