package org.tju.food_007.controller.pub.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.StoreRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.StoreRegistrationResponseDTO;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.service.CustomerRegistrationService;
import org.tju.food_007.service.StoreRegistrationService;

/**
 * @author WGY
 * @create 2024-03-05-14:58
 */
@RestController
@RequestMapping("/api/pub/register")
public class UserRegistrationController {
    @Autowired
    CustomRegistrationRepository customRegistrationRepository;

    @Autowired
    CustomerRegistrationService customerRegistrationService;


    @Autowired
    StoreRegistrationService storeRegistrationService;
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity<CustomRegistrationResponseDTO> CusRegister (@RequestBody CustomRegistrationRequestDTO request) {
        CustomRegistrationResponseDTO response=new CustomRegistrationResponseDTO();
        CustomRegistrationResponseDTO temp=customerRegistrationService.UserRegister(request);
        response=customerRegistrationService.CusRegister(request, temp.getUser_id());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public ResponseEntity<CustomRegistrationResponseDTO> StoRegister (@RequestBody StoreRegistrationRequestDTO request) {
        CustomRegistrationResponseDTO response=storeRegistrationService.StoreRegister(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

//TODO：Delegate IDE build/run actions to Maven如果这个不选择就会报错
