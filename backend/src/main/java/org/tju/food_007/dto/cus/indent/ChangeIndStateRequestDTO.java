package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author WGY
 * @create 2024-04-17-16:30
 */
@Setter
@Getter
public class ChangeIndStateRequestDTO {
    Integer ind_id;
    Integer ind_state;
}
