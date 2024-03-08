package org.tju.food_007.dto.sto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class StoinformationdetailResponseDTO {
    private int user_ID;
    private String user_phone;
    private String user_password;
    private String user_address;
    private int user_state;
    private BigDecimal user_balance;
    private String user_regTime;
    private int user_type;
    private int user_gender;
    private String sto_name;
    private String sto_introduction;
    private int sto_state;
    private String sto_openingTime;
    private String sto_closingTime;
    private String sto_latitude;
    private String sto_longtitude;
    private StoCategoriesInformationResponseDTO[] store_categories;
    private StoLicenseInformationResponseDTO[] storeLicense;
    private StoImageInfomationResponseDTO[] storeImage;
    private BigDecimal distance = BigDecimal.valueOf(0);
}
