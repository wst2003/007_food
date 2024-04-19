package org.tju.food_007.controller.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.cus.indent.ChangeIndStateRequestDTO;
import org.tju.food_007.dto.cus.indent.GenerateIndentRequestDTO;
import org.tju.food_007.dto.pub.NormalResponseDTO;
import org.tju.food_007.service.cus.indent.GenerateIndentService;

/**
 * @author WGY
 * @create 2024-03-17-16:57
 */
@RestController
@RequestMapping("/api/cus")
public class GenerateIndentController {
    @Autowired
    GenerateIndentService generateIndentService;
    @RequestMapping(value = "/generateIndent",method = RequestMethod.POST)
    public ResponseEntity<String>GenerateIndent(@RequestBody GenerateIndentRequestDTO requestDTO){
        generateIndentService.GenerateIndent(requestDTO);
        return new ResponseEntity<>("订单生成成功", HttpStatus.OK);

    }

    @RequestMapping(value = "/changeIndentState",method = RequestMethod.POST)
    public ResponseEntity<NormalResponseDTO>ChangeIndentState(@RequestBody ChangeIndStateRequestDTO requestDTO){
        generateIndentService.ChangeIndentState(requestDTO);
        //generateIndentService.ModifyStoID();
        NormalResponseDTO responseDTO=new NormalResponseDTO();
        responseDTO.setMsg("success");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }




}
