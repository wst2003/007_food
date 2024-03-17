package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author WGY
 * @create 2024-03-17-21:12
 */
@Getter
@Setter
public class IndentComInfo {
    private Integer com_id;
    private Integer ind_quantity;
    private BigDecimal commodity_money;
    private Integer rating_type;
    private String com_name;
    private String com_position;
}
