package org.tju.food_007.dto.com.mysterybox;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMysteryboxRequestDTO {
    private Integer mystery_box_ID;
    private Integer sto_ID;
    private Integer page_size;
    private Integer page_num;
    private Integer sort_by; //0按价格排序  1按评分排序
    private Integer sort_order;//0升序1降序
}
