package org.tju.food_007.dto.com.mysterybox;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMysteryboxResponseDTO {
    private int mystery_box_ID;
    private double highest_price;
    private double lowest_price;
    private int category_count;
    private String item_image;
    private String com_name;
    private String com_introduction;
    private int com_left;
    private double com_oriPrice;
    private int sto_ID;
    private int com_type;
    private int praise_rate;
    private String sto_name;
    private String sto_openingTime;
    private String sto_closingTime;
    private String sto_latitude;
    private String sto_longitude;
    private int sto_rating;
    private String sto_logo;
    private String user_address;
    private ImageDTO[] contain_images;

    @Getter
    @Setter
    public static class ImageDTO {
        private String com_image;
    }
}



