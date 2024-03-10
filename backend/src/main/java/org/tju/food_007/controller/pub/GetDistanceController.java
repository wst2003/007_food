package org.tju.food_007.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.pub.GetDistanceRequestDTO;
import org.tju.food_007.dto.pub.GetDistanceResponseDTO;
import org.tju.food_007.service.pub.GetDistanceService;

/**
 * @author WGY
 * @create 2024-03-10-16:09
 */

@RestController
@RequestMapping("/api/pub/getDistance")
public class GetDistanceController {
    @Autowired
    GetDistanceService getDistanceService;
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity<GetDistanceResponseDTO>GetStoDistance(@RequestBody GetDistanceRequestDTO requestDTO){
        GetDistanceResponseDTO responseDTO=getDistanceService.GetStoDistance(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
