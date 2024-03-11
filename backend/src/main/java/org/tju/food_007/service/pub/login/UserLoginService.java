package org.tju.food_007.service.pub.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.login.UserLoginRequestDTO;
import org.tju.food_007.dto.pub.login.UserLoginResponseDTO;
import org.tju.food_007.model.UserEntity;
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
    public UserLoginResponseDTO UserLogin(UserLoginRequestDTO requestDTO){
        UserLoginResponseDTO responseDTO=new UserLoginResponseDTO();
        UserEntity targetUser=userLoginRepository.findByUserPhone(requestDTO.getUser_phone());
        if (targetUser!=null){
            if(targetUser.getUserPassword().equals(requestDTO.getUser_password()))
            {
                responseDTO.setUser_type(targetUser.getUserType());
                responseDTO.setMsg("success");
            }
            else
            {
                {
                    responseDTO.setUser_type(-1);
                    responseDTO.setMsg("密码错误");
                }

            }
        }
        else {
            {
                responseDTO.setUser_type(-1);
                responseDTO.setMsg("账号不存在");
            }
        }
        return responseDTO;
    }
}
