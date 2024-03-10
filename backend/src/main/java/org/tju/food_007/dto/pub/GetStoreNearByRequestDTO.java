package org.tju.food_007.dto.pub;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-10-15:32
 */
@Getter
@Setter
public class GetStoreNearByRequestDTO {
    private double cur_latitude;
    private double cur_longitude;


}
//TODO:需要给密码加密，需要做坚权
