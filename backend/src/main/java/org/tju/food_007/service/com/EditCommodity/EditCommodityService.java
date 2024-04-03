package org.tju.food_007.service.com.EditCommodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.com.EditCommodity.EditCommodityRequestDTO;
import org.tju.food_007.dto.com.EditCommodity.EditCommodityResponseDTO;
import org.tju.food_007.model.CommodityEntity;
import org.tju.food_007.model.RegularCommodityEntity;
import org.tju.food_007.repository.com.EditCommodity.EditCommodityRepository;
import org.tju.food_007.repository.com.EditCommodity.EditRegularCommodityRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EditCommodityService {
    @Autowired
    EditCommodityRepository editCommodityRepository;

    @Autowired
    EditRegularCommodityRepository editRegularCommodityRepository;



    public EditCommodityResponseDTO editCommodity(EditCommodityRequestDTO request){
        EditCommodityResponseDTO response = new EditCommodityResponseDTO();
        if(!editCommodityRepository.existsByComId(request.getCom_ID())){
            response.setStatus("商品不存在");
            return response;
        }
        if(request.getCom_left()!=null){
            CommodityEntity commodity = editCommodityRepository.findByComId(request.getCom_ID());
            commodity.setComLeft(request.getCom_left());
            editCommodityRepository.save(commodity);
        }
        if (request.getOff_shelf() != null && request.getOff_shelf() == Boolean.TRUE) {
            RegularCommodityEntity regularCommodityEntity =
                    editRegularCommodityRepository.findByRegularComId(request.getCom_ID());
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String currentDateTimeStr = currentDateTime.format(formatter); // 将当前日期时间格式化为字符串

            // 将格式化后的字符串解析为 Timestamp 类型
            Timestamp timestamp = Timestamp.valueOf(currentDateTimeStr);

            regularCommodityEntity.setComExpirationDate(timestamp);
            editRegularCommodityRepository.save(regularCommodityEntity);
        }
        response.setStatus("success");


        return response;
    }
}
