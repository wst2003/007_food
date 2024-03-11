package org.tju.food_007.controller.cus.indent;


import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.cus.comment.GetCommentResponseDTO;
import org.tju.food_007.dto.cus.indent.GetIndRequestDTO;
import org.tju.food_007.dto.cus.indent.GetIndResponseDTO;
import org.tju.food_007.service.cus.indent.GetIndentService;

import java.util.List;

@RestController
@RequestMapping("/api/cus")
public class GetIndentController {

    @Autowired
    GetIndentService getIndentService;

    @RequestMapping(value = "/getInd",method = RequestMethod.GET)
    public ResponseEntity<GetIndResponseDTO[]> getIndByUserID(@RequestParam int cus_ID,
                                                                 @RequestParam(required = false)Integer state){

        GetIndRequestDTO request = new GetIndRequestDTO();
        request.setCus_ID(cus_ID);
        request.setState(state);
        List<GetIndResponseDTO> response = getIndentService.getIndbyUserID(request);

        return new ResponseEntity<>(response.toArray(new GetIndResponseDTO[0]), HttpStatus.OK);
    }


}
