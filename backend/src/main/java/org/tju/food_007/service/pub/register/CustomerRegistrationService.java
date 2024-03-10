package org.tju.food_007.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.mapper.CustomRegistrationRequestMapper;
import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.CustomRegistrationRepository;
import org.tju.food_007.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

/**
 * @author WGY
 * @create 2024-03-05-14:58
 */
@Service
public class CustomerRegistrationService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    CustomRegistrationRepository customRegistrationRepository;
    private final CustomRegistrationRequestMapper customRegistrationRequestMapper =
            CustomRegistrationRequestMapper.INSTANCE;
    @Transactional
    public CustomRegistrationResponseDTO UserRegister(CustomRegistrationRequestDTO request) {
        UserEntity newUser = customRegistrationRequestMapper.requestToUserEntity(request);

        CustomRegistrationResponseDTO response=new CustomRegistrationResponseDTO();

        LocalDateTime now = LocalDateTime.now();

        // 设置日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前日期时间
        String formattedDateTime = now.format(formatter);

        // 输出格式化后的日期时间
        System.out.println("新顾客注册，当前时间为: " + formattedDateTime);

        // 格式化当前日期时间
        formattedDateTime = now.format(formatter);
        newUser.setUserState(1);
        newUser.setUserBalance(BigDecimal.valueOf(0));
        //TODO:不知道时间行不行
        newUser.setUserRegTime(Timestamp.valueOf(now));
        newUser.setUserType(0);
        if (userRegistrationRepository.existsByUserPhone(newUser.getUserPhone())) {
            System.out.println("该号码已存在："+newUser.getUserPhone());
            response.setMsg("该号码已存在");
            response.setUser_type(0);
            response.setUser_id(-1);
        }
        else {
            UserEntity temp=userRegistrationRepository.save(newUser);
            System.out.println("User已经存入，ID为" + temp.getUserId());
            response.setMsg("User成功注册");
            response.setUser_type(0);
            response.setUser_id(temp.getUserId());
        }
        return response;
    }

    @Transactional
    public CustomRegistrationResponseDTO CusRegister(CustomRegistrationRequestDTO request,Integer cus_id) {

        CustomerEntity newCus = customRegistrationRequestMapper.requestToCusEntity(request);
        CustomRegistrationResponseDTO response=new CustomRegistrationResponseDTO();
        if(userRegistrationRepository.existsByUserId(cus_id)){
            response.setMsg("对应ID的user存在:"+cus_id);
            newCus.setCusId(cus_id);
            newCus.setCusState(1);
            System.out.println("newCus:"+newCus.getCusId());
            customRegistrationRepository.save(newCus);
            response.setMsg("顾客成功注册");
            response.setUser_type(0);
            response.setUser_id(cus_id);
        }
        else {
            response.setMsg("对应ID的user不存在:"+cus_id);
            response.setUser_type(0);
            response.setUser_id(-1);
        }
        return response;
    }
}






















