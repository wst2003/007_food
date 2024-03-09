package org.tju.food_007.dto.com;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCommodityResponseDTO {
    private Integer com_ID;
    private String com_name;
    private String com_position;
    private Integer com_left;
    private Double praise_rate;
    private Double com_price;
    private Double com_dist= (double) 0;
    private String commodityImage;
}
