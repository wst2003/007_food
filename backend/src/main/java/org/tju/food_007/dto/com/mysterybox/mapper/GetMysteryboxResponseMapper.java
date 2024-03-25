package org.tju.food_007.dto.com.mysterybox.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.tju.food_007.dto.com.mysterybox.GetMysteryboxResponseDTO;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.MysteryBoxEntity;
import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.model.UserEntity;

@Mapper
public interface GetMysteryboxResponseMapper {
    GetMysteryboxResponseMapper INSTANCE = Mappers.getMapper(GetMysteryboxResponseMapper.class);

    @Mapping(source = "box.mysteryBoxId", target = "mystery_box_ID")
    @Mapping(source = "box.highestPrice", target = "highest_price")
    @Mapping(source = "box.lowestPrice", target = "lowest_price")
    @Mapping(source = "box.categoryCount", target = "category_count")
    @Mapping(source = "box.itemImage", target = "item_image")
    @Mapping(source = "com.comName", target = "com_name")
    @Mapping(source = "com.comIntroduction", target = "com_introduction")
    @Mapping(source = "com.comLeft", target = "com_left")
    @Mapping(source = "com.comOriPrice",target = "com_oriPrice")
    @Mapping(source = "sto.stoId", target = "sto_ID")
    @Mapping(source = "com.comType", target = "com_type")
    @Mapping(source = "com.praiseRate", target = "praise_rate")
    @Mapping(source = "sto.stoName", target = "sto_name")
    @Mapping(source = "sto.stoOpeningTime", target = "sto_openingTime")
    @Mapping(source = "sto.stoClosingTime", target = "sto_closingTime")
    @Mapping(source = "sto.stoLatitude", target = "sto_latitude")
    @Mapping(source = "sto.stoLongitude", target = "sto_longitude")
    @Mapping(source = "sto.stoRating", target = "sto_rating")
    @Mapping(source = "sto.stoLogo", target = "sto_logo")
    @Mapping(source = "user.userAddress", target = "user_address")
    @Mapping(source = "images", target = "contain_images")
    GetMysteryboxResponseDTO entityToResponse(
            MysteryBoxEntity box, CommodityEntity com ,
            StoreEntity sto , UserEntity user,
            GetMysteryboxResponseDTO.ImageDTO[] images
            );

}
