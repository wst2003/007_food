package org.tju.food_007.dto.pub.register.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.pub.register.CustomRegistrationRequestDTO;
import org.tju.food_007.model.CustomerEntity;
import org.tju.food_007.model.UserEntity;

/**
 * @author WGY
 * @create 2024-03-05-14:53
 */

@Mapper
public interface CustomRegistrationRequestMapper {
    CustomRegistrationRequestMapper INSTANCE = Mappers.getMapper(CustomRegistrationRequestMapper.class);
    // target的字段和entity中的相同，source的字段和dto中的相同
    @Mapping(target ="userId",ignore = true)
    @Mapping(target="userAddress",source = "user_address")
    @Mapping(target="userPhone",source = "user_phone")
    @Mapping(target="userPassword",source = "user_password")
    @Mapping(target="userState",ignore = true)
    @Mapping(target="userBalance",ignore = true)
    @Mapping(target="userRegTime",ignore = true)
    @Mapping(target = "userType",ignore = true)
    @Mapping(target="userGender",source = "user_gender")
    UserEntity requestToUserEntity(CustomRegistrationRequestDTO request);

    @Mapping(target="cusId",ignore = true)
    @Mapping(target = "cusNickname",source = "cus_nickname")
    @Mapping(target = "cusPayPassword",source = "cus_payPassword")
    @Mapping(target = "cusState",ignore = true)
    CustomerEntity requestToCusEntity(CustomRegistrationRequestDTO request);

}

