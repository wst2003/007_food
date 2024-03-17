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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.com.SearchCommodityResponseDTO;
import org.tju.food_007.dto.cus.indent.GetIndRequestDTO;
import org.tju.food_007.dto.cus.indent.GetIndResponseDTO;
import org.tju.food_007.dto.cus.indent.mapper.GetIndResponseMapper;
import org.tju.food_007.model.*;
import org.tju.food_007.repository.cus.indent.GetIndComPositionRepository;
import org.tju.food_007.repository.cus.indent.GetIndCommodityRepository;
import org.tju.food_007.repository.cus.indent.GetIndentComRepository;
import org.tju.food_007.repository.cus.indent.GetIndentRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author WGY
 * @create 2024-03-17-21:15
 */

@Service
public class GetIndentService {
    private final GetIndResponseMapper getIndResponseMapper =
            GetIndResponseMapper.INSTANCE;

    @Autowired
    GetIndCommodityRepository getIndCommodityRepository;

    @Autowired
    GetIndComPositionRepository getIndComPositionRepository;

    @Autowired
    GetIndentComRepository getIndentComRepository;

    @Autowired
    GetIndentRepository getIndentRepository;

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

    public List<GetIndResponseDTO> getIndbyUserID(GetIndRequestDTO request)
    {
        List<GetIndResponseDTO> responses =new ArrayList<>();

        List<IndentEntity>indents=getIndentRepository.findAllByCusId(request.getCus_ID());

        //筛选类别
        if(request.getState() != null){
            Iterator<IndentEntity> iterator = indents.iterator();
            while (iterator.hasNext()) {
                IndentEntity dto = iterator.next();
                if (!Objects.equals(dto.getIndState(), request.getState())) {
                    iterator.remove(); // 从结果中删除 sto_id 不匹配的项
                }
            }
        }

        for(IndentEntity indent : indents){
            GetIndResponseDTO tempDTO = new GetIndResponseDTO();

            List<GetIndResponseDTO.IndComDTO> tempComDTOs =new ArrayList<>();
            int sto_id=0;
            List<IndentCommodityEntity> IndComs = getIndentComRepository.findAllByIndId(indent.getIndId());
            for(IndentCommodityEntity IndCom :IndComs){
                GetIndResponseDTO.IndComDTO tempComDTO = new GetIndResponseDTO.IndComDTO();
                CommodityEntity commodity = getIndCommodityRepository.findByComId(IndCom.getComId());
                tempComDTO.setCom_ID(IndCom.getComId());
                tempComDTO.setInd_quantity(IndCom.getIndQuantity());
                tempComDTO.setCommodity_money(Double.parseDouble(IndCom.getCommodityMoney().toString()));
                tempComDTO.setRating_type(IndCom.getRatingType());
                tempComDTO.setCom_name(commodity.getComName());
                tempComDTOs.add(tempComDTO);
                sto_id=commodity.getStoId();
            }
            UserEntity store=getIndComPositionRepository.findByUserId(sto_id);
            tempDTO=getIndResponseMapper.entityToResponse(indent ,tempComDTOs.toArray(new GetIndResponseDTO.IndComDTO[0]),store);

            responses.add(tempDTO);

        }

        return responses;
    }


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
