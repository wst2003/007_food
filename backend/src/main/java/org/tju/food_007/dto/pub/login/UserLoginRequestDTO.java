package org.tju.food_007.dto.pub.login;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-07-16:02
 */
@Getter
@Setter
public class UserLoginRequestDTO {
    public String user_phone;
    public String user_password;
}
