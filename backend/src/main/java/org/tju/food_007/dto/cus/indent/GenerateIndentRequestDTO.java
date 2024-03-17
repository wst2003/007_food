package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter

public class GenerateIndentRequestDTO {
    private String cus_Id;
    private List<IndentCommodity> com_arr;
    private Integer delivery_method;
    private String delivery_address;
    private String ind_notes;
    private Double ind_money;
}
