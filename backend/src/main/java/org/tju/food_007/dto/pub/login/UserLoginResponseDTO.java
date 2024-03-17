package org.tju.food_007.dto.pub.login;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-07-16:08
 */
@Getter
@Setter
public class UserLoginResponseDTO {
     private String msg;
     private  Integer user_type;
     private  Integer user_id;
}
