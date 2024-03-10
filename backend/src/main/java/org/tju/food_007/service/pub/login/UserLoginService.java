package org.tju.food_007.service.pub.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.repository.CustomRegistrationRepository;
import org.tju.food_007.repository.UserRegistrationRepository;
import org.tju.food_007.repository.pub.login.UserLoginRepository;

/**
 * @author WGY
 * @create 2024-03-10-16:53
 */
@Service
public class UserLoginService {
    @Autowired
    UserLoginRepository userLoginRepository;
    @Transactional
    UserLoginResponseDTO UserLogin(UserLoginRequestDTO requestDTO){
        return null;
    }
}
