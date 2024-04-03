package org.tju.food_007.dto.com.EditCommodity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditCommodityRequestDTO {
    Integer com_ID;
    Integer com_left;
    Boolean off_shelf;
}
