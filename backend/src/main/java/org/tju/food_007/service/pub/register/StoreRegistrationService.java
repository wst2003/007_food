package org.tju.food_007.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.CustomRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.StoreRegistrationRequestDTO;
import org.tju.food_007.dto.pub.register.StoreRegistrationResponseDTO;
import org.tju.food_007.dto.pub.register.mapper.CustomRegistrationRequestMapper;
import org.tju.food_007.dto.pub.register.mapper.StoreRegistrationRequestMapper;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;
import org.tju.food_007.repository.StoreRegistrationRepository;
import org.tju.food_007.repository.pub.register.CustomRegistrationRepository;
import org.tju.food_007.repository.pub.register.UserRegistrationRepository;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author WGY
 * @create 2024-03-10-11:30
 */
@Service
public class StoreRegistrationService {
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    StoreRegistrationRepository storeRegistrationRepository;
    private final StoreRegistrationRequestMapper  storeRegistrationRequestMapper =
            StoreRegistrationRequestMapper.INSTANCE;
    @Transactional
    public StoreRegistrationResponseDTO StoreRegister(StoreRegistrationRequestDTO request) {
        UserEntity newUser = storeRegistrationRequestMapper.requestToUserEntity(request);
        StoreEntity newSto = storeRegistrationRequestMapper.requestToStoEntity(request);
        StoreRegistrationResponseDTO response=new StoreRegistrationResponseDTO();

        LocalDateTime now = LocalDateTime.now();

        // 设置日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 格式化当前日期时间
        String formattedDateTime = now.format(formatter);

        // 输出格式化后的日期时间
        System.out.println("新商家注册，当前时间为: " + formattedDateTime);

        // 格式化当前日期时间
        formattedDateTime = now.format(formatter);
        newUser.setUserState(1);
        newUser.setUserBalance(BigDecimal.valueOf(0));
        newUser.setUserRegTime(Timestamp.valueOf(now));
        newUser.setUserType(1);
        if (userRegistrationRepository.existsByUserPhone(newUser.getUserPhone())) {
            System.out.println("该号码已存在："+newUser.getUserPhone());
            response.setMsg("该号码已存在");
        }
        else {
            UserEntity temp=userRegistrationRepository.save(newUser);
            System.out.println("User已经存入，ID为" + temp.getUserId());
            response.setMsg("User成功注册");

            newSto.setStoId(temp.getUserId());
            newSto.setStoState(1);
            StoreEntity tempSto= storeRegistrationRepository.save(newSto);
            response.setMsg("Store成功注册");
        }
        return response;
    }
}
