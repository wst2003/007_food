package org.tju.food_007.service.cus.indent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.cus.indent.GetIndentResponseDTO;
import org.tju.food_007.dto.cus.indent.IndentComInfo;
import org.tju.food_007.dto.cus.mapper.GenerateIndentComRequstMapper;
import org.tju.food_007.dto.cus.mapper.GenerateIndentRequestMapper;
import org.tju.food_007.model.IndentCommodityEntity;
import org.tju.food_007.model.IndentEntity;
import org.tju.food_007.repository.cus.indent.GenerateIndentComRepository;
import org.tju.food_007.repository.cus.indent.GenerateIndentRepository;
import org.tju.food_007.repository.cus.indent.IndentCommentDetailRepository;
import org.tju.food_007.repository.cus.indent.IndentCommentRopsitory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WGY
 * @create 2024-03-17-21:15
 */
@Service
public class GetIndentService {
    @Autowired
    GenerateIndentRepository generateIndentRepository;
    @Autowired
    GenerateIndentComRepository generateIndentComRepository;
    @Autowired
    IndentCommentRopsitory indentCommentRopsitory;
    @Autowired
    IndentCommentDetailRepository indentCommentDetailRepository;
    private final GenerateIndentComRequstMapper generateIndentComRequstMapper=GenerateIndentComRequstMapper.INSTANCE;
    private final GenerateIndentRequestMapper generateIndentRequestMapper=GenerateIndentRequestMapper.INSTANCE;

    public GetIndentResponseDTO GetIndent(Integer ind_id){

        GetIndentResponseDTO responseDTO =new GetIndentResponseDTO();
        IndentEntity aim_ind=generateIndentRepository.findByIndId(ind_id);
        responseDTO.setInd_id(aim_ind.getIndId());
        responseDTO.setCus_id(aim_ind.getCusId());
        responseDTO.setInd_money(aim_ind.getIndMoney());
        responseDTO.setInd_creationTime(aim_ind.getIndCreationTime().toString()); // 假设这是一个DateTime对象，需要转换为字符串
        responseDTO.setInd_verificationCode(aim_ind.getIndVerificationCode());
        responseDTO.setInd_notes(aim_ind.getIndNotes());
        responseDTO.setInd_state(aim_ind.getIndState());
        responseDTO.setFood_quality_score(aim_ind.getFoodQualityScore());
        responseDTO.setService_quality_score(aim_ind.getServiceQualityScore());
        responseDTO.setPrice_performance_ratio(aim_ind.getPricePerformanceRatio());
        responseDTO.setDelivery_method(aim_ind.getDeliveryMethod());
        responseDTO.setDelivery_address(aim_ind.getDeliveryAddress());

        ArrayList<IndentCommodityEntity> arr_com= generateIndentComRepository.findByIndId(ind_id);
        ArrayList<IndentComInfo>arr_com_info=new ArrayList<IndentComInfo>();
        for(IndentCommodityEntity com : arr_com){
            IndentComInfo com_info =new IndentComInfo();
            com_info.setCom_id(com.getComId());
            com_info.setInd_quantity(com.getIndQuantity());
            com_info.setCommodity_money(com.getCommodityMoney());
            com_info.setRating_type(com.getRatingType());
            com_info.setCom_name(indentCommentDetailRepository.findByComId(com.getComId()).getComName());
            com_info.setCom_position("上海");
            arr_com_info.add(com_info);
        }
        responseDTO.setCommodities(arr_com_info);
        return responseDTO;
    }
}
