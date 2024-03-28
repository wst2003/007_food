package org.tju.food_007.dto.com;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCommodityRequestDTO {
    private String content;
    private String com_type;
    private Integer sort_by;
    private Integer sort_order;
    private Integer page_size;
    private Integer page_num;
    private Integer sto_ID=0;
    private Double remaining_proportion;
}
