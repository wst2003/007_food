package org.tju.food_007.dto.cus.info;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author WGY
 * @create 2024-04-01-17:43
 */
@Getter
@Setter
public class GetCusInfoResponseDTO {

    private String user_phone;
    private String user_address;
    private BigDecimal user_balance;
    private Integer user_gender; // 0: 女性, 1: 男性
    private String cus_nickname;
    private String user_logo;
}
