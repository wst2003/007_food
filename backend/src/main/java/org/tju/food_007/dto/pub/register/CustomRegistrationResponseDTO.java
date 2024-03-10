package org.tju.food_007.dto.pub.register;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-05-14:54
 */

@Getter
@Setter
public class CustomRegistrationResponseDTO {
    private int user_id;
    private  int user_type;
    private String msg;
}
