package org.tju.food_007.dto.com;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommodityDetailDTO {
    private int com_ID;
    private String com_name;
    private String com_introduction;
    private String com_uploadDate;
    private int com_left;
    private int sto_ID;
    private int com_type;
    private double com_oriPrice;
    private double praise_rate;
    private String com_expirationDate;
    private String com_producedDate;
    private CommodityPriceCurveResponseDTO[] commodityPriceCurve;
    private CommodityImageResponseDTO[] commodityImage;
    private CommodityCategoriesResponseDTO[] commodity_categories;
    private String sto_openingTime;
    private String sto_closingTime;
}
