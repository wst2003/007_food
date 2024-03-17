package org.tju.food_007.dto.cus.indent;

import lombok.Getter;
import lombok.Setter;

/**
 * @author WGY
 * @create 2024-03-17-20:36
 */
@Getter
@Setter
public class RatingRequestDTO {
    private String cus_id;
    private String ind_id;
    private Double food_quality_score;
    private Double service_quality_score;
    private Double price_performance_ratio;
    private String cmt_content;
    private Integer rating_type;
}
