package org.tju.food_007.dto.sto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-20-11:05
 */

@Setter
@Getter
public class PriceCurveInfo {
    private String com_pc_time; // 上传日期节点
    private Double com_pc_price; // 对应价格

    // Getter和Setter省略
}
