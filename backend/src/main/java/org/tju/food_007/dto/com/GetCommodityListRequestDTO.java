package org.tju.food_007.dto.com;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommodityListRequestDTO {
    private int sto_ID;
    private int page_size;
    private int page_num;
    private String com_type;
}
