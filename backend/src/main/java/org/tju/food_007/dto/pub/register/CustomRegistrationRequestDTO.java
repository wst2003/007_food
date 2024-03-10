package org.tju.food_007.dto.pub.register;

import lombok.Getter;
import lombok.Setter;

/**
 * @create 2024-03-05-14:49
 */
@Getter
@Setter
public class CustomRegistrationRequestDTO {
    private String user_phone;
    private String user_password;
    private String user_address;
    private int user_gender;
    private String cus_nickname;
    private String cus_payPassword;

}
//TODO:需要给密码加密，需要做坚权
